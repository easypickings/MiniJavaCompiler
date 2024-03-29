//
// Generated by JTB 1.3.2
//

package spiglet.visitor;

import spiglet.syntaxtree.*;

import java.util.Enumeration;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJNoArguDepthFirst<R> implements GJNoArguVisitor<R> {
    //
    // Auto class visitors--probably don't need to be overridden.
    //
    @Override
    public R visit(NodeList n) {
        R _ret = null;
        int _count = 0;
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this);
            _count++;
        }
        return _ret;
    }

    @Override
    public R visit(NodeListOptional n) {
        if (n.present()) {
            R _ret = null;
            int _count = 0;
            for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
                e.nextElement().accept(this);
                _count++;
            }
            return _ret;
        } else
            return null;
    }

    @Override
    public R visit(NodeOptional n) {
        if (n.present())
            return n.node.accept(this);
        else
            return null;
    }

    @Override
    public R visit(NodeSequence n) {
        R _ret = null;
        int _count = 0;
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this);
            _count++;
        }
        return _ret;
    }

    @Override
    public R visit(NodeToken n) { return null; }

    //
    // User-generated visitor methods below
    //

    /**
     * f0 -> "MAIN"
     * f1 -> StmtList()
     * f2 -> "END"
     * f3 -> ( Procedure() )*
     * f4 -> <EOF>
     */
    @Override
    public R visit(Goal n) {
        R _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }

    /**
     * f0 -> ( ( Label() )? Stmt() )*
     */
    @Override
    public R visit(StmtList n) {
        R _ret = null;
        n.f0.accept(this);
        return _ret;
    }

    /**
     * f0 -> Label()
     * f1 -> "["
     * f2 -> IntegerLiteral()
     * f3 -> "]"
     * f4 -> StmtExp()
     */
    @Override
    public R visit(Procedure n) {
        R _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
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
     */
    @Override
    public R visit(Stmt n) {
        R _ret = null;
        n.f0.accept(this);
        return _ret;
    }

    /**
     * f0 -> "NOOP"
     */
    @Override
    public R visit(NoOpStmt n) {
        R _ret = null;
        n.f0.accept(this);
        return _ret;
    }

    /**
     * f0 -> "ERROR"
     */
    @Override
    public R visit(ErrorStmt n) {
        R _ret = null;
        n.f0.accept(this);
        return _ret;
    }

    /**
     * f0 -> "CJUMP"
     * f1 -> Temp()
     * f2 -> Label()
     */
    @Override
    public R visit(CJumpStmt n) {
        R _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

    /**
     * f0 -> "JUMP"
     * f1 -> Label()
     */
    @Override
    public R visit(JumpStmt n) {
        R _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
     * f0 -> "HSTORE"
     * f1 -> Temp()
     * f2 -> IntegerLiteral()
     * f3 -> Temp()
     */
    @Override
    public R visit(HStoreStmt n) {
        R _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        return _ret;
    }

    /**
     * f0 -> "HLOAD"
     * f1 -> Temp()
     * f2 -> Temp()
     * f3 -> IntegerLiteral()
     */
    @Override
    public R visit(HLoadStmt n) {
        R _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        return _ret;
    }

    /**
     * f0 -> "MOVE"
     * f1 -> Temp()
     * f2 -> Exp()
     */
    @Override
    public R visit(MoveStmt n) {
        R _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

    /**
     * f0 -> "PRINT"
     * f1 -> SimpleExp()
     */
    @Override
    public R visit(PrintStmt n) {
        R _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
     * f0 -> Call()
     * | HAllocate()
     * | BinOp()
     * | SimpleExp()
     */
    @Override
    public R visit(Exp n) {
        R _ret = null;
        n.f0.accept(this);
        return _ret;
    }

    /**
     * f0 -> "BEGIN"
     * f1 -> StmtList()
     * f2 -> "RETURN"
     * f3 -> SimpleExp()
     * f4 -> "END"
     */
    @Override
    public R visit(StmtExp n) {
        R _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }

    /**
     * f0 -> "CALL"
     * f1 -> SimpleExp()
     * f2 -> "("
     * f3 -> ( Temp() )*
     * f4 -> ")"
     */
    @Override
    public R visit(Call n) {
        R _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }

    /**
     * f0 -> "HALLOCATE"
     * f1 -> SimpleExp()
     */
    @Override
    public R visit(HAllocate n) {
        R _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
     * f0 -> Operator()
     * f1 -> Temp()
     * f2 -> SimpleExp()
     */
    @Override
    public R visit(BinOp n) {
        R _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

    /**
     * f0 -> "LT"
     * | "PLUS"
     * | "MINUS"
     * | "TIMES"
     */
    @Override
    public R visit(Operator n) {
        R _ret = null;
        n.f0.accept(this);
        return _ret;
    }

    /**
     * f0 -> Temp()
     * | IntegerLiteral()
     * | Label()
     */
    @Override
    public R visit(SimpleExp n) {
        R _ret = null;
        n.f0.accept(this);
        return _ret;
    }

    /**
     * f0 -> "TEMP"
     * f1 -> IntegerLiteral()
     */
    @Override
    public R visit(Temp n) {
        R _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    @Override
    public R visit(IntegerLiteral n) {
        R _ret = null;
        n.f0.accept(this);
        return _ret;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public R visit(Label n) {
        R _ret = null;
        n.f0.accept(this);
        return _ret;
    }

}
