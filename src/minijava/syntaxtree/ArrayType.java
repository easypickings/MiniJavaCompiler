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
 * f0 -> "int"
 * f1 -> "["
 * f2 -> "]"
 */
public class ArrayType implements Node {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public NodeToken f0;
    public NodeToken f1;
    public NodeToken f2;

    public ArrayType(NodeToken n0, NodeToken n1, NodeToken n2) {
        f0 = n0;
        f1 = n1;
        f2 = n2;
    }

    public ArrayType() {
        f0 = new NodeToken("int");
        f1 = new NodeToken("[");
        f2 = new NodeToken("]");
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

