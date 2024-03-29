//
// Generated by JTB 1.3.2
//

package minijava.visitor;

import minijava.syntaxtree.*;

import java.util.Enumeration;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class DepthFirstVisitor implements Visitor {
    //
    // Auto class visitors--probably don't need to be overridden.
    //
    @Override
    public void visit(NodeList n) {
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this);
    }

    @Override
    public void visit(NodeListOptional n) {
        if (n.present())
            for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
                e.nextElement().accept(this);
    }

    @Override
    public void visit(NodeOptional n) {
        if (n.present())
            n.node.accept(this);
    }

    @Override
    public void visit(NodeSequence n) {
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this);
    }

    @Override
    public void visit(NodeToken n) { }

    //
    // User-generated visitor methods below
    //

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    @Override
    public void visit(Goal n) {
        System.out.println("Goal");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> "public"
     * f4 -> "static"
     * f5 -> "void"
     * f6 -> "main"
     * f7 -> "("
     * f8 -> "String"
     * f9 -> "["
     * f10 -> "]"
     * f11 -> Identifier()
     * f12 -> ")"
     * f13 -> "{"
     * f14 -> ( VarDeclaration() )*
     * f15 -> ( Statement() )*
     * f16 -> "}"
     * f17 -> "}"
     */
    @Override
    public void visit(MainClass n) {
        System.out.println("MainClass");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
        n.f7.accept(this);
        n.f8.accept(this);
        n.f9.accept(this);
        n.f10.accept(this);
        n.f11.accept(this);
        n.f12.accept(this);
        n.f13.accept(this);
        n.f14.accept(this);
        n.f15.accept(this);
        n.f16.accept(this);
        n.f17.accept(this);
    }

    /**
     * f0 -> ClassDeclaration()
     * | ClassExtendsDeclaration()
     */
    @Override
    public void visit(TypeDeclaration n) {
        System.out.println("TypeDeclaration");
        n.f0.accept(this);
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> ( VarDeclaration() )*
     * f4 -> ( MethodDeclaration() )*
     * f5 -> "}"
     */
    @Override
    public void visit(ClassDeclaration n) {
        System.out.println("ClassDeclaration");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "extends"
     * f3 -> Identifier()
     * f4 -> "{"
     * f5 -> ( VarDeclaration() )*
     * f6 -> ( MethodDeclaration() )*
     * f7 -> "}"
     */
    @Override
    public void visit(ClassExtendsDeclaration n) {
        System.out.println("ClassExtendsDeclaration");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
        n.f7.accept(this);
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    @Override
    public void visit(VarDeclaration n) {
        System.out.println("VarDeclaration");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
    }

    /**
     * f0 -> "public"
     * f1 -> Type()
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( FormalParameterList() )?
     * f5 -> ")"
     * f6 -> "{"
     * f7 -> ( VarDeclaration() )*
     * f8 -> ( Statement() )*
     * f9 -> "return"
     * f10 -> Expression()
     * f11 -> ";"
     * f12 -> "}"
     */
    @Override
    public void visit(MethodDeclaration n) {
        System.out.println("MethodDeclaration");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
        n.f7.accept(this);
        n.f8.accept(this);
        n.f9.accept(this);
        n.f10.accept(this);
        n.f11.accept(this);
        n.f12.accept(this);
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> ( FormalParameterRest() )*
     */
    @Override
    public void visit(FormalParameterList n) {
        System.out.println("FormalParameterList");
        n.f0.accept(this);
        n.f1.accept(this);
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    @Override
    public void visit(FormalParameter n) {
        System.out.println("FormalParameter");
        n.f0.accept(this);
        n.f1.accept(this);
    }

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     */
    @Override
    public void visit(FormalParameterRest n) {
        System.out.println("FormalParameterRest");
        n.f0.accept(this);
        n.f1.accept(this);
    }

    /**
     * f0 -> ArrayType()
     * | BooleanType()
     * | IntegerType()
     * | Identifier()
     */
    @Override
    public void visit(Type n) {
        System.out.println("Type");
        n.f0.accept(this);
    }

    /**
     * f0 -> "int"
     * f1 -> "["
     * f2 -> "]"
     */
    @Override
    public void visit(ArrayType n) {
        System.out.println("ArrayType");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
    }

    /**
     * f0 -> "boolean"
     */
    @Override
    public void visit(BooleanType n) {
        System.out.println("BooleanType");
        n.f0.accept(this);
    }

    /**
     * f0 -> "int"
     */
    @Override
    public void visit(IntegerType n) {
        System.out.println("IntegerType");
        n.f0.accept(this);
    }

    /**
     * f0 -> Block()
     * | AssignmentStatement()
     * | ArrayAssignmentStatement()
     * | IfStatement()
     * | WhileStatement()
     * | PrintStatement()
     */
    @Override
    public void visit(Statement n) {
        System.out.println("Statement");
        n.f0.accept(this);
    }

    /**
     * f0 -> "{"
     * f1 -> ( Statement() )*
     * f2 -> "}"
     */
    @Override
    public void visit(Block n) {
        System.out.println("Block");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    @Override
    public void visit(AssignmentStatement n) {
        System.out.println("AssignmentStatement");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
    }

    /**
     * f0 -> Identifier()
     * f1 -> "["
     * f2 -> Expression()
     * f3 -> "]"
     * f4 -> "="
     * f5 -> Expression()
     * f6 -> ";"
     */
    @Override
    public void visit(ArrayAssignmentStatement n) {
        System.out.println("ArrayAssignmentStatement");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
    }

    /**
     * f0 -> "if"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     * f5 -> "else"
     * f6 -> Statement()
     */
    @Override
    public void visit(IfStatement n) {
        System.out.println("IfStatement");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
    }

    /**
     * f0 -> "while"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     */
    @Override
    public void visit(WhileStatement n) {
        System.out.println("WhileStatement");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
    }

    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> ";"
     */
    @Override
    public void visit(PrintStatement n) {
        System.out.println("PrintStatement");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
    }

    /**
     * f0 -> AndExpression()
     * | CompareExpression()
     * | PlusExpression()
     * | MinusExpression()
     * | TimesExpression()
     * | ArrayLookup()
     * | ArrayLength()
     * | MessageSend()
     * | PrimaryExpression()
     */
    @Override
    public void visit(Expression n) {
        System.out.println("Expression");
        n.f0.accept(this);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    @Override
    public void visit(AndExpression n) {
        System.out.println("AndExpression");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    @Override
    public void visit(CompareExpression n) {
        System.out.println("CompareExpression");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    @Override
    public void visit(PlusExpression n) {
        System.out.println("PlusExpression");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    @Override
    public void visit(MinusExpression n) {
        System.out.println("MinusExpression");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    @Override
    public void visit(TimesExpression n) {
        System.out.println("TimesExpression");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    @Override
    public void visit(ArrayLookup n) {
        System.out.println("ArrayLookup");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    @Override
    public void visit(ArrayLength n) {
        System.out.println("ArrayLength");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( ExpressionList() )?
     * f5 -> ")"
     */
    @Override
    public void visit(MessageSend n) {
        System.out.println("MessageSend");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
    }

    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    @Override
    public void visit(ExpressionList n) {
        System.out.println("ExpressionList");
        n.f0.accept(this);
        n.f1.accept(this);
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    @Override
    public void visit(ExpressionRest n) {
        System.out.println("ExpressionRest");
        n.f0.accept(this);
        n.f1.accept(this);
    }

    /**
     * f0 -> IntegerLiteral()
     * | TrueLiteral()
     * | FalseLiteral()
     * | Identifier()
     * | ThisExpression()
     * | ArrayAllocationExpression()
     * | AllocationExpression()
     * | NotExpression()
     * | BracketExpression()
     */
    @Override
    public void visit(PrimaryExpression n) {
        System.out.println("PrimaryExpression");
        n.f0.accept(this);
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    @Override
    public void visit(IntegerLiteral n) {
        System.out.println("IntegerLiteral: " + n.f0.toString());
        n.f0.accept(this);
    }

    /**
     * f0 -> "true"
     */
    @Override
    public void visit(TrueLiteral n) {
        System.out.println("TrueLiteral");
        n.f0.accept(this);
    }

    /**
     * f0 -> "false"
     */
    @Override
    public void visit(FalseLiteral n) {
        System.out.println("FalseLiteral");
        n.f0.accept(this);
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public void visit(Identifier n) {
        System.out.println("Identifer: " + n.f0.toString());
        n.f0.accept(this);
    }

    /**
     * f0 -> "this"
     */
    @Override
    public void visit(ThisExpression n) {
        System.out.println("ThisExpression");
        n.f0.accept(this);
    }

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     */
    @Override
    public void visit(ArrayAllocationExpression n) {
        System.out.println("ArrayAllocationExpression");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    @Override
    public void visit(AllocationExpression n) {
        System.out.println("AllocationExpression");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    @Override
    public void visit(NotExpression n) {
        System.out.println("NotExpression");
        n.f0.accept(this);
        n.f1.accept(this);
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    @Override
    public void visit(BracketExpression n) {
        System.out.println("BracketExpression");
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
    }

}
