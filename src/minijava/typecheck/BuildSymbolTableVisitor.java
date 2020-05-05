package minijava.typecheck;

import minijava.symbol.*;
import minijava.syntaxtree.*;
import minijava.visitor.GJVoidDepthFirst;

import java.util.Enumeration;

/**
 * Build-symbol-table visitor
 */
public class BuildSymbolTableVisitor extends GJVoidDepthFirst<MSymbol> {
    //
    // Auto class visitors--probably don't need to be overridden.
    //
    @Override
    public void visit(NodeList n, MSymbol argu) {
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this, argu);
    }

    @Override
    public void visit(NodeListOptional n, MSymbol argu) {
        if (n.present())
            for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
                e.nextElement().accept(this, argu);
    }

    @Override
    public void visit(NodeOptional n, MSymbol argu) {
        if (n.present())
            n.node.accept(this, argu);
    }

    @Override
    public void visit(NodeSequence n, MSymbol argu) {
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); )
            e.nextElement().accept(this, argu);
    }

    @Override
    public void visit(NodeToken n, MSymbol argu) {}

    //
    // User-generated visitor methods below
    //

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    @Override
    public void visit(Goal n, MSymbol argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
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
    public void visit(MainClass n, MSymbol argu) {
        MClass mainClass = new MClass(n.f1.f0.tokenImage, n.f1.f0.beginLine,
                n.f1.f0.beginColumn);
        MTypeList.AddClass(mainClass);

        MMethod mainMethod = new MMethod("main", "void", mainClass,
                n.f6.beginLine, n.f6.beginColumn);
        mainClass.AddMethod(mainMethod);

        MVar arg = new MVar(n.f11.f0.tokenImage, "String[]", mainMethod,
                n.f11.f0.beginLine, n.f11.f0.beginColumn);
        mainMethod.AddArg(arg);

        n.f14.accept(this, mainMethod);
    }

    /**
     * f0 -> ClassDeclaration()
     * | ClassExtendsDeclaration()
     */
    @Override
    public void visit(TypeDeclaration n, MSymbol argu) {
        n.f0.accept(this, argu);
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
    public void visit(ClassDeclaration n, MSymbol argu) {
        MClass c = new MClass(n.f1.f0.tokenImage, n.f1.f0.beginLine,
                n.f1.f0.beginColumn);
        MTypeList.AddClass(c); // add class to MTypeList

        n.f3.accept(this, c);
        n.f4.accept(this, c);
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
    public void visit(ClassExtendsDeclaration n, MSymbol argu) {
        MClass c = new MClass(n.f1.f0.tokenImage, n.f3.f0.tokenImage,
                n.f1.f0.beginLine, n.f1.f0.beginColumn);
        MTypeList.AddClass(c); // add class to MTypeList

        n.f5.accept(this, c);
        n.f6.accept(this, c);
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    @Override
    public void visit(VarDeclaration n, MSymbol argu) {
        MVar var = new MVar(n.f1.f0.tokenImage, TypeString(n.f0), argu,
                n.f1.f0.beginLine, n.f1.f0.beginColumn);
        if (argu instanceof MClass) ((MClass) argu).AddVar(var);
        else if (argu instanceof MMethod) ((MMethod) argu).AddVar(var);
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
    public void visit(MethodDeclaration n, MSymbol argu) {
        MMethod method = new MMethod(n.f2.f0.tokenImage, TypeString(n.f1),
                (MClass) argu, n.f2.f0.beginLine, n.f2.f0.beginColumn);
        ((MClass) argu).AddMethod(method);

        n.f4.accept(this, method);
        n.f7.accept(this, method);
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> ( FormalParameterRest() )*
     */
    @Override
    public void visit(FormalParameterList n, MSymbol argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    @Override
    public void visit(FormalParameter n, MSymbol argu) {
        MVar arg = new MVar(n.f1.f0.tokenImage, TypeString(n.f0), argu,
                n.f1.f0.beginLine, n.f1.f0.beginColumn);
        ((MMethod) argu).AddArg(arg);
    }

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     */
    @Override
    public void visit(FormalParameterRest n, MSymbol argu) {
        n.f1.accept(this, argu);
    }

    /**
     * get the name string of a Type node
     *
     * @param n a Type node
     * @return name string of the type
     */
    private String TypeString(Type n) {
        if (n.f0.choice instanceof ArrayType) return "int[]";
        else if (n.f0.choice instanceof BooleanType) return "boolean";
        else if (n.f0.choice instanceof IntegerType) return "int";
        else if (n.f0.choice instanceof Identifier)
            return ((Identifier) n.f0.choice).f0.tokenImage;
        return null; // unlikely to reach here;
    }
}