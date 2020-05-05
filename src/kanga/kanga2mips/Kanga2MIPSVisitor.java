package kanga.kanga2mips;

import kanga.syntaxtree.*;
import kanga.visitor.GJVoidDepthFirst;

import java.util.Enumeration;

import static kanga.kanga2mips.MIPSWriter.*;

public class Kanga2MIPSVisitor extends GJVoidDepthFirst<String> {
    //
    // Auto class visitors--probably don't need to be overridden.
    //
    @Override
    public void visit(NodeList n, String argu) {
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this, argu);
    }

    @Override
    public void visit(NodeListOptional n, String argu) {
        if (n.present())
            for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
                e.nextElement().accept(this, argu);
    }

    @Override
    public void visit(NodeOptional n, String argu) {
        if (n.present())
            n.node.accept(this, argu);
    }

    @Override
    public void visit(NodeSequence n, String argu) {
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this, argu);
    }

    @Override
    public void visit(NodeToken n, String argu) {}

    //
    // User-generated visitor methods below
    //

    /**
     * f0 -> "MAIN"
     * f1 -> "["
     * f2 -> IntegerLiteral()
     * f3 -> "]"
     * f4 -> "["
     * f5 -> IntegerLiteral()
     * f6 -> "]"
     * f7 -> "["
     * f8 -> IntegerLiteral()
     * f9 -> "]"
     * f10 -> StmtList()
     * f11 -> "END"
     * f12 -> ( Procedure() )*
     * f13 -> <EOF>
     */
    @Override
    public void visit(Goal n, String argu) {
        int stacks = I(n.f5);
        begin("main", stacks);
        n.f10.accept(this, argu);
        end(stacks);
        n.f12.accept(this, argu);
        halloc();
        print();
        error();
        endl();
        strerr();
    }

    /**
     * f0 -> ( ( Label() )? Stmt() )*
     */
    @Override
    public void visit(StmtList n, String argu) {
        n.f0.accept(this, argu);
    }

    /**
     * f0 -> Label()
     * f1 -> "["
     * f2 -> IntegerLiteral()
     * f3 -> "]"
     * f4 -> "["
     * f5 -> IntegerLiteral()
     * f6 -> "]"
     * f7 -> "["
     * f8 -> IntegerLiteral()
     * f9 -> "]"
     * f10 -> StmtList()
     * f11 -> "END"
     */
    @Override
    public void visit(Procedure n, String argu) {
        int stacks = I(n.f5);
        begin(n.f0.f0.tokenImage, stacks);
        n.f10.accept(this, argu);
        end(stacks);
    }

    /**
     * f0 -> NoOpStmt()
     * | ErrorStmt()
     * | CJumpStmt()
     * | JumpStmt()
     * | HStoreStmt()
     * | HLoadStmt()
     * | MoveStmt()
     * | PrintStmt()
     * | ALoadStmt()
     * | AStoreStmt()
     * | PassArgStmt()
     * | CallStmt()
     */
    @Override
    public void visit(Stmt n, String argu) {
        n.f0.accept(this, argu);
    }

    /**
     * f0 -> "NOOP"
     */
    @Override
    public void visit(NoOpStmt n, String argu) {}

    /**
     * f0 -> "ERROR"
     */
    @Override
    public void visit(ErrorStmt n, String argu) {
        j("_error");
    }

    /**
     * f0 -> "CJUMP"
     * f1 -> Reg()
     * f2 -> Label()
     */
    @Override
    public void visit(CJumpStmt n, String argu) {
        bne(R(n.f1), L(n.f2));
    }

    /**
     * f0 -> "JUMP"
     * f1 -> Label()
     */
    @Override
    public void visit(JumpStmt n, String argu) {
        b(L(n.f1));
    }

    /**
     * f0 -> "HSTORE"
     * f1 -> Reg()
     * f2 -> IntegerLiteral()
     * f3 -> Reg()
     */
    @Override
    public void visit(HStoreStmt n, String argu) {
        sw(R(n.f3), R(n.f1), I(n.f2));
    }

    /**
     * f0 -> "HLOAD"
     * f1 -> Reg()
     * f2 -> Reg()
     * f3 -> IntegerLiteral()
     */
    @Override
    public void visit(HLoadStmt n, String argu) {
        lw(R(n.f1), R(n.f2), I(n.f3));
    }

    /**
     * f0 -> "MOVE"
     * f1 -> Reg()
     * f2 -> Exp()
     */
    @Override
    public void visit(MoveStmt n, String argu) {
        String dst = n.f1.f0.choice.toString();
        n.f2.accept(this, dst);
    }

    /**
     * f0 -> "PRINT"
     * f1 -> SimpleExp()
     */
    @Override
    public void visit(PrintStmt n, String argu) {
        n.f1.accept(this, "a0");
        jal("_print");
    }

    /**
     * f0 -> "ALOAD"
     * f1 -> Reg()
     * f2 -> SpilledArg()
     */
    @Override
    public void visit(ALoadStmt n, String argu) {
        lw(R(n.f1), "fp", (-I(n.f2.f1) - 3) * 4);
    }

    /**
     * f0 -> "ASTORE"
     * f1 -> SpilledArg()
     * f2 -> Reg()
     */
    @Override
    public void visit(AStoreStmt n, String argu) {
        sw(R(n.f2), "fp", (-I(n.f1.f1) - 3) * 4);
    }

    /**
     * f0 -> "PASSARG"
     * f1 -> IntegerLiteral()
     * f2 -> Reg()
     */
    @Override
    public void visit(PassArgStmt n, String argu) {
        sw(R(n.f2), "sp", (-I(n.f1) - 2) * 4);
    }

    /**
     * f0 -> "CALL"
     * f1 -> SimpleExp()
     */
    @Override
    public void visit(CallStmt n, String argu) {
        Node exp = n.f1.f0.choice;
        if (exp instanceof Reg) jalr(R((Reg) exp));
        else if (exp instanceof IntegerLiteral) // possible?
            jal(((IntegerLiteral) exp).f0.tokenImage);
        else if (exp instanceof Label) jal(L((Label) exp));
    }

    /**
     * f0 -> HAllocate()
     * | BinOp()
     * | SimpleExp()
     */
    @Override
    public void visit(Exp n, String argu) {
        n.f0.accept(this, argu);
    }

    /**
     * f0 -> "HALLOCATE"
     * f1 -> SimpleExp()
     */
    @Override
    public void visit(HAllocate n, String argu) {
        n.f1.accept(this, "a0");
        jal("_halloc");
        move(argu, "v0");
    }

    /**
     * f0 -> Operator()
     * f1 -> Reg()
     * f2 -> SimpleExp()
     */
    @Override
    public void visit(BinOp n, String argu) {
        String op = n.f0.f0.choice.toString();
        String src1 = R(n.f1), src2 = null;
        int srci = 0;
        boolean imm = false, store = false;
        Node exp = n.f2.f0.choice;

        if (exp instanceof Reg) src2 = R((Reg) exp);
        else if (exp instanceof IntegerLiteral) {
            srci = I((IntegerLiteral) exp);
            imm = true;
        } else if (exp instanceof Label) {
            /* load exp to a temporary register first */
            src2 = reg(argu, src1);
            la(src2, L((Label) exp));
            store = true;
        }

        if (store) push(src2);
        switch (op) {
            case "LT" -> {
                if (imm) slti(argu, src1, srci);
                else slt(argu, src1, src2);
            }
            case "PLUS" -> {
                if (imm) addi(argu, src1, srci);
                else add(argu, src1, src2);
            }
            case "MINUS" -> {
                if (imm) subi(argu, src1, srci);
                else sub(argu, src1, src2);
            }
            case "TIMES" -> {
                if (imm) muli(argu, src1, srci);
                else mul(argu, src1, src2);
            }
        }
        if (store) pop(src2);
    }

    /**
     * f0 -> Reg()
     * | IntegerLiteral()
     * | Label()
     */
    @Override
    public void visit(SimpleExp n, String argu) {
        Node exp = n.f0.choice;
        if (exp instanceof Reg)
            move(argu, R((Reg) exp));
        else if (exp instanceof IntegerLiteral)
            li(argu, I((IntegerLiteral) exp));
        else if (exp instanceof Label)
            la(argu, L((Label) exp));
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public void visit(Label n, String argu) {
        label(n.f0.tokenImage);
    }

    protected String R(Reg n) {
        return n.f0.choice.toString();
    }

    protected int I(IntegerLiteral n) {
        return Integer.parseInt(n.f0.tokenImage);
    }

    protected String L(Label n) {
        return n.f0.tokenImage;
    }

}
