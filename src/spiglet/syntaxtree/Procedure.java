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
 * f0 -> Label()
 * f1 -> "["
 * f2 -> IntegerLiteral()
 * f3 -> "]"
 * f4 -> StmtExp()
 */
public class Procedure implements Node {
    private static final long serialVersionUID = -3819827189953699474L;
    public Label f0;
    public NodeToken f1;
    public IntegerLiteral f2;
    public NodeToken f3;
    public StmtExp f4;

    public Procedure(Label n0, NodeToken n1, IntegerLiteral n2, NodeToken n3,
                     StmtExp n4) {
        f0 = n0;
        f1 = n1;
        f2 = n2;
        f3 = n3;
        f4 = n4;
    }

    public Procedure(Label n0, IntegerLiteral n1, StmtExp n2) {
        f0 = n0;
        f1 = new NodeToken("[");
        f2 = n1;
        f3 = new NodeToken("]");
        f4 = n2;
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

