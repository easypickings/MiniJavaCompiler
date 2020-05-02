package spiglet.flow;

import java.util.*;

public class Block {

    /**
     * TEMPs that are live at the entry of the block
     */
    protected Set<Integer> in = new HashSet<>();
    /**
     * TEMPS that are live at the exit of the block
     */
    protected Set<Integer> out = new HashSet<>();
    /**
     * TEMPs that are defined before usage in the block
     */
    protected Set<Integer> def = new HashSet<>();
    /**
     * TEMPs that are used before definition in the block
     */
    protected Set<Integer> use = new HashSet<>();
    /**
     * Statements in the block
     */
    protected List<Statement> statements = new LinkedList<>();
    /**
     * Predecessors of the block
     */
    protected Set<Block> preBlocks = new HashSet<>();
    /**
     * Successors of the block
     */
    protected Set<Block> sucBlocks = new HashSet<>();
    /**
     * Label of the block
     */
    protected String label;
    /**
     * Control Flow Graph (CFG) the block belongs to
     */
    protected Graph graph;

    protected Block(Graph _graph) {
        graph = _graph;
    }

    protected void addStatement(Statement stmt) {
        statements.add(stmt);
    }

    protected void setDefUse() {
        ListIterator<Statement> it = statements.listIterator(statements.size());
        while (it.hasPrevious()) {
            Statement stmt = it.previous();
            def.removeAll(stmt.use); // TODO: is this necessary?
            def.addAll(stmt.def);
            use.removeAll(stmt.def);
            use.addAll(stmt.use);
        }
    }

    protected void BuildInterference() {
        Set<Integer> live = new HashSet<>(out);
        ListIterator<Statement> it = statements.listIterator(statements.size());
        while (it.hasPrevious()) {
            setInterference(live);
            Statement stmt = it.previous();

            // TODO: dead code elimination (stmt.def not in live)

            /* TEMPs that are live across a procedure call */
            if (stmt.isCall) graph.acrossCall.addAll(live);

            live.removeAll(stmt.def);
            live.addAll(stmt.use);
        }
        setInterference(live);
    }

    protected void setInterference(Set<Integer> s) {
        for (int i : s)
            for (int j : s)
                graph.setInterference(i, j);
    }

    protected void Print() {
        System.out.println("\n********************");
        System.out.println("statements: " + statements.size());
        System.out.println("in: " + in);
        System.out.println("out: " + out);
        System.out.println("def: " + def);
        System.out.println("use: " + use);
        System.out.println("********************\n");
    }
}