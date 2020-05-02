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
 * f0 -> "if"
 * f1 -> "("
 * f2 -> Expression()
 * f3 -> ")"
 * f4 -> Statement()
 * f5 -> "else"
 * f6 -> Statement()
 */
public class IfStatement implements Node {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public NodeToken f0;
    public NodeToken f1;
    public Expression f2;
    public NodeToken f3;
    public Statement f4;
    public NodeToken f5;
    public Statement f6;

    public IfStatement(NodeToken n0, NodeToken n1, Expression n2,
                       NodeToken n3, Statement n4, NodeToken n5, Statement n6) {
        f0 = n0;
        f1 = n1;
        f2 = n2;
        f3 = n3;
        f4 = n4;
        f5 = n5;
        f6 = n6;
    }

    public IfStatement(Expression n0, Statement n1, Statement n2) {
        f0 = new NodeToken("if");
        f1 = new NodeToken("(");
        f2 = n0;
        f3 = new NodeToken(")");
        f4 = n1;
        f5 = new NodeToken("else");
        f6 = n2;
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

