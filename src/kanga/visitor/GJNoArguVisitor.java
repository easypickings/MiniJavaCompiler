//
// Generated by JTB 1.3.2
//

package kanga.visitor;

import kanga.syntaxtree.*;

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
     * f4 -> "["
     * f5 -> IntegerLiteral()
     * f6 -> "]"
     * f7 -> "["
     * f8 -> IntegerLiteral()
     * f9 -> "]"
     * f10 -> StmtList()
     * f11 -> "END"
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
     * | ALoadStmt()
     * | AStoreStmt()
     * | PassArgStmt()
     * | CallStmt()
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
     * f1 -> Reg()
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
     * f1 -> Reg()
     * f2 -> IntegerLiteral()
     * f3 -> Reg()
     */
    R visit(HStoreStmt n);

    /**
     * f0 -> "HLOAD"
     * f1 -> Reg()
     * f2 -> Reg()
     * f3 -> IntegerLiteral()
     */
    R visit(HLoadStmt n);

    /**
     * f0 -> "MOVE"
     * f1 -> Reg()
     * f2 -> Exp()
     */
    R visit(MoveStmt n);

    /**
     * f0 -> "PRINT"
     * f1 -> SimpleExp()
     */
    R visit(PrintStmt n);

    /**
     * f0 -> "ALOAD"
     * f1 -> Reg()
     * f2 -> SpilledArg()
     */
    R visit(ALoadStmt n);

    /**
     * f0 -> "ASTORE"
     * f1 -> SpilledArg()
     * f2 -> Reg()
     */
    R visit(AStoreStmt n);

    /**
     * f0 -> "PASSARG"
     * f1 -> IntegerLiteral()
     * f2 -> Reg()
     */
    R visit(PassArgStmt n);

    /**
     * f0 -> "CALL"
     * f1 -> SimpleExp()
     */
    R visit(CallStmt n);

    /**
     * f0 -> HAllocate()
     * | BinOp()
     * | SimpleExp()
     */
    R visit(Exp n);

    /**
     * f0 -> "HALLOCATE"
     * f1 -> SimpleExp()
     */
    R visit(HAllocate n);

    /**
     * f0 -> Operator()
     * f1 -> Reg()
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
     * f0 -> "SPILLEDARG"
     * f1 -> IntegerLiteral()
     */
    R visit(SpilledArg n);

    /**
     * f0 -> Reg()
     * | IntegerLiteral()
     * | Label()
     */
    R visit(SimpleExp n);

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
    R visit(Reg n);

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    R visit(IntegerLiteral n);

    /**
     * f0 -> <IDENTIFIER>
     */
    R visit(Label n);

}

