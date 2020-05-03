package spiglet.flow;

import spiglet.spiglet2kanga.Spiglet2KangaVisitor;
import spiglet.syntaxtree.MoveStmt;

import java.util.*;

import static spiglet.spiglet2kanga.KangaWriter.label;

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
     * Number of statements excluding NOOP/JUMP/CJUMP and dead MOVE/HLOAD
     */
    protected int valid = 0;
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
    protected String label = null;
    /**
     * Control Flow Graph (CFG) the block belongs to
     */
    protected Graph graph;

    protected Block(Graph _graph) {
        graph = _graph;
    }

    protected void addStatement(Statement stmt, boolean isValid) {
        statements.add(stmt);
        if (isValid) ++valid;
    }

    protected void setDefUse() {
        ListIterator<Statement> it = statements.listIterator(statements.size());
        while (it.hasPrevious()) {
            Statement stmt = it.previous();
            def.removeAll(stmt.use);
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

            /*
             * dead code elimination:
             *   for MOVE/HLOAD statement
             *   dead statement if stmt.def not in live
             */
            if (!stmt.def.isEmpty()) stmt.dead = true;
            for (int temp : stmt.def)
                if (live.contains(temp)) {
                    stmt.dead = false;
                    break;
                }

            live.removeAll(stmt.def);

            /* TEMPs that are live across a procedure call */
            if (stmt.isCall) graph.acrossCall.addAll(live);

            if (!stmt.dead || stmt.isCall) live.addAll(stmt.use);
            else --valid; // stmt is not valid
        }
        setInterference(live);
    }

    protected void setInterference(Set<Integer> s) {
        for (int i : s)
            for (int j : s)
                graph.setInterference(i, j);
    }

    public void Kanga(Spiglet2KangaVisitor visitor) {
        if (label != null) {
            int l = graph.getLabel(label);
            if (l > 0) label(l);
        }

        for (Statement stmt : statements)
            if (stmt.node != null)
                if (!stmt.dead) visitor.visit(stmt.node, graph);
                else if (stmt.isCall)
                    visitor.visit(((MoveStmt) stmt.node.f0.choice).f2, graph);
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