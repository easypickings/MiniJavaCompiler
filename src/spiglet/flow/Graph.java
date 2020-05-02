package spiglet.flow;

import java.util.*;

import static spiglet.spiglet2kanga.KangaWriter.*;

public class Graph {

    /**
     * Name of the procedure
     */
    protected String name;
    /**
     * Blocks in the graph
     */
    protected List<Block> blocks = new LinkedList<>();
    /**
     * Entry block
     */
    protected Block entry;
    /**
     * Exit block
     */
    protected Block exit;
    /**
     * Previous block
     */
    protected Block pre;
    /**
     * Current block
     */
    protected Block cur;
    /**
     * Successor's label of blocks ending with JUMP/CJUMP/ERROR
     */
    protected Map<Block, String> jump = new HashMap<>();
    /**
     * Blocks with label
     */
    protected Map<String, Block> labelBlocks = new HashMap<>();
    /**
     * (New) global label number of labels
     */
    protected Map<String, Integer> newLabels = new HashMap<>();

    /**
     * Interference graph
     */
    protected Map<Integer, Set<Integer>> interferenceGraph = new HashMap<>();
    /**
     * TEMPs that are live across a procedure call,
     * therefore should be allocated to s0-s7 only
     */
    protected Set<Integer> acrossCall = new HashSet<>();
    /**
     * Register numbers TEMPs are allocated to
     */
    protected Map<Integer, Integer> allocTemp = new HashMap<>();
    /**
     * TEMPs that are spilled to stack
     */
    protected Set<Integer> spillTemp = new HashSet<>();
    /**
     * Registers s0-s7 allocated in the procedure
     */
    protected Set<Integer> sAllocated = new HashSet<>();

    /**
     * Number of args taken by the procedure
     */
    protected int args;
    /**
     * Number of stack slots the procedure requires
     */
    protected int stacks = 0;
    /**
     * Maximum arguments of a call in the body of the procedure
     */
    protected int callArgs = 0;

    public Graph(String _name, int _args) {
        name = _name;
        args = _args;

        entry = new Block(this);
        exit = new Block(this);
        pre = entry;
        cur = new Block(this);

        blocks.add(cur);
        LinkBlock(pre, cur);

        labelBlocks.put("-1", exit); // label "-1" denotes exit block
    }

    /**
     * Add a statement to current block {@code cur}
     *
     * @param stmt statement to be add to current block
     */
    public void addStatement(Statement stmt) {
        cur.addStatement(stmt);
    }

    protected void LinkBlock(Block prev, Block next) {
        prev.sucBlocks.add(next);
        next.preBlocks.add(prev);
    }

    protected void UnlinkBlock(Block prev, Block next) {
        prev.sucBlocks.remove(next);
        next.preBlocks.remove(prev);
    }

    public void NewBlock() {
        pre = cur;
        cur = new Block(this);
        blocks.add(cur);
        LinkBlock(pre, cur);
    }

    public void NewLabelBlock(String label) {
        /* Only start a new block when current block contains statement(s)
         * Otherwise may generate empty blocks, e.g.
         *
         *         JUMP L1   // start a new block b1
         *     L2  NOOP      // start another new block, b1 will be empty
         */
        if (cur.statements.size() > 0) NewBlock();
        cur.label = label;
        labelBlocks.put(label, cur);
        newLabels.put(label, NewLabel());
    }

    /**
     * Set jumping destination and begin a new block
     * when current block {@code cur} ends with ERROR/JUMP/CJUMP
     *
     * @param label  label of jumping destination
     * @param unlink whether unlink {@code cur} and the new block
     */
    public void setJump(String label, boolean unlink) {
        jump.put(cur, label); // set the label of jumping destination
        NewBlock(); // begin a new block
        if (unlink) UnlinkBlock(pre, cur); // unlink blocks
    }

    public void setCallArgs(int _args) {
        callArgs = Math.max(callArgs, _args);
    }

    public void BuildControlFlow() {
        /* complete block linking */
        for (Map.Entry<Block, String> e : jump.entrySet())
            LinkBlock(e.getKey(), labelBlocks.get(e.getValue()));

        /* set blocks' def and use */
        for (Block b : blocks) b.setDefUse();
    }

    public void AllocateReg() {
        LiveAnalysis();
        for (Block b : blocks) b.BuildInterference();

        // TODO: remove redundant blocks and labels

        PreColor();
        Color();

        for (int k = s0; k <= s7; ++k)
            if (allocTemp.containsValue(k)) sAllocated.add(k);

        /*          stack
         * _______________________ 0
         * |     extra args      |
         * +---------------------+
         * |callee-save registers|
         * |  (s0-s7 allocated)  |
         * +---------------------+
         * |    spilled temps    |
         * +---------------------+ n
         */
        stacks = Math.max(args - 4, 0) + sAllocated.size() + spillTemp.size();


        Print();
    }

    protected void LiveAnalysis() {
        Set<Block> changed = new HashSet<>(blocks);

        while (!changed.isEmpty()) {
            Block b = changed.iterator().next();
            changed.remove(b);

            b.out.clear();
            for (Block s : b.sucBlocks) b.out.addAll(s.in);

            Set<Integer> in = new HashSet<>(b.out);
            in.removeAll(b.def);
            in.addAll(b.use);

            if (!in.equals(b.in)) {
                b.in.clear();
                b.in.addAll(in);
                changed.addAll(b.preBlocks);
            }
        }
    }

    protected void setInterference(int i, int j) {
        if (i == j) {
            /* assure degree-0 nodes are put into interference graph as well */
            if (!interferenceGraph.containsKey(i))
                interferenceGraph.put(i, new HashSet<>());
            return;
        }

        Set<Integer> iNeighbor = interferenceGraph.getOrDefault(i,
                new HashSet<>());
        Set<Integer> jNeighbor = interferenceGraph.getOrDefault(j,
                new HashSet<>());
        iNeighbor.add(j);
        jNeighbor.add(i);
        interferenceGraph.put(i, iNeighbor);
        interferenceGraph.put(j, jNeighbor);
    }

    /**
     * Pre color TEMPs that are live across a procedure call
     * only allocate to s0-s7
     */
    protected void PreColor() {
        Map<Integer, Integer> degree = new HashMap<>();
        for (int i : acrossCall) {
            int deg = 0;
            for (int j : interferenceGraph.get(i))
                if (acrossCall.contains(j)) ++deg;
            degree.put(i, deg);
        }

        Stack<Integer> stack = new Stack<>();
        int maxReg = 8; // s0-s7
        while (!degree.isEmpty()) {
            int i = -1; // node about to push to the stack
            int m = -1, maxDeg = -1; // node with greatest degree
            for (Map.Entry<Integer, Integer> e : degree.entrySet()) {
                int deg = e.getValue();
                if (deg < maxReg) {
                    i = e.getKey();
                    break;
                }
                if (deg > maxDeg) {
                    m = e.getKey();
                    maxDeg = deg;
                }
            }

            /* NOTE: spilled nodes are pushed into the stack as well in case
             * that they can still be colored.
             *
             * e.g. Suppose  2 - 1 - 0  is about to colored with 2 registers.
             *               |   |
             *               3 - 4
             *      Although a node among {1,2,3,4} will be spilled, the whole
             *      graph can still be colored. No actual spilling is necessary.
             *
             * The nodes are actually spilled if no register is available
             * when being popped out from the stack.
             */
            if (i < 0) i = m;
            stack.push(i);

            for (int j : interferenceGraph.get(i))
                if (degree.containsKey(j)) // TODO: acrossCall.contains(j)?
                    degree.put(j, degree.get(j) - 1);
            degree.remove(i);
        }

        TreeSet<Integer> freeReg = new TreeSet<>();
        while (!stack.isEmpty()) {
            int i = stack.pop();

            freeReg.clear();
            for (int k = s0; k <= s7; ++k) freeReg.add(k);

            for (int j : interferenceGraph.get(i))
                if (allocTemp.containsKey(j))
                    freeReg.remove(allocTemp.get(j));

            if (freeReg.isEmpty()) spillTemp.add(i); // spill the node
            else allocTemp.put(i, freeReg.first());
        }
    }

    protected void Color() {
        Map<Integer, Integer> degree = new HashMap<>();
        for (Map.Entry<Integer, Set<Integer>> e :
                interferenceGraph.entrySet()) {
            int i = e.getKey();
            if (!allocTemp.containsKey(i)) // ignore pre colored nodes
                degree.put(i, e.getValue().size());
        }

        Stack<Integer> stack = new Stack<>();
        int maxReg = 18; // t0-t9, s0-s7
        while (!degree.isEmpty()) {
            int i = -1; // node about to push to the stack
            int m = -1, maxDeg = -1; // node with greatest degree
            for (Map.Entry<Integer, Integer> e : degree.entrySet()) {
                int deg = e.getValue();
                if (deg < maxReg) {
                    i = e.getKey();
                    break;
                }
                if (deg > maxDeg) {
                    m = e.getKey();
                    maxDeg = deg;
                }
            }

            // NOTE: see PreColor()
            if (i < 0) i = m;
            stack.push(i);

            for (int j : interferenceGraph.get(i))
                if (degree.containsKey(j))
                    degree.put(j, degree.get(j) - 1);
            degree.remove(i);
        }

        TreeSet<Integer> freeReg = new TreeSet<>();
        while (!stack.isEmpty()) {
            int i = stack.pop();

            freeReg.clear();
            for (int k = t0; k <= s7; ++k) freeReg.add(k);

            for (int j : interferenceGraph.get(i))
                if (allocTemp.containsKey(j))
                    freeReg.remove(allocTemp.get(j));

            if (freeReg.isEmpty()) spillTemp.add(i); // spill the node
            else allocTemp.put(i, freeReg.first());
        }
    }

    protected void Print() {
        System.out.println("\n********************");
        System.out.println(name);
        for (Map.Entry<Integer, Integer> e : allocTemp.entrySet())
            System.out.println("\tTEMP " + e.getKey() + " -> " + reg[e.getValue()]);
        for (int i : spillTemp)
            System.out.println("\tTEMP " + i + " spilled");
        System.out.println("********************\n");
    }


}
