//
// Generated by JTB 1.3.2
//

package spiglet.syntaxtree;

import spiglet.visitor.GJNoArguVisitor;
import spiglet.visitor.GJVisitor;
import spiglet.visitor.GJVoidVisitor;
import spiglet.visitor.Visitor;

/**
 * Grammar production:
 * f0 -> "NOOP"
 */
public class NoOpStmt implements Node {
    private static final long serialVersionUID = 3305363365886221926L;
    public NodeToken f0;

    public NoOpStmt(NodeToken n0) {
        f0 = n0;
    }

    public NoOpStmt() {
        f0 = new NodeToken("NOOP");
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public <R, A> R accept(GJVisitor<R, A> v, A argu) {
        return v.visit(this, argu);
    }

    @Override
    public <R> R accept(GJNoArguVisitor<R> v) {
        return v.visit(this);
    }

    @Override
    public <A> void accept(GJVoidVisitor<A> v, A argu) {
        v.visit(this, argu);
    }
}

