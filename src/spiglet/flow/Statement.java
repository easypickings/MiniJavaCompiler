package spiglet.flow;

import spiglet.syntaxtree.Stmt;

import java.util.HashSet;
import java.util.Set;

public class Statement {

    /**
     * Stmt node
     */
    protected Stmt node;
    /**
     * TEMPs defined in the statement
     */
    protected Set<Integer> def = new HashSet<>();
    /**
     * TEMPs used in the statement
     */
    protected Set<Integer> use = new HashSet<>();
    /**
     * Whether the statement contains CALL expression
     */
    protected boolean isCall = false;
    /**
     * Whether the statement is dead code
     */
    protected boolean dead = false;

    public Statement(Stmt n) {
        node = n;
    }

    /*
     * e.g. x = y + z
     *      use = {y, z}
     *      def = {x} - {y, z}
     * NOTE: y, z can be identical with x
     */

    public void addDef(int temp) {
        // carefully assure addDef() is called AFTER addUse()
        def.add(temp);
        def.removeAll(use);
    }

    public void addUse(int temp) {
        use.add(temp);
    }

    public void setCall() {
        isCall = true;
    }
}
