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
 * f0 -> "ALOAD"
 * f1 -> Reg()
 * f2 -> SpilledArg()
 */
public class ALoadStmt implements Node {
    private static final long serialVersionUID = -4296396940352567363L;
    public NodeToken f0;
    public Reg f1;
    public SpilledArg f2;

    public ALoadStmt(NodeToken n0, Reg n1, SpilledArg n2) {
        f0 = n0;
        f1 = n1;
        f2 = n2;
    }

    public ALoadStmt(Reg n0, SpilledArg n1) {
        f0 = new NodeToken("ALOAD");
        f1 = n0;
        f2 = n1;
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
