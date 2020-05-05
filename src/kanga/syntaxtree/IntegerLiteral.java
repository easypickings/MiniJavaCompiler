//
// Generated by JTB 1.3.2
//

package kanga.syntaxtree;

import kanga.visitor.GJNoArguVisitor;
import kanga.visitor.GJVisitor;
import kanga.visitor.GJVoidVisitor;
import kanga.visitor.Visitor;

/**
 * Grammar production:
 * f0 -> <INTEGER_LITERAL>
 */
public class IntegerLiteral implements Node {
    private static final long serialVersionUID = -5421121472013468170L;
    public NodeToken f0;

    public IntegerLiteral(NodeToken n0) {
        f0 = n0;
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

