//
// Generated by JTB 1.3.2
//

package kanga.visitor;

import kanga.syntaxtree.*;

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
     * f4 -> "["
     * f5 -> IntegerLiteral()
     * f6 -> "]"
     * f7 -> "["
     * f8 -> IntegerLiteral()
     * f9 -> "]"
     * f10 -> StmtList()
     * f11 -> "END"
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
     * | ALoadStmt()
     * | AStoreStmt()
     * | PassArgStmt()
     * | CallStmt()
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
     * f1 -> Reg()
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
     * f1 -> Reg()
     * f2 -> IntegerLiteral()
     * f3 -> Reg()
     */
    void visit(HStoreStmt n, A argu);

    /**
     * f0 -> "HLOAD"
     * f1 -> Reg()
     * f2 -> Reg()
     * f3 -> IntegerLiteral()
     */
    void visit(HLoadStmt n, A argu);

    /**
     * f0 -> "MOVE"
     * f1 -> Reg()
     * f2 -> Exp()
     */
    void visit(MoveStmt n, A argu);

    /**
     * f0 -> "PRINT"
     * f1 -> SimpleExp()
     */
    void visit(PrintStmt n, A argu);

    /**
     * f0 -> "ALOAD"
     * f1 -> Reg()
     * f2 -> SpilledArg()
     */
    void visit(ALoadStmt n, A argu);

    /**
     * f0 -> "ASTORE"
     * f1 -> SpilledArg()
     * f2 -> Reg()
     */
    void visit(AStoreStmt n, A argu);

    /**
     * f0 -> "PASSARG"
     * f1 -> IntegerLiteral()
     * f2 -> Reg()
     */
    void visit(PassArgStmt n, A argu);

    /**
     * f0 -> "CALL"
     * f1 -> SimpleExp()
     */
    void visit(CallStmt n, A argu);

    /**
     * f0 -> HAllocate()
     * | BinOp()
     * | SimpleExp()
     */
    void visit(Exp n, A argu);

    /**
     * f0 -> "HALLOCATE"
     * f1 -> SimpleExp()
     */
    void visit(HAllocate n, A argu);

    /**
     * f0 -> Operator()
     * f1 -> Reg()
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
     * f0 -> "SPILLEDARG"
     * f1 -> IntegerLiteral()
     */
    void visit(SpilledArg n, A argu);

    /**
     * f0 -> Reg()
     * | IntegerLiteral()
     * | Label()
     */
    void visit(SimpleExp n, A argu);

    /**
     * f0 -> "a0"
     * | "a1"
     * | "a2"
     * | "a3"
     * | "t0"
     * | "t1"
     * | "t2"
     * | "t3"
     * | "t4"
     * | "t5"
     * | "t6"
     * | "t7"
     * | "s0"
     * | "s1"
     * | "s2"
     * | "s3"
     * | "s4"
     * | "s5"
     * | "s6"
     * | "s7"
     * | "t8"
     * | "t9"
     * | "v0"
     * | "v1"
     */
    void visit(Reg n, A argu);

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    void visit(IntegerLiteral n, A argu);

    /**
     * f0 -> <IDENTIFIER>
     */
    void visit(Label n, A argu);

}

