package minijava.minijava2piglet;

import minijava.symbol.*;
import minijava.syntaxtree.*;
import minijava.visitor.GJDepthFirst;
import minijava.visitor.GJVoidDepthFirst;

import java.util.ArrayList;
import java.util.Enumeration;

import static minijava.minijava2piglet.PigletWriter.*;

/**
 * MiniJava-to-Piglet visitor
 */
public class MiniJava2PigletVisitor extends GJDepthFirst<MVar, MSymbol> {
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
        MTypeList.setMethodOffset();

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        MTypeList.BuildDTable();

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
        emit("MAIN\n", "");
        n.f14.accept(this, main);
        n.f15.accept(this, main);
        emit("END\n\n", "");
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
        /*
         * VarDeclaration() is not visited because member
         * variables are not bound to TEMP when declared
         */
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
        /*
         * VarDeclaration() is not visited because member
         * variables are not bound to TEMP when declared
         */
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
        /*
         * assuring only MethodDeclaration() visit this
         * so only local variables are bound to TEMP
         */
        ((MMethod) argu).getVar(n.f1.f0.tokenImage).
                setTemp(NewTemp());
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
        MMethod method = ((MClass) argu).getMethod(n.f2.f0.tokenImage);
        method.Piglet(); // print headings here
        ResetTemp(); // reset TEMP number

        n.f4.accept(this, method);
        n.f7.accept(this, method);
        n.f8.accept(this, method);

        MVar exp = n.f10.accept(this, method);
        emit("RETURN\n\t" + exp.getExp() + "\nEND\n\n", "");
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
        ((MMethod) argu).getVar(n.f1.f0.tokenImage)
                .setTemp(NewTemp()); // args bound from TEMP 1
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
        MVar var = ((MMethod) argu).getVar(n.f0.f0.tokenImage);
        MVar exp = n.f2.accept(this, argu);

        if (var.isMember()) // var is a member variable
            store(0, var.getOffset(), exp.getTemp());
        else move(var.getTemp(), exp.getExp());
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
        MVar var = ((MMethod) argu).getVar(n.f0.f0.tokenImage);
        if (var.isMember()) { // var is a member variable
            int temp = NewTemp();
            load(temp, 0, var.getOffset());
            var.setTemp(temp);
        }

        MVar exp1 = n.f2.accept(this, argu);

        /* check out of bounds */

        int baseTemp = var.getTemp();
        int offsetTemp = exp1.getTemp();
        int condTemp = NewTemp();
        int lenTemp = NewTemp();

        load(lenTemp, baseTemp, 0); // length
        lt(condTemp, offsetTemp, T(lenTemp)); // cond = offset < length
        int errorLabel = NewLabel();
        jump(errorLabel, condTemp); // if (cond != 1) error
        lt(condTemp, offsetTemp, "0"); // cond = offset < 0
        int okLabel = NewLabel();
        jump(okLabel, condTemp); // if (cond != 1) ok
        label(errorLabel);
        error();
        label(okLabel);


        int temp = NewTemp();
        times(temp, offsetTemp, "4");
        plus(temp, baseTemp, T(temp));

        MVar exp2 = n.f5.accept(this, argu);
        store(temp, 4, exp2.getTemp());
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
        int falseLabel = NewLabel();
        jump(falseLabel, exp.getTemp());
        n.f4.accept(this, argu);
        int nextLabel = NewLabel();
        jump(nextLabel, -1);
        label(falseLabel);
        n.f6.accept(this, argu);
        label(nextLabel);
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
        int loopLabel = NewLabel();
        label(loopLabel);
        MVar exp = n.f2.accept(this, argu);
        int nextLabel = NewLabel();
        jump(nextLabel, exp.getTemp());
        n.f4.accept(this, argu);
        jump(loopLabel, -1);
        label(nextLabel);
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
        print(exp.getExp());
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
        int temp = NewTemp();
        move(temp, "0");
        int label = NewLabel();
        jump(label, exp1.getTemp()); // jump out if exp1 is false
        MVar exp2 = n.f2.accept(this, argu);
        lt(temp, temp, exp2.getExp());
        label(label);
        return new MVar("", "boolean").setTemp(temp);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MVar visit(CompareExpression n, MSymbol argu) {
        MVar exp1 = n.f0.accept(this, argu);
        MVar exp2 = n.f2.accept(this, argu);
        int temp = NewTemp();
        lt(temp, exp1.getTemp(), exp2.getExp());
        return new MVar("", "boolean").setTemp(temp);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MVar visit(PlusExpression n, MSymbol argu) {
        MVar exp1 = n.f0.accept(this, argu);
        MVar exp2 = n.f2.accept(this, argu);
        int temp = NewTemp();
        plus(temp, exp1.getTemp(), exp2.getExp());
        return new MVar("", "int").setTemp(temp);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MVar visit(MinusExpression n, MSymbol argu) {
        MVar exp1 = n.f0.accept(this, argu);
        MVar exp2 = n.f2.accept(this, argu);
        int temp = NewTemp();
        minus(temp, exp1.getTemp(), exp2.getExp());
        return new MVar("", "int").setTemp(temp);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MVar visit(TimesExpression n, MSymbol argu) {
        MVar exp1 = n.f0.accept(this, argu);
        MVar exp2 = n.f2.accept(this, argu);
        int temp = NewTemp();
        times(temp, exp1.getTemp(), exp2.getExp());
        return new MVar("", "int").setTemp(temp);
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
        MVar exp2 = n.f2.accept(this, argu);

        /* check out of bounds */

        int baseTemp = exp1.getTemp();
        int offsetTemp = exp2.getTemp();
        int condTemp = NewTemp();
        int lenTemp = NewTemp();

        load(lenTemp, baseTemp, 0); // length
        lt(condTemp, offsetTemp, T(lenTemp)); // cond = offset < length
        int errorLabel = NewLabel();
        jump(errorLabel, condTemp); // if (cond != 1) error
        lt(condTemp, offsetTemp, "0"); // cond = offset < 0
        int okLabel = NewLabel();
        jump(okLabel, condTemp); // if (cond != 1) ok
        label(errorLabel);
        error();
        label(okLabel);


        int temp = NewTemp();
        times(temp, offsetTemp, "4"); // temp = offset * 4
        plus(temp, baseTemp, T(temp)); // temp = temp + base
        load(temp, temp, 4); // temp = *(temp + 4)

        return new MVar("", "int").setTemp(temp);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    @Override
    public MVar visit(ArrayLength n, MSymbol argu) {
        MVar exp = n.f0.accept(this, argu);
        int temp = NewTemp();
        load(temp, exp.getTemp(), 0);
        return new MVar("", "int").setTemp(temp);
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
        MMethod method = ((MClass) exp.getType()).getMethod(n.f2.f0.tokenImage);

        String methodName;
        if (method.getStatus() == 0) methodName = method.PigletName();
        else {
            int addrTemp = NewTemp(); // method address
            load(addrTemp, exp.getTemp(), 0); // load DTable address
            load(addrTemp, addrTemp, method.getOffset()); // load method address
            methodName = T(addrTemp);
        }

        class ExprList {
            /**
             * array of TEMPs args bound to
             */
            private final ArrayList<Integer> argList = new ArrayList<>();
            private final MiniJava2PigletVisitor visitor;
            private final MSymbol argu;

            private ExprList(MiniJava2PigletVisitor _visitor, MSymbol _argu) {
                visitor = _visitor;
                argu = _argu;
            }
        }

        class ExprListVisitor extends GJVoidDepthFirst<ExprList> {
            @Override
            public void visit(Expression n, ExprList e) {
                MVar var = n.accept(e.visitor, e.argu);
                e.argList.add(var.getTemp());
            }
        }

        ExprList l = new ExprList(this, argu);
        l.argList.add(exp.getTemp()); // *this in TEMP 0

        n.f4.accept(new ExprListVisitor(), l);

        int returnTemp = NewTemp();

        int argSize = l.argList.size();
        if (argSize <= maxArgs)
            call(returnTemp, methodName,
                    l.argList.stream().mapToInt(Integer::valueOf).toArray());
        else {
            int[] args = new int[maxArgs];
            for (int i = 0; i < maxArgs - 1; ++i)
                args[i] = l.argList.get(i);

            int num = argSize - maxArgs + 1;
            /* TEMP 19 -> | arg 19 | arg 20 | ... | */
            int temp = NewTemp();
            allocate(temp, Integer.toString(num * 4));

            for (int i = 0; i < num; ++i)
                store(temp, i * 4, l.argList.get(i + 19));

            args[maxArgs - 1] = temp;
            call(returnTemp, methodName, args);
        }

        return new MVar("", method.getTypeString()).setTemp(returnTemp);
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
        MVar exp = n.f0.accept(this, argu);
        if (exp.isMember()) { // exp is a member variable
            int temp = NewTemp();
            load(temp, 0, exp.getOffset());
            return new MVar(exp.getName(), exp.getTypeString()).setTemp(temp);
        }
        return exp;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    @Override
    public MVar visit(IntegerLiteral n, MSymbol argu) {
        return new MVar("", "int").setTemp(NewTemp())
                .setValue(n.f0.tokenImage); // set literal value
    }

    /**
     * f0 -> "true"
     */
    @Override
    public MVar visit(TrueLiteral n, MSymbol argu) {
        return new MVar("", "boolean").setTemp(NewTemp())
                .setValue("1"); // set literal value
    }

    /**
     * f0 -> "false"
     */
    @Override
    public MVar visit(FalseLiteral n, MSymbol argu) {
        return new MVar("", "boolean").setTemp(NewTemp())
                .setValue("0"); // set literal value
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
        return ((MMethod) argu).getVar(n.f0.tokenImage);
    }

    /**
     * f0 -> "this"
     */
    @Override
    public MVar visit(ThisExpression n, MSymbol argu) {
        /* *this always in TEMP 0 */
        return new MVar("", ((MMethod) argu).getOwner().getName()).setTemp(0);
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
        int lenTemp = exp.getTemp();
        int sizeTemp = NewTemp();
        times(sizeTemp, lenTemp, "4");
        /* maintain array's length at offset 0 */
        plus(sizeTemp, sizeTemp, "4");
        int arrayTemp = NewTemp();
        allocate(arrayTemp, T(sizeTemp));

        /* initiate memory to 0 */

        int addrTemp = NewTemp();
        int offsetTemp = NewTemp();
        int zeroTemp = NewTemp();
        int condTemp = NewTemp();

        move(addrTemp, T(arrayTemp));
        move(offsetTemp, "0");
        move(zeroTemp, "0");
        int loopLabel = NewLabel();
        label(loopLabel);
        lt(condTemp, offsetTemp, T(sizeTemp));
        int nextLabel = NewLabel();
        jump(nextLabel, condTemp);
        store(addrTemp, 0, zeroTemp);
        plus(addrTemp, addrTemp, "4");
        plus(offsetTemp, offsetTemp, "4");
        jump(loopLabel, -1);
        label(nextLabel);


        store(arrayTemp, 0, lenTemp); // set array's length

        return new MVar("", "int[]").setTemp(arrayTemp);
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    @Override
    public MVar visit(AllocationExpression n, MSymbol argu) {
        MClass c = (MClass) MTypeList.getType(n.f1);
        int VTableTemp = NewTemp();
        /* 4 for DTable pointer */
        int sizeTemp = NewTemp();
        move(sizeTemp,
                Integer.toString(c.instanceSize() + 4));// 4 for DTable pointer
        allocate(VTableTemp, T(sizeTemp));

        /* initiate memory to 0 */

        int addrTemp = NewTemp();
        int offsetTemp = NewTemp();
        int zeroTemp = NewTemp();
        int condTemp = NewTemp();

        move(addrTemp, T(VTableTemp));
        move(offsetTemp, "0");
        move(zeroTemp, "0");
        int loopLabel = NewLabel();
        label(loopLabel);
        lt(condTemp, offsetTemp, T(sizeTemp));
        int nextLabel = NewLabel();
        jump(nextLabel, condTemp);
        store(addrTemp, 0, zeroTemp);
        plus(addrTemp, addrTemp, "4");
        plus(offsetTemp, offsetTemp, "4");
        jump(loopLabel, -1);
        label(nextLabel);


        int DTableTemp = NewTemp();
        if (c.DTableSize() > 0) {
            call(DTableTemp, c.getName()); // build DTable
            store(VTableTemp, 0, DTableTemp);
        }

        return new MVar("", c.getName()).setTemp(VTableTemp);
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    @Override
    public MVar visit(NotExpression n, MSymbol argu) {
        MVar exp = n.f1.accept(this, argu);
        int temp = NewTemp();
        lt(temp, exp.getTemp(), "1");

        return new MVar("", "boolean").setTemp(temp);
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