package spiglet.spiglet2kanga;

import spiglet.flow.Graph;
import spiglet.flow.Statement;
import spiglet.syntaxtree.*;
import spiglet.visitor.GJVoidDepthFirst;

import java.util.Enumeration;

public class BuildGraphVisitor extends GJVoidDepthFirst<Graph> {

    /**
     * Current statement to which the visiting nodes belong
     */
    protected Statement cur;

    //
    // Auto class visitors--probably don't need to be overridden.
    //
    @Override
    public void visit(NodeList n, Graph argu) {
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this, argu);
    }

    @Override
    public void visit(NodeListOptional n, Graph argu) {
        if (n.present())
            for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
                e.nextElement().accept(this, argu);
    }

    @Override
    public void visit(NodeOptional n, Graph argu) {
        if (n.present())
            n.node.accept(this, argu);
    }

    @Override
    public void visit(NodeSequence n, Graph argu) {
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this, argu);
    }

    @Override
    public void visit(NodeToken n, Graph argu) {}

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
    public void visit(Goal n, Graph argu) {
        Graph graph = new Graph("MAIN", 0);
        n.f1.accept(this, graph);
        graph.BuildControlFlow();
        graph.AllocateReg();

        n.f3.accept(this, null);
    }

    /**
     * f0 -> ( ( Label() )? Stmt() )*
     */
    @Override
    public void visit(StmtList n, Graph argu) {
        n.f0.accept(this, argu);
    }

    /**
     * f0 -> Label()
     * f1 -> "["
     * f2 -> IntegerLiteral()
     * f3 -> "]"
     * f4 -> StmtExp()
     */
    @Override
    public void visit(Procedure n, Graph argu) {
        String name = n.f0.f0.tokenImage;
        int args = Integer.parseInt(n.f2.f0.tokenImage);

        Graph graph = new Graph(name, args);
        n.f4.accept(this, graph);
        graph.BuildControlFlow();
        graph.AllocateReg();

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
    public void visit(Stmt n, Graph argu) {
        cur = new Statement(); // new Statement
        n.f0.accept(this, argu);
    }

    /**
     * f0 -> "NOOP"
     */
    @Override
    public void visit(NoOpStmt n, Graph argu) {
        argu.addStatement(cur);
    }

    /**
     * f0 -> "ERROR"
     */
    @Override
    public void visit(ErrorStmt n, Graph argu) {
        argu.addStatement(cur);
        argu.setJump("-1", true); // label "-1" denotes exit block
    }

    /**
     * f0 -> "CJUMP"
     * f1 -> Temp()
     * f2 -> Label()
     */
    @Override
    public void visit(CJumpStmt n, Graph argu) {
        cur.addUse(Integer.parseInt(n.f1.f1.f0.tokenImage)); // Temp is used
        argu.addStatement(cur);
        argu.setJump(n.f2.f0.tokenImage, false); // don't unlink pre and cur
    }

    /**
     * f0 -> "JUMP"
     * f1 -> Label()
     */
    @Override
    public void visit(JumpStmt n, Graph argu) {
        argu.addStatement(cur);
        argu.setJump(n.f1.f0.tokenImage, true);
    }

    /**
     * f0 -> "HSTORE"
     * f1 -> Temp()
     * f2 -> IntegerLiteral()
     * f3 -> Temp()
     */
    @Override
    public void visit(HStoreStmt n, Graph argu) {
        cur.addUse(Integer.parseInt(n.f1.f1.f0.tokenImage)); // Temp is used
        cur.addUse(Integer.parseInt(n.f3.f1.f0.tokenImage)); // Temp is used
        argu.addStatement(cur);
    }

    /**
     * f0 -> "HLOAD"
     * f1 -> Temp()
     * f2 -> Temp()
     * f3 -> IntegerLiteral()
     */
    @Override
    public void visit(HLoadStmt n, Graph argu) {
        cur.addUse(Integer.parseInt(n.f2.f1.f0.tokenImage)); // Temp is used
        cur.addDef(Integer.parseInt(n.f1.f1.f0.tokenImage)); // Temp is defined
        argu.addStatement(cur);
    }

    /**
     * f0 -> "MOVE"
     * f1 -> Temp()
     * f2 -> Exp()
     */
    @Override
    public void visit(MoveStmt n, Graph argu) {
        n.f2.accept(this, argu);
        cur.addDef(Integer.parseInt(n.f1.f1.f0.tokenImage)); // Temp is defined
        argu.addStatement(cur);
    }

    /**
     * f0 -> "PRINT"
     * f1 -> SimpleExp()
     */
    @Override
    public void visit(PrintStmt n, Graph argu) {
        n.f1.accept(this, argu);
        argu.addStatement(cur);
    }

    /**
     * f0 -> Call()
     * | HAllocate()
     * | BinOp()
     * | SimpleExp()
     */
    @Override
    public void visit(Exp n, Graph argu) {
        n.f0.accept(this, argu);
    }

    /**
     * f0 -> "BEGIN"
     * f1 -> StmtList()
     * f2 -> "RETURN"
     * f3 -> SimpleExp()
     * f4 -> "END"
     */
    @Override
    public void visit(StmtExp n, Graph argu) {
        n.f1.accept(this, argu);

        cur = new Statement(); // RETURN is treated as a statement
        n.f3.accept(this, argu);
        argu.addStatement(cur);
    }

    /**
     * f0 -> "CALL"
     * f1 -> SimpleExp()
     * f2 -> "("
     * f3 -> ( Temp() )*
     * f4 -> ")"
     */
    @Override
    public void visit(Call n, Graph argu) {
        cur.setCall();
        n.f1.accept(this, argu);

        argu.setCallArgs(n.f3.size()); // update callArgs

        n.f3.accept(this, argu); // visit Temp()
    }

    /**
     * f0 -> "HALLOCATE"
     * f1 -> SimpleExp()
     */
    @Override
    public void visit(HAllocate n, Graph argu) {
        n.f1.accept(this, argu);
    }

    /**
     * f0 -> Operator()
     * f1 -> Temp()
     * f2 -> SimpleExp()
     */
    @Override
    public void visit(BinOp n, Graph argu) {
        cur.addUse(Integer.parseInt(n.f1.f1.f0.tokenImage)); // Temp is used
        n.f2.accept(this, argu);
    }

    /**
     * f0 -> Temp()
     * | IntegerLiteral()
     * | Label()
     */
    @Override
    public void visit(SimpleExp n, Graph argu) {
        Node t = n.f0.choice;
        if (t instanceof Temp)
            cur.addUse(Integer.parseInt(((Temp) t).f1.f0.tokenImage));
    }

    /**
     * f0 -> "TEMP"
     * f1 -> IntegerLiteral()
     */
    @Override
    public void visit(Temp n, Graph argu) {
        /* Can only be Call() -> Temp(), so Temp is to be used */
        cur.addUse(Integer.parseInt(n.f1.f0.tokenImage)); // Temp is used
    }


    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public void visit(Label n, Graph argu) {
        /* Can only be StmtList() -> Label(), treated as block's label */
        argu.NewLabelBlock(n.f0.tokenImage); // start a new block with label
    }

}
