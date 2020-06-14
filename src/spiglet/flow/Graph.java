package spiglet.flow;

import spiglet.spiglet2kanga.Spiglet2KangaVisitor;
import spiglet.syntaxtree.SimpleExp;

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
     * Stack slots TEMPS are spilled to
     */
    protected Map<Integer, Integer> spillTemp = new HashMap<>();
    /**
     * Callee-save registers (s0-s7) allocated in the procedure
     */
    protected List<Integer> sAllocated = new ArrayList<>();

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

    /**
     * Return expression
     */
    protected SimpleExp retExp = null;

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
    public void addStatement(Statement stmt, boolean valid) {
        cur.addStatement(stmt, valid);
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
        /*
         * Only start a new block when current block contains statement(s)
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

    public void setRet(SimpleExp _exp) {
        retExp = _exp;
    }

    public void BuildControlFlow() {
        /* complete block linking */
        for (Map.Entry<Block, String> e : jump.entrySet())
            LinkBlock(e.getKey(), labelBlocks.get(e.getValue()));

        /* remove dead block */
        DeadBlock();

        /* set blocks' def and use */
        for (Block b : blocks) b.setDefUse();
    }

    public void AllocateReg() {
        LiveAnalysis();
        for (Block b : blocks) b.BuildInterference();

        PreColor();
        Color();

        DeadBlock();
        DeadLabel();

        for (int k = s0; k <= s7; ++k)
            if (allocTemp.containsValue(k)) sAllocated.add(k);

        int offset = Math.max(args - 4, 0) + sAllocated.size();
        spillTemp.replaceAll((k, v) -> v + offset); // add offset to spill slots

        /*
         *        stack
         * _______________________ 0  _
         * |     extra args      |    |
         * +---------------------+    |
         * |callee-save registers|  offset
         * |  (s0-s7 allocated)  |    |
         * +---------------------+    -
         * |    spilled temps    |
         * +---------------------+ n
         */
        stacks = offset + spillTemp.size();
        // Print();
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

            /*
             * NOTE: spilled nodes are pushed into the stack as well in case
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
                if (degree.containsKey(j))
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

            if (freeReg.isEmpty())
                spillTemp.put(i, spillTemp.size()); // spill the node
            else allocTemp.put(i, freeReg.first());
        }
    }

    protected void Color() {
        Map<Integer, Integer> degree = new HashMap<>();
        for (Map.Entry<Integer, Set<Integer>> e :
                interferenceGraph.entrySet()) {
            int i = e.getKey();

            // ignore pre colored nodes
            if (!allocTemp.containsKey(i) && !spillTemp.containsKey(i))
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

            if (freeReg.isEmpty())
                spillTemp.put(i, spillTemp.size()); // spill the node
            else allocTemp.put(i, freeReg.first());
        }
    }

    protected void DeadBlock() {
        /* blocks with no predecessor is dead */
        for (Block b : blocks)
            if (b.preBlocks.isEmpty()) {
                for (Block s : b.sucBlocks) s.preBlocks.remove(b);
                blocks.remove(b);
            }
    }

    protected void DeadLabel() {
        /*
         * labels that are not jump targets or jump right from previous block
         * is redundant
         */
        Block prev = null;
        for (Block b : blocks) {
            boolean redundant = true;
            for (Block p : b.preBlocks)
                if (p != prev) {
                    redundant = false;
                    break;
                }
            if (redundant) // will do nothing if b.label is null
                newLabels.replace(b.label, -1);
            prev = b;
        }

        /*
         * labels of blocks that contain no valid statements (i.e. only
         * NOOP/JUMP/CJUMP or dead MOVE/HLOAD statements) can be substituted by
         * the label of successor
         */
        prev = null;
        for (Block b : blocks) {
            if (b.valid == 0 && b.sucBlocks.size() == 1) {
                Block s = b.sucBlocks.iterator().next();
                if (b.label != null && s.label != null) {
                    int bLabel = newLabels.get(b.label),
                            sLabel = newLabels.get(s.label);
                    for (Map.Entry<String, Integer> e : newLabels.entrySet())
                        // if current value is bLabel, replace it by sLabel
                        newLabels.replace(e.getKey(), bLabel, sLabel);
                    b.label = null;

                    if (!b.preBlocks.contains(prev))
                        b.statements.clear();
                }
            }
            prev = b;
        }
    }

    public void Kanga() {
        Begin();

        Spiglet2KangaVisitor kanga = new Spiglet2KangaVisitor();
        for (Block block : blocks) block.Kanga(kanga);

        if (retExp != null) // handle RETURN
            move(v0, kanga.visit(retExp, this));

        End();
    }


    /**
     * Print Kanga prologue of the procedure
     */
    protected void Begin() {
        emit(name + " [" + args + "][" + stacks + "][" + callArgs + "]\n", "");

        /* store s* registers before using in the procedure */
        int x = Math.max(args - 4, 0);
        for (int i : sAllocated) astore(x++, i);

        /* load live args to t0-t9/s0-s7, or spill if necessary */
        for (int arg : entry.out) {
            int reg = getReg(arg);
            if (reg >= 0) // arg is allocated to a register
                if (arg < 4) move(reg, Reg(a0 + arg));// move from a* to reg
                else aload(reg, arg - 4); // load from stack to reg
            else // arg is spilled
                if (arg < 4) // store a* to stack
                    astore(spillTemp.get(arg), a0 + arg);
                else { // load from stack to v1, then store v1 to stack
                    aload(v1, arg - 4);
                    astore(spillTemp.get(arg), v1);
                }
        }
    }

    /**
     * Print Kanga epilogue of the procedure
     */
    protected void End() {
        /* restore s* registers from stack */
        int x = Math.max(args - 4, 0);
        for (int i : sAllocated) aload(i, x++);
        emit("END\n\n", "");
    }

    /**
     * Get the register a TEMP is allocated to. If TEMP is not allocated to a
     * register, load it to a temporary register {@code v} (v0 or v1) first.
     *
     * @param temp TEMP number
     * @param v    temporary register number
     * @return number of the register {@code temp} is allocated to, or {@code
     * v} if {@code temp} is not allocated to a register
     */
    public int getReg(int temp, int v) {
        if (allocTemp.containsKey(temp)) return allocTemp.get(temp);
        aload(v, spillTemp.get(temp));
        return v;
    }

    /**
     * Get the register a TEMP is allocated to. If TEMP is not allocated to a
     * register, return {@code -1}.
     *
     * @param temp TEMP number
     * @return number of the register {@code temp} is allocated to, or {@code
     * -1} if {@code temp} is not allocated to a register
     */
    public int getReg(int temp) {
        return allocTemp.getOrDefault(temp, -1);
    }

    /**
     * Get the (new) label number of a label string. If the number doesn't
     * exist (due to redundant label removal), return {@code -1}.
     *
     * @param label old label string
     * @return new label number, or {@code -1} if not exist
     */
    public int getLabel(String label) {
        return newLabels.getOrDefault(label, -1);
    }

    /**
     * Get the stack slot a TEMP is spilled to. If TEMP is not spilled,
     * return {@code -1}.
     *
     * @param temp TEMP number
     * @return stack slot {@code temp} is spilled to, or {@code -1} if {@code
     * temp} is not spilled.
     */
    public int getSpill(int temp) {
        return spillTemp.getOrDefault(temp, -1);
    }

    protected void Print() {
        System.out.println("\n********************");
        System.out.println(name);
        for (Map.Entry<Integer, Integer> e : allocTemp.entrySet())
            System.out.println("\tTEMP " + e.getKey()
                    + " -> " + Reg(e.getValue()));
        for (Map.Entry<Integer, Integer> e : spillTemp.entrySet())
            System.out.println("\tTEMP " + e.getKey()
                    + " -> stack " + e.getValue());
        System.out.println("acrossCall: " + acrossCall);
        System.out.println("********************\n");
    }

}
