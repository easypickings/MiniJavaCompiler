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
 * f0 -> "PRINT"
 * f1 -> SimpleExp()
 */
public class PrintStmt implements Node {
    private static final long serialVersionUID = -6801225628590791858L;
    public NodeToken f0;
    public SimpleExp f1;

    public PrintStmt(NodeToken n0, SimpleExp n1) {
        f0 = n0;
        f1 = n1;
    }

    public PrintStmt(SimpleExp n0) {
        f0 = new NodeToken("PRINT");
        f1 = n0;
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

