//
// Generated by JTB 1.3.2
//

package spiglet.visitor;

import spiglet.syntaxtree.*;

/**
 * All GJ void visitors must implement this interface.
 */

public interface GJVoidVisitor<A> {

    //
    // GJ void Auto class visitors
    //

    void visit(NodeList n, A argu);

    void visit(NodeListOptional n, A argu);

    void visit(NodeOptional n, A argu);

    void visit(NodeSequence n, A argu);

    void visit(NodeToken n, A argu);

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
    void visit(Goal n, A argu);

    /**
     * f0 -> ( ( Label() )? Stmt() )*
     */
    void visit(StmtList n, A argu);

    /**
     * f0 -> Label()
     * f1 -> "["
     * f2 -> IntegerLiteral()
     * f3 -> "]"
     * f4 -> StmtExp()
     */
    void visit(Procedure n, A argu);

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
    void visit(Stmt n, A argu);

    /**
     * f0 -> "NOOP"
     */
    void visit(NoOpStmt n, A argu);

    /**
     * f0 -> "ERROR"
     */
    void visit(ErrorStmt n, A argu);

    /**
     * f0 -> "CJUMP"
     * f1 -> Temp()
     * f2 -> Label()
     */
    void visit(CJumpStmt n, A argu);

    /**
     * f0 -> "JUMP"
     * f1 -> Label()
     */
    void visit(JumpStmt n, A argu);

    /**
     * f0 -> "HSTORE"
     * f1 -> Temp()
     * f2 -> IntegerLiteral()
     * f3 -> Temp()
     */
    void visit(HStoreStmt n, A argu);

    /**
     * f0 -> "HLOAD"
     * f1 -> Temp()
     * f2 -> Temp()
     * f3 -> IntegerLiteral()
     */
    void visit(HLoadStmt n, A argu);

    /**
     * f0 -> "MOVE"
     * f1 -> Temp()
     * f2 -> Exp()
     */
    void visit(MoveStmt n, A argu);

    /**
     * f0 -> "PRINT"
     * f1 -> SimpleExp()
     */
    void visit(PrintStmt n, A argu);

    /**
     * f0 -> Call()
     * | HAllocate()
     * | BinOp()
     * | SimpleExp()
     */
    void visit(Exp n, A argu);

    /**
     * f0 -> "BEGIN"
     * f1 -> StmtList()
     * f2 -> "RETURN"
     * f3 -> SimpleExp()
     * f4 -> "END"
     */
    void visit(StmtExp n, A argu);

    /**
     * f0 -> "CALL"
     * f1 -> SimpleExp()
     * f2 -> "("
     * f3 -> ( Temp() )*
     * f4 -> ")"
     */
    void visit(Call n, A argu);

    /**
     * f0 -> "HALLOCATE"
     * f1 -> SimpleExp()
     */
    void visit(HAllocate n, A argu);

    /**
     * f0 -> Operator()
     * f1 -> Temp()
     * f2 -> SimpleExp()
     */
    void visit(BinOp n, A argu);

    /**
     * f0 -> "LT"
     * | "PLUS"
     * | "MINUS"
     * | "TIMES"
     */
    void visit(Operator n, A argu);

    /**
     * f0 -> Temp()
     * | IntegerLiteral()
     * | Label()
     */
    void visit(SimpleExp n, A argu);

    /**
     * f0 -> "TEMP"
     * f1 -> IntegerLiteral()
     */
    void visit(Temp n, A argu);

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    void visit(IntegerLiteral n, A argu);

    /**
     * f0 -> <IDENTIFIER>
     */
    void visit(Label n, A argu);

}

