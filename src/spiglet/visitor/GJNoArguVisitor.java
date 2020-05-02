//
// Generated by JTB 1.3.2
//

package spiglet.visitor;

import spiglet.syntaxtree.*;

/**
 * All GJ visitors with no argument must implement this interface.
 */

public interface GJNoArguVisitor<R> {

    //
    // GJ Auto class visitors with no argument
    //

    R visit(NodeList n);

    R visit(NodeListOptional n);

    R visit(NodeOptional n);

    R visit(NodeSequence n);

    R visit(NodeToken n);

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
    R visit(Goal n);

    /**
     * f0 -> ( ( Label() )? Stmt() )*
     */
    R visit(StmtList n);

    /**
     * f0 -> Label()
     * f1 -> "["
     * f2 -> IntegerLiteral()
     * f3 -> "]"
     * f4 -> StmtExp()
     */
    R visit(Procedure n);

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
    R visit(Stmt n);

    /**
     * f0 -> "NOOP"
     */
    R visit(NoOpStmt n);

    /**
     * f0 -> "ERROR"
     */
    R visit(ErrorStmt n);

    /**
     * f0 -> "CJUMP"
     * f1 -> Temp()
     * f2 -> Label()
     */
    R visit(CJumpStmt n);

    /**
     * f0 -> "JUMP"
     * f1 -> Label()
     */
    R visit(JumpStmt n);

    /**
     * f0 -> "HSTORE"
     * f1 -> Temp()
     * f2 -> IntegerLiteral()
     * f3 -> Temp()
     */
    R visit(HStoreStmt n);

    /**
     * f0 -> "HLOAD"
     * f1 -> Temp()
     * f2 -> Temp()
     * f3 -> IntegerLiteral()
     */
    R visit(HLoadStmt n);

    /**
     * f0 -> "MOVE"
     * f1 -> Temp()
     * f2 -> Exp()
     */
    R visit(MoveStmt n);

    /**
     * f0 -> "PRINT"
     * f1 -> SimpleExp()
     */
    R visit(PrintStmt n);

    /**
     * f0 -> Call()
     * | HAllocate()
     * | BinOp()
     * | SimpleExp()
     */
    R visit(Exp n);

    /**
     * f0 -> "BEGIN"
     * f1 -> StmtList()
     * f2 -> "RETURN"
     * f3 -> SimpleExp()
     * f4 -> "END"
     */
    R visit(StmtExp n);

    /**
     * f0 -> "CALL"
     * f1 -> SimpleExp()
     * f2 -> "("
     * f3 -> ( Temp() )*
     * f4 -> ")"
     */
    R visit(Call n);

    /**
     * f0 -> "HALLOCATE"
     * f1 -> SimpleExp()
     */
    R visit(HAllocate n);

    /**
     * f0 -> Operator()
     * f1 -> Temp()
     * f2 -> SimpleExp()
     */
    R visit(BinOp n);

    /**
     * f0 -> "LT"
     * | "PLUS"
     * | "MINUS"
     * | "TIMES"
     */
    R visit(Operator n);

    /**
     * f0 -> Temp()
     * | IntegerLiteral()
     * | Label()
     */
    R visit(SimpleExp n);

    /**
     * f0 -> "TEMP"
     * f1 -> IntegerLiteral()
     */
    R visit(Temp n);

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    R visit(IntegerLiteral n);

    /**
     * f0 -> <IDENTIFIER>
     */
    R visit(Label n);

}

