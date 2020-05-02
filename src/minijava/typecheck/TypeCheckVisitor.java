<<<<<<< HEAD
package minijava.typecheck;

import minijava.symbol.*;
import minijava.syntaxtree.*;
import minijava.visitor.GJDepthFirst;
import minijava.visitor.GJVoidDepthFirst;
import utils.ErrorHandler;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Type-check visitor
 */
public class TypeCheckVisitor extends GJDepthFirst<MSymbol, MSymbol> {
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

        String mainClassName = n.f1.accept(this, argu).getName();
        MClass mainClass = (MClass) MTypeList.getType(mainClassName);
        MMethod mainMethod = mainClass.getMethod("main");

        n.f2.accept(this, mainClass);
        n.f3.accept(this, mainClass);
        n.f4.accept(this, mainClass);
        n.f5.accept(this, mainClass);
        n.f6.accept(this, mainClass);

        n.f7.accept(this, mainMethod);
        n.f8.accept(this, mainMethod);
        n.f9.accept(this, mainMethod);
        n.f10.accept(this, mainMethod);
        n.f11.accept(this, mainMethod);
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

        String simpleClassName = n.f1.accept(this, argu).getName();
        MClass simpleClass = (MClass) MTypeList.getType(simpleClassName);

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
        String err = null;

        n.f0.accept(this, argu);

        MSymbol id1 = n.f1.accept(this, argu);
        String extendClassName = id1.getName();
        MClass extendClass = (MClass) MTypeList.getType(extendClassName);

        n.f2.accept(this, argu);

        MSymbol id2 = n.f3.accept(this, argu);
        String parentClassName = id2.getName();
        /* check whether parent class is defined */
        err = MTypeList.CheckDef(parentClassName);
        if (err != null) {
            ErrorHandler.Error(err, id2.getRow(), id2.getCol());
            /* clear parent */
            extendClass.setParent(null);
        }
        err = null;

        /* check circular inheritance */
        extendClass.CheckCycle();

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
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
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
        String err = null;

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        MSymbol id = n.f2.accept(this, argu);
        String methodName = id.getName();
        MMethod method = ((MClass) argu).getMethod(methodName);

        MClass parentClass = ((MClass) argu).getParent();
        /* check overload */
        if (parentClass != null) {
            err = parentClass.CheckMethod(method);
            if (err != null)
                ErrorHandler.Error(err, id.getRow(), id.getCol());
            err = null;
        }

        n.f3.accept(this, method);
        n.f4.accept(this, method);
        n.f5.accept(this, method);
        n.f6.accept(this, method);
        n.f7.accept(this, method);
        n.f8.accept(this, method);
        n.f9.accept(this, method);

        MVar var = (MVar) n.f10.accept(this, method);
        /* var should match the return type */
        err = method.getType().CheckType(var.getType());
        if (err != null)
            ErrorHandler.Error(err, var.getRow(), var.getCol());
        err = null;

        n.f11.accept(this, method);
        n.f12.accept(this, method);

        method.CheckUse();

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
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
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
        String err = null;

        _ret = n.f0.accept(this, argu);
        String typeName = _ret.getName();
        /* check whether type exists */
        err = MTypeList.CheckDef(typeName);
        if (err != null)
            ErrorHandler.Error(err, _ret.getRow(), _ret.getCol());
        err = null;

        return MTypeList.getType(typeName);
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
        String err = null;

        MSymbol id = n.f0.accept(this, argu);
        String var1Name = id.getName();
        MVar var1 = ((MMethod) argu).getVar(var1Name);
        if (var1 == null) {
            ErrorHandler.Error("\33[31mVariable \33[32;4m" + var1Name
                    + "\33[0m\33[31m undefined for method \33[34;4m"
                    + argu.getName() + "\33[0m", id.getRow(), id.getCol());
            var1 = new MVar(var1Name, "(undefined)",
                    id.getRow(), id.getCol());
        }

        n.f1.accept(this, argu);

        MVar var2 = (MVar) n.f2.accept(this, argu);
        /* can var2 be assigned to var1? */
        err = var1.getType().CheckType(var2.getType());
        if (err != null)
            ErrorHandler.Error(err, var2.getRow(), var2.getCol());
        err = null;

        /* var1 is initiated */
        var1.Initiated();

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
        String err = null;

        MSymbol id = n.f0.accept(this, argu);
        String var1Name = id.getName();
        MVar var1 = ((MMethod) argu).getVar(var1Name);
        if (var1 == null) {
            ErrorHandler.Error("\33[31mVariable \33[32;4m" + var1Name
                    + "\33[0m\33[31m undefined for method \33[34;4m"
                    + argu.getName() + "\33[0m", id.getRow(), id.getCol());
            var1 = new MVar(var1Name, "(undefined)",
                    id.getRow(), id.getCol());
        }

        /* var1 should be of type MArray */
        err = MTypeList.Array().CheckType(var1.getType());
        if (err != null)
            ErrorHandler.Error(err, id.getRow(), id.getCol());
        err = null;

        n.f1.accept(this, argu);

        MVar var2 = (MVar) n.f2.accept(this, argu);
        /* var2 should be of type MInt */
        err = MTypeList.Int().CheckType(var2.getType());
        if (err != null)
            ErrorHandler.Error(err, var2.getRow(), var2.getCol());
        err = null;

        n.f3.accept(this, argu);
        n.f4.accept(this, argu);

        MVar var3 = (MVar) n.f5.accept(this, argu);
        /* var3 should be of type MInt */
        err = MTypeList.Int().CheckType(var3.getType());
        if (err != null)
            ErrorHandler.Error(err, var3.getRow(), var3.getCol());
        err = null;

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
        String err = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        MVar var = (MVar) n.f2.accept(this, argu);
        /* var should be of type MBoolean */
        err = MTypeList.Boolean().CheckType(var.getType());
        if (err != null)
            ErrorHandler.Error(err, var.getRow(), var.getCol());
        err = null;

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
        String err = null;

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        MVar var = (MVar) n.f2.accept(this, argu);
        /* var should be of type MBoolean */
        err = MTypeList.Boolean().CheckType(var.getType());
        if (err != null)
            ErrorHandler.Error(err, var.getRow(), var.getCol());
        err = null;

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
        String err = null;

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        MVar var = (MVar) n.f2.accept(this, argu);
        /* var should be of type MInt */
        err = MTypeList.Int().CheckType(var.getType());
        if (err != null)
            ErrorHandler.Error(err, var.getRow(), var.getCol());
        err = null;

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
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MSymbol visit(AndExpression n, MSymbol argu) {
        MSymbol _ret = null;
        String err = null;

        MVar var1 = (MVar) n.f0.accept(this, argu);
        /* var1 should be of type MBoolean */
        err = MTypeList.Boolean().CheckType(var1.getType());
        if (err != null)
            ErrorHandler.Error(err, var1.getRow(), var1.getCol());
        err = null;

        n.f1.accept(this, argu);
        MVar var2 = (MVar) n.f2.accept(this, argu);
        /* var2 should be of type MBoolean */
        err = MTypeList.Boolean().CheckType(var2.getType());
        if (err != null)
            ErrorHandler.Error(err, var2.getRow(), var2.getCol());
        err = null;

        /* return an anonymous boolean variable */
        _ret = new MVar("", "boolean", var1.getRow(), var1.getCol());
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
        String err = null;

        MVar var1 = (MVar) n.f0.accept(this, argu);
        /* var1 should be of type MInt */
        err = MTypeList.Int().CheckType(var1.getType());
        if (err != null)
            ErrorHandler.Error(err, var1.getRow(), var1.getCol());
        err = null;

        n.f1.accept(this, argu);

        MVar var2 = (MVar) n.f2.accept(this, argu);
        /* var2 should be of type MInt */
        err = MTypeList.Int().CheckType(var2.getType());
        if (err != null)
            ErrorHandler.Error(err, var2.getRow(), var2.getCol());
        err = null;

        /* return an anonymous boolean variable */
        _ret = new MVar("", "boolean", var1.getRow(), var1.getCol());
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
        String err = null;

        MVar var1 = (MVar) n.f0.accept(this, argu);
        /* var1 should be of type MInt */
        err = MTypeList.Int().CheckType(var1.getType());
        if (err != null)
            ErrorHandler.Error(err, var1.getRow(), var1.getCol());
        err = null;

        n.f1.accept(this, argu);

        MVar var2 = (MVar) n.f2.accept(this, argu);
        /* var2 should be of type MInt */
        err = MTypeList.Int().CheckType(var2.getType());
        if (err != null)
            ErrorHandler.Error(err, var2.getRow(), var2.getCol());
        err = null;

        /* return an anonymous int variable */
        _ret = new MVar("", "int", var1.getRow(), var1.getCol());
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
        String err = null;

        MVar var1 = (MVar) n.f0.accept(this, argu);
        /* var1 should be of type MInt */
        err = MTypeList.Int().CheckType(var1.getType());
        if (err != null)
            ErrorHandler.Error(err, var1.getRow(), var1.getCol());
        err = null;

        n.f1.accept(this, argu);

        MVar var2 = (MVar) n.f2.accept(this, argu);
        /* var2 should be of type MInt */
        err = MTypeList.Int().CheckType(var2.getType());
        if (err != null)
            ErrorHandler.Error(err, var2.getRow(), var2.getCol());
        err = null;

        /* return an anonymous int variable */
        _ret = new MVar("", "int", var1.getRow(), var1.getCol());
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
        String err = null;

        MVar var1 = (MVar) n.f0.accept(this, argu);
        /* var1 should be of type MInt */
        err = MTypeList.Int().CheckType(var1.getType());
        if (err != null)
            ErrorHandler.Error(err, var1.getRow(), var1.getCol());
        err = null;

        n.f1.accept(this, argu);
        MVar var2 = (MVar) n.f2.accept(this, argu);
        /* var2 should be of type MInt */
        err = MTypeList.Int().CheckType(var2.getType());
        if (err != null)
            ErrorHandler.Error(err, var2.getRow(), var2.getCol());
        err = null;

        /* return an anonymous int variable */
        _ret = new MVar("", "int", var1.getRow(), var1.getCol());
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
        String err = null;

        MVar var1 = (MVar) n.f0.accept(this, argu);
        /* var1 should be of type MArray */
        err = MTypeList.Array().CheckType(var1.getType());
        if (err != null)
            ErrorHandler.Error(err, var1.getRow(), var1.getCol());
        err = null;

        n.f1.accept(this, argu);

        MVar var2 = (MVar) n.f2.accept(this, argu);
        /* var2 should be of type MInt */
        err = MTypeList.Int().CheckType(var2.getType());
        if (err != null)
            ErrorHandler.Error(err, var2.getRow(), var2.getCol());
        err = null;

        n.f3.accept(this, argu);

        /* return an anonymous int variable */
        _ret = new MVar("", "int", var1.getRow(), var1.getCol());
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
        String err = null;

        MVar var = (MVar) n.f0.accept(this, argu);

        /* var should be of type MArray */
        err = MTypeList.Array().CheckType(var.getType());
        if (err != null)
            ErrorHandler.Error(err, var.getRow(), var.getCol());
        err = null;

        n.f1.accept(this, argu);
        n.f2.accept(this, argu);

        /* return an anonymous int variable */
        _ret = new MVar("", "int", var.getRow(), var.getCol());
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

        MVar var = (MVar) n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        MSymbol id = n.f2.accept(this, argu);
        MType type = var.getType();
        MMethod method = null;

        /* whether type is a class */
        if (!(type instanceof MClass))
            ErrorHandler.Error("\33[31mCannot invoke \33[34;4m"
                            + id.getName() + "\33[0m\33[31m on type \33[33;4m"
                            + var.getTypeString() + "\33[0m",
                    var.getRow(), var.getCol());
        else {
            method = ((MClass) type).getMethod(id.getName());
            /* whether class contains method(identifier) */
            if (method == null)
                ErrorHandler.Error("\33[31mMethod \33[34;4m" + id.getName()
                                + "\33[0m\33[31m undefined for type \33[33;4m"
                                + var.getTypeString() + "\33[0m",
                        var.getRow(), var.getCol());
        }

        n.f3.accept(this, argu);

        /*
         * How to fetch the list of args' type?
         * Define another visitor to visit ExpressionList!
         */
        class ExprList {
            protected ArrayList<MType> argTypeList;
            protected TypeCheckVisitor visitor;
            protected MSymbol argu;

            public ExprList(TypeCheckVisitor _visitor, MSymbol _argu) {
                argTypeList = new ArrayList<MType>();
                visitor = _visitor;
                argu = _argu;
            }

            public String ErrInfo() {
                String info = "\33[31m(";
                Iterator<MType> it = argTypeList.iterator();
                if (it.hasNext())
                    info = info + "\33[33;4m" + it.next().getName();
                while (it.hasNext())
                    info = info + "\33[0m\33[31m, \33[33;4m" + it.next().getName();
                return info + "\33[0m\33[31m)\33[0m";
            }
        }

        class ExprListVisitor extends GJVoidDepthFirst<ExprList> {
            @Override
            public void visit(Expression n, ExprList e) {
                MVar var = (MVar) n.accept(e.visitor, e.argu);
                e.argTypeList.add(var.getType());
            }
        }

        ExprList argList = new ExprList(this, argu);
        n.f4.accept(new ExprListVisitor(), argList);

        /* check args' legality */
        if (method != null)
            if (!method.CheckArgs(argList.argTypeList))
                ErrorHandler.Error("\33[31mInvalid args \33[30m"
                        + argList.ErrInfo() + "\33[31m for method \33[30m"
                        + method.ErrInfo() + "\33[31m in type \33[33;4m"
                        + type.getName() + "\33[30m", id.getRow(), id.getCol());

        n.f5.accept(this, argu);

        /* return an anonymous variable */
        if (method != null)
            _ret = new MVar("", method.getTypeString(),
                    var.getRow(), var.getCol());
        else
            _ret = new MVar("", "(undefined)",
                    var.getRow(), var.getCol());

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
        _ret = n.f0.accept(this, argu);

        /* check whether an variable(identifier) is defined */
        if (!(_ret instanceof MVar)) {
            String varName = _ret.getName();
            MVar var = ((MMethod) argu).getVar(varName);
            if (var == null) {
                ErrorHandler.Error("\33[31mVariable \33[32;4m" + varName
                                + "\33[0m\33[31m undefined for method \33[34;4m"
                                + argu.getName() + "\33[0m",
                        _ret.getRow(), _ret.getCol());
                _ret = new MVar(varName, "(undefined)",
                        _ret.getRow(), _ret.getCol());
            } else {
                var.Used(); // variable is used
                if (!((MMethod) argu).CheckInit(var))
                    ErrorHandler.Warn("\33[33mVariable \33[32;4m"
                                    + varName + "\33[0m\33[33m may not have "
                                    + "been initialized \33[0m",
                            _ret.getRow(), _ret.getCol());
                _ret = new MVar(varName, var.getTypeString(),
                        _ret.getRow(), _ret.getCol());
            }
        }

        return _ret;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    @Override
    public MSymbol visit(IntegerLiteral n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);

        /* return an anonymous int variable */
        _ret = new MVar(n.f0.toString(), "int",
                n.f0.beginLine, n.f0.beginColumn);
        return _ret;
    }

    /**
     * f0 -> "true"
     */
    @Override
    public MSymbol visit(TrueLiteral n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);

        /* return an anonymous boolean variable "true" */
        _ret = new MVar("true", "boolean",
                n.f0.beginLine, n.f0.beginColumn);
        return _ret;
    }

    /**
     * f0 -> "false"
     */
    @Override
    public MSymbol visit(FalseLiteral n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);

        /* return an anonymous boolean variable "false" */
        _ret = new MVar("false", "boolean",
                n.f0.beginLine, n.f0.beginColumn);
        return _ret;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public MSymbol visit(Identifier n, MSymbol argu) {
        MSymbol _ret = null;
        n.f0.accept(this, argu);

        /* return an MSymbol instance and pass upward */
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

        /* return an anonymous variable "this" */
        _ret = new MVar("this", ((MMethod) argu).getOwner().getName(),
                n.f0.beginLine, n.f0.beginColumn);
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
        String err = null;

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);

        MVar var = (MVar) n.f3.accept(this, argu);
        /* var should be of type MInt */
        err = MTypeList.Int().CheckType(var.getType());
        if (err != null)
            ErrorHandler.Error(err, var.getRow(), var.getCol());
        err = null;

        n.f4.accept(this, argu);

        /* return an anonymous int[] variable */
        _ret = new MVar("", "int[]",
                n.f0.beginLine, n.f0.beginColumn);
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
        String err = null;

        n.f0.accept(this, argu);

        MSymbol id = n.f1.accept(this, argu);
        String typeName = id.getName();
        /* check whether class(identifier) is defined */
        err = MTypeList.CheckDef(typeName);
        if (err != null)
            ErrorHandler.Error(err, id.getRow(), id.getCol());
        err = null;

        n.f2.accept(this, argu);
        n.f3.accept(this, argu);

        /* return an anonymous class instance */
        _ret = new MVar("", typeName, n.f0.beginLine, n.f0.beginColumn);
        return _ret;
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    @Override
    public MSymbol visit(NotExpression n, MSymbol argu) {
        MSymbol _ret = null;
        String err = null;

        n.f0.accept(this, argu);
        MVar var = (MVar) n.f1.accept(this, argu);
        /* var should be of type MBoolean */
        err = MTypeList.Boolean().CheckType(var.getType());
        if (err != null)
            ErrorHandler.Error(err, var.getRow(), var.getCol());
        err = null;

        /* return an anonymous boolean variable */
        _ret = new MVar("", "boolean",
                n.f0.beginLine, n.f0.beginColumn);
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
        _ret = n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

}
=======
package minijava.typecheck;

import minijava.symbol.*;
import minijava.syntaxtree.*;
import minijava.visitor.GJDepthFirst;
import minijava.visitor.GJVoidDepthFirst;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import static utils.ErrorHandler.Error;

/**
 * Type-check visitor
 */
public class TypeCheckVisitor extends GJDepthFirst<MVar, MSymbol> {
    //
    // Auto class visitors--probably don't need to be overridden.
    //
    @Override
    public MVar visit(NodeList n, MSymbol argu) {
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this, argu);
        return null;
    }

    @Override
    public MVar visit(NodeListOptional n, MSymbol argu) {
        if (n.present())
            for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
                e.nextElement().accept(this, argu);
        return null;
    }

    @Override
    public MVar visit(NodeOptional n, MSymbol argu) {
        if (n.present())
            return n.node.accept(this, argu);
        else
            return null;
    }

    @Override
    public MVar visit(NodeSequence n, MSymbol argu) {
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this, argu);
        return null;
    }

    @Override
    public MVar visit(NodeToken n, MSymbol argu) { return null; }

    //
    // User-generated visitor methods below
    //

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    @Override
    public MVar visit(Goal n, MSymbol argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return null;
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
    public MVar visit(MainClass n, MSymbol argu) {
        MMethod main = ((MClass) MTypeList.getType(n.f1.f0.tokenImage))
                .getMethod("main");
        n.f14.accept(this, main);
        n.f15.accept(this, main);
        return null;
    }

    /**
     * f0 -> ClassDeclaration()
     * | ClassExtendsDeclaration()
     */
    @Override
    public MVar visit(TypeDeclaration n, MSymbol argu) {
        n.f0.accept(this, argu);
        return null;
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
    public MVar visit(ClassDeclaration n, MSymbol argu) {
        MClass c = (MClass) MTypeList.getType(n.f1.f0.tokenImage);

        n.f3.accept(this, c);
        c.setVarOffset(); // set member variable offset in VTable
        n.f4.accept(this, c);
        return null;
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
    public MVar visit(ClassExtendsDeclaration n, MSymbol argu) {
        MClass c = (MClass) MTypeList.getType(n.f1.f0.tokenImage);

        /* check whether parent class is defined */
        c.CheckParent(n.f3.f0.beginLine, n.f3.f0.beginColumn);

        c.CheckCycle(); // check circular inheritance

        n.f5.accept(this, c);
        c.setVarOffset(); // set member variable offset in VTable

        n.f6.accept(this, c);
        return null;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    @Override
    public MVar visit(VarDeclaration n, MSymbol argu) {
        MTypeList.getType(n.f0.f0.choice); // check type's legality
        return null;
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
    public MVar visit(MethodDeclaration n, MSymbol argu) {
        MTypeList.getType(n.f1.f0.choice); // check type's legality

        String methodName = n.f2.f0.tokenImage;
        MMethod method = ((MClass) argu).getMethod(methodName);

        ((MClass) argu).CheckMethod(methodName); // check method overload

        n.f4.accept(this, method);
        n.f7.accept(this, method);
        n.f8.accept(this, method);

        MVar exp = n.f10.accept(this, method);
        method.getType().CheckType(exp); // var should match the return type
        method.CheckInit();
        method.CheckUse();
        return null;
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> ( FormalParameterRest() )*
     */
    @Override
    public MVar visit(FormalParameterList n, MSymbol argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return null;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    @Override
    public MVar visit(FormalParameter n, MSymbol argu) {
        MTypeList.getType(n.f0.f0.choice); // check type's legality

        /* assume args are initiated */
        ((MMethod) argu).getVar(n.f1.f0.tokenImage).Initiated();
        return null;
    }

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     */
    @Override
    public MVar visit(FormalParameterRest n, MSymbol argu) {
        n.f1.accept(this, argu);
        return null;
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
    public MVar visit(Statement n, MSymbol argu) {
        n.f0.accept(this, argu);
        return null;
    }

    /**
     * f0 -> "{"
     * f1 -> ( Statement() )*
     * f2 -> "}"
     */
    @Override
    public MVar visit(Block n, MSymbol argu) {
        n.f1.accept(this, argu);
        return null;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    @Override
    public MVar visit(AssignmentStatement n, MSymbol argu) {
        NodeToken id = n.f0.f0;
        String varName = id.tokenImage;
        MVar var = ((MMethod) argu).getVar(varName);
        if (var == null)
            Error("Variable " + varName + " undefined for method "
                    + argu.getName(), id.beginLine, id.beginColumn);

        MVar exp = n.f2.accept(this, argu);
        if (var != null) {
            var.getType().CheckType(exp); // can exp be assigned to var?
            var.Initiated(); // var is initiated
        }
        return null;
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
    public MVar visit(ArrayAssignmentStatement n, MSymbol argu) {
        NodeToken id = n.f0.f0;
        String varName = id.tokenImage;
        MVar var = ((MMethod) argu).getVar(varName);
        if (var == null)
            Error("Variable " + varName + " undefined for method "
                    + argu.getName(), id.beginLine, id.beginColumn);
        else MTypeList.Array().CheckType(var); // var should be of type MArray

        MVar exp1 = n.f2.accept(this, argu);
        MTypeList.Int().CheckType(exp1); // exp1 should be of type MInt

        MVar exp2 = n.f5.accept(this, argu);
        MTypeList.Int().CheckType(exp2); // exp2 should be of type MInt

        if (var != null) var.Initiated(); // var is initiated

        return null;
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
    public MVar visit(IfStatement n, MSymbol argu) {
        MVar exp = n.f2.accept(this, argu);
        MTypeList.Boolean().CheckType(exp); // exp should be of type MBoolean

        n.f4.accept(this, argu);
        n.f6.accept(this, argu);
        return null;
    }

    /**
     * f0 -> "while"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     */
    @Override
    public MVar visit(WhileStatement n, MSymbol argu) {
        MVar exp = n.f2.accept(this, argu);
        MTypeList.Boolean().CheckType(exp); // exp should be of type MBoolean

        n.f4.accept(this, argu);
        return null;
    }

    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> ";"
     */
    @Override
    public MVar visit(PrintStatement n, MSymbol argu) {
        MVar exp = n.f2.accept(this, argu);
        MTypeList.Int().CheckType(exp); // exp should be of type MInt
        return null;
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
    public MVar visit(Expression n, MSymbol argu) {
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MVar visit(AndExpression n, MSymbol argu) {
        MVar exp1 = n.f0.accept(this, argu);
        MTypeList.Boolean().CheckType(exp1); // exp1 should be of type MBoolean

        MVar exp2 = n.f2.accept(this, argu);
        MTypeList.Boolean().CheckType(exp2); // exp2 should be of type MBoolean

        /* return an anonymous variable */
        return new MVar("", "boolean", exp1.getRow(), exp1.getCol());
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MVar visit(CompareExpression n, MSymbol argu) {
        MVar exp1 = n.f0.accept(this, argu);
        MTypeList.Int().CheckType(exp1); // exp1 should be of type MInt

        MVar exp2 = n.f2.accept(this, argu);
        MTypeList.Int().CheckType(exp2); // exp2 should be of type MInt

        /* return an anonymous variable */
        return new MVar("", "boolean", exp1.getRow(), exp1.getCol());
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MVar visit(PlusExpression n, MSymbol argu) {
        MVar exp1 = n.f0.accept(this, argu);
        MTypeList.Int().CheckType(exp1); // exp1 should be of type MInt

        MVar exp2 = n.f2.accept(this, argu);
        MTypeList.Int().CheckType(exp2); // exp2 should be of type MInt

        /* return an anonymous variable */
        return new MVar("", "int", exp1.getRow(), exp1.getCol());
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MVar visit(MinusExpression n, MSymbol argu) {
        MVar exp1 = n.f0.accept(this, argu);
        MTypeList.Int().CheckType(exp1); // exp1 should be of type MInt

        MVar exp2 = n.f2.accept(this, argu);
        MTypeList.Int().CheckType(exp2); // exp2 should be of type MInt

        /* return an anonymous variable */
        return new MVar("", "int", exp1.getRow(), exp1.getCol());
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MVar visit(TimesExpression n, MSymbol argu) {
        MVar exp1 = n.f0.accept(this, argu);
        MTypeList.Int().CheckType(exp1); // exp1 should be of type MInt

        MVar exp2 = n.f2.accept(this, argu);
        MTypeList.Int().CheckType(exp2); // exp2 should be of type MInt

        /* return an anonymous variable */
        return new MVar("", "int", exp1.getRow(), exp1.getCol());
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    @Override
    public MVar visit(ArrayLookup n, MSymbol argu) {
        MVar exp1 = n.f0.accept(this, argu);
        MTypeList.Array().CheckType(exp1); // exp1 should be of type MArray

        MVar exp2 = n.f2.accept(this, argu);
        MTypeList.Int().CheckType(exp2); // exp2 should be of type MInt

        /* return an anonymous variable */
        return new MVar("", "int", exp1.getRow(), exp1.getCol());
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    @Override
    public MVar visit(ArrayLength n, MSymbol argu) {
        MVar exp = n.f0.accept(this, argu);
        MTypeList.Array().CheckType(exp); // exp1 should be of type MArray

        /* return an anonymous variable */
        return new MVar("", "int", exp.getRow(), exp.getCol());
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
    public MVar visit(MessageSend n, MSymbol argu) {
        MVar exp = n.f0.accept(this, argu);
        NodeToken id = n.f2.f0;
        String methodName = id.tokenImage;
        MType type = exp.getType();

        if (!(type instanceof MClass)) { // var should be of type MClass
            Error("Cannot invoke " + methodName + " on type "
                    + type.getName(), exp.getRow(), exp.getCol());
            return new MVar("", "", exp.getRow(), exp.getCol());
        }

        MMethod method = ((MClass) type).getMethod(methodName);
        if (method == null) { // method should be defined for the class
            Error("Method " + methodName + " undefined for type "
                    + type.getName(), exp.getRow(), exp.getCol());
            return new MVar("", "", exp.getRow(), exp.getCol());
        }


        /*
         * define another visitor for ExpressionList
         * to fetch the args' type list
         */

        class ExprList {
            private final ArrayList<MType> argTypeList;
            private final TypeCheckVisitor visitor;
            private final MSymbol argu;

            private ExprList(TypeCheckVisitor _visitor, MSymbol _argu) {
                argTypeList = new ArrayList<>();
                visitor = _visitor;
                argu = _argu;
            }

            private String ErrInfo() {
                StringBuilder info = new StringBuilder("(");
                Iterator<MType> it = argTypeList.iterator();
                if (it.hasNext()) info.append(it.next().getName());
                while (it.hasNext()) info.append(", " + it.next().getName());
                info.append(")");
                return info.toString();
            }
        }

        class ExprListVisitor extends GJVoidDepthFirst<ExprList> {
            @Override
            public void visit(Expression n, ExprList e) {
                MVar var = n.accept(e.visitor, e.argu);
                e.argTypeList.add(var.getType());
            }
        }

        ExprList argList = new ExprList(this, argu);
        n.f4.accept(new ExprListVisitor(), argList);

        /* check args' legality */
        if (!method.CheckArgs(argList.argTypeList))
            Error("Invalid args " + argList.ErrInfo()
                    + " for method " + method.ErrInfo() + " in type "
                    + type.getName(), id.beginLine, id.beginColumn);

        /* return an anonymous variable */
        return new MVar("", method.getTypeString(), exp.getRow(), exp.getCol());
    }


    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    @Override
    public MVar visit(ExpressionList n, MSymbol argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return null;
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    @Override
    public MVar visit(ExpressionRest n, MSymbol argu) {
        n.f1.accept(this, argu);
        return null;
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
    public MVar visit(PrimaryExpression n, MSymbol argu) {
        /* exp is anonymous(temporary), while var is a member variable*/
        MVar exp = n.f0.accept(this, argu);
        MVar var = ((MMethod) argu).getVar(exp.getName());
        if (var != null) var.Used(); // var is used
        return exp;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    @Override
    public MVar visit(IntegerLiteral n, MSymbol argu) {
        return new MVar("", "int", n.f0.beginLine,
                n.f0.beginColumn).Initiated();
    }

    /**
     * f0 -> "true"
     */
    @Override
    public MVar visit(TrueLiteral n, MSymbol argu) {
        return new MVar("", "boolean", n.f0.beginLine,
                n.f0.beginColumn).Initiated();
    }

    /**
     * f0 -> "false"
     */
    @Override
    public MVar visit(FalseLiteral n, MSymbol argu) {
        return new MVar("", "boolean", n.f0.beginLine,
                n.f0.beginColumn).Initiated();
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public MVar visit(Identifier n, MSymbol argu) {
        /*
         * Can only be PrimaryExpression() -> Identifier()
         * Identifier must be a variable
         */
        NodeToken id = n.f0;
        String varName = id.tokenImage;
        MVar var = ((MMethod) argu).getVar(varName);
        if (var == null) {
            Error("Variable " + varName + " undefined for method "
                    + argu.getName(), id.beginLine, id.beginColumn);
            return new MVar(varName, "", id.beginLine, id.beginColumn);
        }
        return new MVar(varName, var.getTypeString(), id.beginLine,
                id.beginColumn);
    }

    /**
     * f0 -> "this"
     */
    @Override
    public MVar visit(ThisExpression n, MSymbol argu) {
        return new MVar("", ((MMethod) argu).getOwner().getName(),
                n.f0.beginLine, n.f0.beginColumn).Initiated();
    }

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     */
    @Override
    public MVar visit(ArrayAllocationExpression n, MSymbol argu) {
        MVar exp = n.f3.accept(this, argu);
        MTypeList.Int().CheckType(exp); // exp should be of type MInt

        return new MVar("", "int[]", n.f0.beginLine,
                n.f0.beginColumn).Initiated();
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    @Override
    public MVar visit(AllocationExpression n, MSymbol argu) {
        MTypeList.getType(n.f1); // check whether class(identifier) is defined
        return new MVar("", n.f1.f0.tokenImage, n.f0.beginLine,
                n.f0.beginColumn).Initiated();
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    @Override
    public MVar visit(NotExpression n, MSymbol argu) {
        MVar exp = n.f1.accept(this, argu);
        MTypeList.Boolean().CheckType(exp); // exp should be of type boolean
        return new MVar("", "boolean", n.f0.beginLine,
                n.f0.beginColumn).Initiated();
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    @Override
    public MVar visit(BracketExpression n, MSymbol argu) {
        return n.f1.accept(this, argu);
    }
}
>>>>>>> 9c71cfa... feat(src/spiglet/*): implement register allocation
