//
// Generated by JTB 1.3.2
//

package spiglet.visitor;

import spiglet.syntaxtree.*;

/**
 * All GJ visitors must implement this interface.
 */

public interface GJVisitor<R, A> {

    //
    // GJ Auto class visitors
    //

    R visit(NodeList n, A argu);

    R visit(NodeListOptional n, A argu);

    R visit(NodeOptional n, A argu);

    R visit(NodeSequence n, A argu);

    R visit(NodeToken n, A argu);

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
    R visit(Goal n, A argu);

    /**
     * f0 -> ( ( Label() )? Stmt() )*
     */
    R visit(StmtList n, A argu);

    /**
     * f0 -> Label()
     * f1 -> "["
     * f2 -> IntegerLiteral()
     * f3 -> "]"
     * f4 -> StmtExp()
     */
    R visit(Procedure n, A argu);

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
    R visit(Stmt n, A argu);

    /**
     * f0 -> "NOOP"
     */
    R visit(NoOpStmt n, A argu);

    /**
     * f0 -> "ERROR"
     */
    R visit(ErrorStmt n, A argu);

    /**
     * f0 -> "CJUMP"
     * f1 -> Temp()
     * f2 -> Label()
     */
    R visit(CJumpStmt n, A argu);

    /**
     * f0 -> "JUMP"
     * f1 -> Label()
     */
    R visit(JumpStmt n, A argu);

    /**
     * f0 -> "HSTORE"
     * f1 -> Temp()
     * f2 -> IntegerLiteral()
     * f3 -> Temp()
     */
    R visit(HStoreStmt n, A argu);

    /**
     * f0 -> "HLOAD"
     * f1 -> Temp()
     * f2 -> Temp()
     * f3 -> IntegerLiteral()
     */
    R visit(HLoadStmt n, A argu);

    /**
     * f0 -> "MOVE"
     * f1 -> Temp()
     * f2 -> Exp()
     */
    R visit(MoveStmt n, A argu);

    /**
     * f0 -> "PRINT"
     * f1 -> SimpleExp()
     */
    R visit(PrintStmt n, A argu);

    /**
     * f0 -> Call()
     * | HAllocate()
     * | BinOp()
     * | SimpleExp()
     */
    R visit(Exp n, A argu);

    /**
     * f0 -> "BEGIN"
     * f1 -> StmtList()
     * f2 -> "RETURN"
     * f3 -> SimpleExp()
     * f4 -> "END"
     */
    R visit(StmtExp n, A argu);

    /**
     * f0 -> "CALL"
     * f1 -> SimpleExp()
     * f2 -> "("
     * f3 -> ( Temp() )*
     * f4 -> ")"
     */
    R visit(Call n, A argu);

    /**
     * f0 -> "HALLOCATE"
     * f1 -> SimpleExp()
     */
    R visit(HAllocate n, A argu);

    /**
     * f0 -> Operator()
     * f1 -> Temp()
     * f2 -> SimpleExp()
     */
    R visit(BinOp n, A argu);

    /**
     * f0 -> "LT"
     * | "PLUS"
     * | "MINUS"
     * | "TIMES"
     */
    R visit(Operator n, A argu);

    /**
     * f0 -> Temp()
     * | IntegerLiteral()
     * | Label()
     */
    R visit(SimpleExp n, A argu);

    /**
     * f0 -> "TEMP"
     * f1 -> IntegerLiteral()
     */
    R visit(Temp n, A argu);

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    R visit(IntegerLiteral n, A argu);

    /**
     * f0 -> <IDENTIFIER>
     */
    R visit(Label n, A argu);

}
