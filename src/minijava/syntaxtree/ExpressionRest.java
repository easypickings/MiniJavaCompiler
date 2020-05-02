//
// Generated by JTB 1.3.2
//

package minijava.syntaxtree;

import minijava.visitor.GJNoArguVisitor;
import minijava.visitor.GJVisitor;
import minijava.visitor.GJVoidVisitor;
import minijava.visitor.Visitor;

/**
 * Grammar production:
 * f0 -> ","
 * f1 -> Expression()
 */
public class ExpressionRest implements Node {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public NodeToken f0;
    public Expression f1;

    public ExpressionRest(NodeToken n0, Expression n1) {
        f0 = n0;
        f1 = n1;
    }

    public ExpressionRest(Expression n0) {
        f0 = new NodeToken(",");
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

