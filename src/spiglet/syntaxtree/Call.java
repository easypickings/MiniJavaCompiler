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
 * f0 -> "CALL"
 * f1 -> SimpleExp()
 * f2 -> "("
 * f3 -> ( Temp() )*
 * f4 -> ")"
 */
public class Call implements Node {
    private static final long serialVersionUID = 747685503949246918L;
    public NodeToken f0;
    public SimpleExp f1;
    public NodeToken f2;
    public NodeListOptional f3;
    public NodeToken f4;

    public Call(NodeToken n0, SimpleExp n1, NodeToken n2, NodeListOptional n3
            , NodeToken n4) {
        f0 = n0;
        f1 = n1;
        f2 = n2;
        f3 = n3;
        f4 = n4;
    }

    public Call(SimpleExp n0, NodeListOptional n1) {
        f0 = new NodeToken("CALL");
        f1 = n0;
        f2 = new NodeToken("(");
        f3 = n1;
        f4 = new NodeToken(")");
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

