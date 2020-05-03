package spiglet.spiglet2kanga;

import spiglet.flow.Graph;
import spiglet.syntaxtree.*;
import spiglet.visitor.DepthFirstVisitor;
import spiglet.visitor.GJDepthFirst;

import java.util.Enumeration;

import static spiglet.spiglet2kanga.KangaWriter.*;

public class Spiglet2KangaVisitor extends GJDepthFirst<String, Graph> {
    //
    // Auto class visitors--probably don't need to be overridden.
    //
    @Override
    public String visit(NodeList n, Graph argu) {
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this, argu);
        return null;
    }

    @Override
    public String visit(NodeListOptional n, Graph argu) {
        if (n.present())
            for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
                e.nextElement().accept(this, argu);
        return null;
    }

    @Override
    public String visit(NodeOptional n, Graph argu) {
        if (n.present())
            return n.node.accept(this, argu);
        else
            return null;
    }

    @Override
    public String visit(NodeSequence n, Graph argu) {
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this, argu);
        return null;
    }

    @Override
    public String visit(NodeToken n, Graph argu) { return null; }

    //
    // User-generated visitor methods below
    //

    /**
     * f0 -> NoOpStmt()
     * | ErrorStmt()
     * | CJumpStmt()
     * | JumpStmt()
     * | HStoreStmt()
     * | HLoadStmt()
     * | MoveStmt()
     * | PrintStmt()
     */
    @Override
    public String visit(Stmt n, Graph argu) {
        n.f0.accept(this, argu);
        return null;
    }

    /**
     * f0 -> "NOOP"
     */
    @Override
    public String visit(NoOpStmt n, Graph argu) {
        return null;
    }

    /**
     * f0 -> "ERROR"
     */
    @Override
    public String visit(ErrorStmt n, Graph argu) {
        error();
        return null;
    }

    /**
     * f0 -> "CJUMP"
     * f1 -> Temp()
     * f2 -> Label()
     */
    @Override
    public String visit(CJumpStmt n, Graph argu) {
        int temp = Integer.parseInt(n.f1.f1.f0.tokenImage),
                reg = argu.getReg(temp, v1);
        String label = n.f2.f0.tokenImage;
        jump(argu.getLabel(label), reg);
        return null;
    }

    /**
     * f0 -> "JUMP"
     * f1 -> Label()
     */
    @Override
    public String visit(JumpStmt n, Graph argu) {
        String label = n.f1.f0.tokenImage;
        jump(argu.getLabel(label), -1);
        return null;
    }

    /**
     * f0 -> "HSTORE"
     * f1 -> Temp()
     * f2 -> IntegerLiteral()
     * f3 -> Temp()
     */
    @Override
    public String visit(HStoreStmt n, Graph argu) {
        int temp, addr, offset, val;

        temp = Integer.parseInt(n.f1.f1.f0.tokenImage);
        addr = argu.getReg(temp, v1);
        offset = Integer.parseInt(n.f2.f0.tokenImage);
        temp = Integer.parseInt(n.f3.f1.f0.tokenImage);
        val = argu.getReg(temp, v0);

        hstore(addr, offset, val);
        return null;
    }

    /**
     * f0 -> "HLOAD"
     * f1 -> Temp()
     * f2 -> Temp()
     * f3 -> IntegerLiteral()
     */
    @Override
    public String visit(HLoadStmt n, Graph argu) {
        int temp, reg, addr, offset;

        temp = Integer.parseInt(n.f2.f1.f0.tokenImage);
        addr = argu.getReg(temp, v0);
        offset = Integer.parseInt(n.f3.f0.tokenImage);
        temp = Integer.parseInt(n.f1.f1.f0.tokenImage);
        reg = argu.getReg(temp);
        if (reg < 0) reg = v1;

        hload(reg, addr, offset);
        /* if temp f1 is spilled, store back to stack */
        if (reg == v1) astore(argu.getSpill(temp), v1);
        return null;
    }

    /**
     * f0 -> "MOVE"
     * f1 -> Temp()
     * f2 -> Exp()
     */
    @Override
    public String visit(MoveStmt n, Graph argu) {
        String exp = n.f2.accept(this, argu);
        int temp = Integer.parseInt(n.f1.f1.f0.tokenImage),
                reg = argu.getReg(temp, v1);
        move(reg, exp);
        /* if temp f1 is spilled, store back to stack */
        if (reg == v1) astore(argu.getSpill(temp), v1);
        return null;
    }

    /**
     * f0 -> "PRINT"
     * f1 -> SimpleExp()
     */
    @Override
    public String visit(PrintStmt n, Graph argu) {
        print(n.f1.accept(this, argu));
        return null;
    }

    /**
     * f0 -> Call()
     * | HAllocate()
     * | BinOp()
     * | SimpleExp()
     */
    @Override
    public String visit(Exp n, Graph argu) {
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> "CALL"
     * f1 -> SimpleExp()
     * f2 -> "("
     * f3 -> ( Temp() )*
     * f4 -> ")"
     */
    @Override
    public String visit(Call n, Graph argu) {
        int[] args = {0};
        class ArgsVisitor extends DepthFirstVisitor {
            @Override
            public void visit(Temp n) {
                int temp = Integer.parseInt(n.f1.f0.tokenImage),
                        reg = argu.getReg(temp, v1);
                if (args[0] < 4) move(a0 + args[0], Reg(reg));
                else passarg(args[0] - 3, reg);
                ++args[0];
            }
        }

        n.f3.accept(new ArgsVisitor());
        call(n.f1.accept(this, argu));
        return "v0";
    }

    /**
     * f0 -> "HALLOCATE"
     * f1 -> SimpleExp()
     */
    @Override
    public String visit(HAllocate n, Graph argu) {
        return "HALLOCATE " + n.f1.accept(this, argu);
    }

    /**
     * f0 -> Operator()
     * f1 -> Temp()
     * f2 -> SimpleExp()
     */
    @Override
    public String visit(BinOp n, Graph argu) {

        String op = n.f0.f0.choice.toString();
        int temp = Integer.parseInt(n.f1.f1.f0.tokenImage),
                reg = argu.getReg(temp, v1);
        String exp = n.f2.accept(this, argu);
        return op + " " + Reg(reg) + " " + exp;
    }

    /**
     * f0 -> Temp()
     * | IntegerLiteral()
     * | Label()
     */
    @Override
    public String visit(SimpleExp n, Graph argu) {
        Node t = n.f0.choice;
        if (t instanceof Temp) {
            int temp = Integer.parseInt(((Temp) t).f1.f0.tokenImage),
                    reg = argu.getReg(temp, v1);
            return Reg(reg);
        } else if (t instanceof IntegerLiteral)
            return ((IntegerLiteral) t).f0.tokenImage;
        else if (t instanceof Label)
            return ((Label) t).f0.tokenImage;

        return null;
    }

}
