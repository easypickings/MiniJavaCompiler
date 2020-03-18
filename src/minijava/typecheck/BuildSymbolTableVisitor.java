package minijava.visitor;

import minijava.symbol.*;
import minijava.syntaxtree.*;
import utils.ErrorHandler;

import java.util.Enumeration;

/**
 * Build-symbol-table visitor
 */
public class BuildSymbolTableVisitor extends GJDepthFirst<MSymbol, MSymbol> {
    //
    // Auto class visitors--probably don't need to be overridden.
    //
    @Override
    public MSymbol visit(NodeList n, MSymbol argu) {
        MSymbol _ret = null;
        // int _count=0;
        // _count++;
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this, argu);
        return _ret;
    }

    @Override
    public MSymbol visit(NodeListOptional n, MSymbol argu) {
        if (n.present()) {
            MSymbol _ret = null;
            // int _count=0;
            // _count++;
            for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
                e.nextElement().accept(this, argu);
            return _ret;
        } else
            return null;
    }

    @Override
    public MSymbol visit(NodeOptional n, MSymbol argu) {
        if (n.present())
            return n.node.accept(this, argu);
        else
            return null;
    }

    @Override
    public MSymbol visit(NodeSequence n, MSymbol argu) {
        MSymbol _ret = null;
        // int _count=0;
        // _count++;
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this, argu);
        return _ret;
    }

    @Override
    public MSymbol visit(NodeToken n, MSymbol argu) { return null; }

    //
    // User-generated visitor methods below
    //

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    @Override
    public MSymbol visit(Goal n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
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
    public MSymbol visit(MainClass n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);

        MSymbol id1 = n.f1.accept(this, argu);
        MClass mainClass = new MClass(id1.getName(), id1.getRow(),
                id1.getCol());
        /* add main class to MTypeList */
        MTypeList.AddClass(mainClass);

        MMethod mainMethod = new MMethod("main", "void", mainClass,
                n.f6.beginLine, n.f6.beginColumn);
        /* add main method to mainClass */
        mainClass.AddMethod(mainMethod);

        n.f2.accept(this, mainClass);
        n.f3.accept(this, mainClass);
        n.f4.accept(this, mainClass);
        n.f5.accept(this, mainClass);
        n.f6.accept(this, mainClass);

        n.f7.accept(this, mainMethod);
        n.f8.accept(this, mainMethod);
        n.f9.accept(this, mainMethod);
        n.f10.accept(this, mainMethod);

        MSymbol id2 = n.f11.accept(this, mainMethod);
        MVar arg = new MVar(id2.getName(), "String[]", mainMethod,
                id2.getRow(), id2.getCol());
        /* add arg to mainMethod */
        mainMethod.AddArg(arg);

        n.f12.accept(this, mainMethod);
        n.f13.accept(this, mainMethod);
        n.f14.accept(this, mainMethod);
        n.f15.accept(this, mainMethod);
        n.f16.accept(this, mainMethod);

        n.f17.accept(this, mainClass);
        return _ret;
    }

    /**
     * f0 -> ClassDeclaration()
     * | ClassExtendsDeclaration()
     */
    @Override
    public MSymbol visit(TypeDeclaration n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        return _ret;
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
    public MSymbol visit(ClassDeclaration n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);

        MSymbol id = n.f1.accept(this, argu);
        MClass simpleClass = new MClass(id.getName(), id.getRow(), id.getCol());
        /* add class to MTypeList */
        MTypeList.AddClass(simpleClass);

        n.f2.accept(this, simpleClass);
        n.f3.accept(this, simpleClass);
        n.f4.accept(this, simpleClass);
        n.f5.accept(this, simpleClass);
        return _ret;
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
    public MSymbol visit(ClassExtendsDeclaration n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);

        MSymbol id1 = n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        MSymbol id2 = n.f3.accept(this, argu);
        /*
         * subclass can be defined before superclass
         * so only the name of superclass is available for now
         */
        MClass extendClass = new MClass(id1.getName(), id2.getName(),
                id1.getRow(), id1.getCol());
        /* add class to MTypeList */
        MTypeList.AddClass(extendClass);

        n.f4.accept(this, extendClass);
        n.f5.accept(this, extendClass);
        n.f6.accept(this, extendClass);
        n.f7.accept(this, extendClass);
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    @Override
    public MSymbol visit(VarDeclaration n, MSymbol argu) {
        MSymbol _ret = null;

        /*
         * type can be Array, Boolean, Int (of MType)
         * or an identifier (of MSymbol)
         */
        MSymbol type = n.f0.accept(this, argu);
        MSymbol id = n.f1.accept(this, argu);

        /*
         * type of a variable can be defined after var declaration
         * so only the name of type is available for now
         */
        MVar var = new MVar(id.getName(), type.getName(), argu, id.getRow(),
                id.getCol());

        if (argu instanceof MClass) ((MClass) argu).AddVar(var);
        else if (argu instanceof MMethod) ((MMethod) argu).AddVar(var);
        else /* unlikely to fall into this */
            ErrorHandler.Error("\33[31;1mIllegal position of variable " +
                    "declaration\33[0m", id.getRow(), id.getCol());

        n.f2.accept(this, argu);
        return _ret;
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
    public MSymbol visit(MethodDeclaration n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);

        MSymbol type = n.f1.accept(this, argu);
        MSymbol id = n.f2.accept(this, argu);
        /*
         * return type of a method can be defined after method declaration
         * so only the name of return type is available for now
         */
        MMethod method = new MMethod(id.getName(), type.getName(),
                (MClass) argu, id.getRow(), id.getCol());

        ((MClass) argu).AddMethod(method);

        n.f3.accept(this, method);
        n.f4.accept(this, method);
        n.f5.accept(this, method);
        n.f6.accept(this, method);
        n.f7.accept(this, method);
        n.f8.accept(this, method);
        n.f9.accept(this, method);
        n.f10.accept(this, method);
        n.f11.accept(this, method);
        n.f12.accept(this, method);
        return _ret;
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> ( FormalParameterRest() )*
     */
    @Override
    public MSymbol visit(FormalParameterList n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    @Override
    public MSymbol visit(FormalParameter n, MSymbol argu) {
        MSymbol _ret = null;

        MSymbol type = n.f0.accept(this, argu);
        MSymbol id = n.f1.accept(this, argu);
        /*
         * type of an arg can be defined after arg declaration
         * so only the name of type is available for now
         */
        MVar arg = new MVar(id.getName(), type.getName(), argu, id.getRow(),
                id.getCol());

        ((MMethod) argu).AddArg(arg);

        return _ret;
    }

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     */
    @Override
    public MSymbol visit(FormalParameterRest n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ArrayType()
     * | BooleanType()
     * | IntegerType()
     * | Identifier()
     */
    @Override
    public MSymbol visit(Type n, MSymbol argu) {
        MSymbol _ret = null;
        _ret = n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "int"
     * f1 -> "["
     * f2 -> "]"
     */
    @Override
    public MSymbol visit(ArrayType n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        _ret = MTypeList.Array();
        return _ret;
    }

    /**
     * f0 -> "boolean"
     */
    @Override
    public MSymbol visit(BooleanType n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        _ret = MTypeList.Boolean();
        return _ret;
    }

    /**
     * f0 -> "int"
     */
    @Override
    public MSymbol visit(IntegerType n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        _ret = MTypeList.Int();
        return _ret;
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
    public MSymbol visit(Statement n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "{"
     * f1 -> ( Statement() )*
     * f2 -> "}"
     */
    @Override
    public MSymbol visit(Block n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    @Override
    public MSymbol visit(AssignmentStatement n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        return _ret;
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
    public MSymbol visit(ArrayAssignmentStatement n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        return _ret;
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
    public MSymbol visit(IfStatement n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "while"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     */
    @Override
    public MSymbol visit(WhileStatement n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> ";"
     */
    @Override
    public MSymbol visit(PrintStatement n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
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
    public MSymbol visit(Expression n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MSymbol visit(AndExpression n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MSymbol visit(CompareExpression n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MSymbol visit(PlusExpression n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MSymbol visit(MinusExpression n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MSymbol visit(TimesExpression n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    @Override
    public MSymbol visit(ArrayLookup n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    @Override
    public MSymbol visit(ArrayLength n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
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
    public MSymbol visit(MessageSend n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    @Override
    public MSymbol visit(ExpressionList n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    @Override
    public MSymbol visit(ExpressionRest n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
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
    public MSymbol visit(PrimaryExpression n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    @Override
    public MSymbol visit(IntegerLiteral n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "true"
     */
    @Override
    public MSymbol visit(TrueLiteral n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "false"
     */
    @Override
    public MSymbol visit(FalseLiteral n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public MSymbol visit(Identifier n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);

        /* generate an MSymbol instance and pass upward */
        _ret = new MSymbol(n.f0.toString(), n.f0.beginLine, n.f0.beginColumn);
        return _ret;
    }

    /**
     * f0 -> "this"
     */
    @Override
    public MSymbol visit(ThisExpression n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     */
    @Override
    public MSymbol visit(ArrayAllocationExpression n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    @Override
    public MSymbol visit(AllocationExpression n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    @Override
    public MSymbol visit(NotExpression n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    @Override
    public MSymbol visit(BracketExpression n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

}