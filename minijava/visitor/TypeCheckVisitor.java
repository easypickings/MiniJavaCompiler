/*
 * @Author       : Can Su
 * @Date         : 2020-03-05 14:49:46
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-07 17:06:33
 * @Description  : Type-check visitor
 * @FilePath     : \Compiler\minijava\visitor\TypeCheckVisitor.java
 */

package minijava.visitor;

import minijava.error.*;
import minijava.symbol.*;
import minijava.syntaxtree.*;
import java.util.*;

/**
 * Type-check visitor
 */
public class TypeCheckVisitor extends GJDepthFirst<MSymbol, MSymbol> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   public MSymbol visit(NodeList n, MSymbol argu) {
      MSymbol _ret=null;
      // int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         // _count++;
      }
      return _ret;
   }

   public MSymbol visit(NodeListOptional n, MSymbol argu) {
      if ( n.present() ) {
         MSymbol _ret=null;
         // int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            // _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public MSymbol visit(NodeOptional n, MSymbol argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public MSymbol visit(NodeSequence n, MSymbol argu) {
      MSymbol _ret=null;
      // int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         // _count++;
      }
      return _ret;
   }

   public MSymbol visit(NodeToken n, MSymbol argu) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public MSymbol visit(Goal n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(MainClass n, MSymbol argu) {
      MSymbol _ret=null;
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
    *       | ClassExtendsDeclaration()
    */
   public MSymbol visit(TypeDeclaration n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(ClassDeclaration n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(ClassExtendsDeclaration n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      n.f0.accept(this, argu);

      MSymbol id1 = n.f1.accept(this, argu);
      String extendClassName = id1.getName();
      MClass extendClass = (MClass) MTypeList.getType(extendClassName);

      n.f2.accept(this, argu);

      MSymbol id2 = n.f3.accept(this, argu);
      String parentClassName = id2.getName();
      /** check whether parent class is defined */
      err = MTypeList.CheckDef(parentClassName);
      if (err != null) {
         ErrorHandler.Error(err, id2.getRow(), id2.getCol());
         /** clear parent */
         extendClass.setParent(null);
      }
      err = null;

      /** check circular inheritance */
      err = extendClass.CheckCycle();
      if (err != null)
         ErrorHandler.Error(err, id1.getRow(), id1.getCol());
      err = null;

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
   public MSymbol visit(VarDeclaration n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(MethodDeclaration n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);

      MSymbol id = n.f2.accept(this, argu);
      String methodName = id.getName();
      MMethod method = ((MClass) argu).getMethod(methodName);

      MClass parentClass = ((MClass) argu).getParent();
      /** check overload */
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
      /** var should match the return type */
      err = method.getType().CheckType(var.getType());
      if (err != null)
         ErrorHandler.Error(err, var.getRow(), var.getCol());
      err = null;

      n.f11.accept(this, method);
      n.f12.accept(this, method);
      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public MSymbol visit(FormalParameterList n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public MSymbol visit(FormalParameter n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public MSymbol visit(FormalParameterRest n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public MSymbol visit(Type n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      _ret = n.f0.accept(this, argu);
      String typeName = _ret.getName();
      /** check whether type exists */
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
   public MSymbol visit(ArrayType n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      _ret = MTypeList.Array();
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public MSymbol visit(BooleanType n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      _ret = MTypeList.Boolean();
      return _ret;
   }

   /**
    * f0 -> "int"
    */
   public MSymbol visit(IntegerType n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      _ret = MTypeList.Int();
      return _ret;
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public MSymbol visit(Statement n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public MSymbol visit(Block n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(AssignmentStatement n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      MSymbol id = n.f0.accept(this, argu);
      String var1Name = id.getName();
      MVar var1 = ((MMethod) argu).getVar(var1Name);
      if (var1 == null) {
         ErrorHandler.Error("\33[31mVariable \33[32;4m" + var1Name + "\33[0m\33[31m undefined for method \33[34;4m" + argu.getName() + "\33[0m", id.getRow(), id.getCol());
         var1 = new MVar(var1Name, "(undefined)", id.getRow(), id.getCol());
      }

      n.f1.accept(this, argu);

      MVar var2 = (MVar) n.f2.accept(this, argu);
      /** can var2 be assigned to var1? */
      err = var1.getType().CheckType(var2.getType());
      if (err != null)
         ErrorHandler.Error(err, var2.getRow(), var2.getCol());
      err = null;

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
   public MSymbol visit(ArrayAssignmentStatement n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      MSymbol id = n.f0.accept(this, argu);
      String var1Name = id.getName();
      MVar var1 = ((MMethod) argu).getVar(var1Name);
      if (var1 == null) {
         ErrorHandler.Error("\33[31mVariable \33[32;4m" + var1Name + "\33[0m\33[31m undefined for method \33[34;4m" + argu.getName() + "\33[0m", id.getRow(), id.getCol());
         var1 = new MVar(var1Name, "(undefined)", id.getRow(), id.getCol());
      }

      /** var1 should be of type MArray */
      err = MTypeList.Array().CheckType(var1.getType());
      if (err != null)
         ErrorHandler.Error(err, id.getRow(), id.getCol());
      err = null;

      n.f1.accept(this, argu);

      MVar var2 = (MVar) n.f2.accept(this, argu);
      /** var2 should be of type MInt */
      err = MTypeList.Int().CheckType(var2.getType());
      if (err != null)
         ErrorHandler.Error(err, var2.getRow(), var2.getCol());
      err = null;

      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      MVar var3 = (MVar) n.f5.accept(this, argu);
      /** var3 should be of type MInt */
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
   public MSymbol visit(IfStatement n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);

      MVar var = (MVar) n.f2.accept(this, argu);
      /** var should be of type MBoolean */
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
   public MSymbol visit(WhileStatement n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      
      MVar var = (MVar) n.f2.accept(this, argu);
      /** var should be of type MBoolean */
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
   public MSymbol visit(PrintStatement n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);

      MVar var = (MVar) n.f2.accept(this, argu);
      /** var should be of type MInt */
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
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
   public MSymbol visit(Expression n, MSymbol argu) {
      MSymbol _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public MSymbol visit(AndExpression n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      MVar var1 = (MVar) n.f0.accept(this, argu);
      /** var1 should be of type MBoolean */
      err = MTypeList.Boolean().CheckType(var1.getType());
      if (err != null)
         ErrorHandler.Error(err, var1.getRow(), var1.getCol());
      err = null;

      n.f1.accept(this, argu);
      MVar var2 = (MVar) n.f2.accept(this, argu);
      /** var2 should be of type MBoolean */
      err = MTypeList.Boolean().CheckType(var2.getType());
      if (err != null)
         ErrorHandler.Error(err, var2.getRow(), var2.getCol());
      err = null;

      /** return an anonymous boolean variable */
      _ret = new MVar(var1.getName() + " && " + var2.getName(), "boolean", var1.getRow(), var1.getCol());
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
   public MSymbol visit(CompareExpression n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      MVar var1 = (MVar) n.f0.accept(this, argu);
      /** var1 should be of type MInt */
      err = MTypeList.Int().CheckType(var1.getType());
      if (err != null)
         ErrorHandler.Error(err, var1.getRow(), var1.getCol());
      err = null;

      n.f1.accept(this, argu);

      MVar var2 = (MVar) n.f2.accept(this, argu);
      /** var2 should be of type MInt */
      err = MTypeList.Int().CheckType(var2.getType());
      if (err != null)
         ErrorHandler.Error(err, var2.getRow(), var2.getCol());
      err = null;

      /** return an anonymous boolean variable */
      _ret = new MVar(var1.getName() + " < " + var2.getName(), "boolean", var1.getRow(), var1.getCol());
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public MSymbol visit(PlusExpression n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      MVar var1 = (MVar) n.f0.accept(this, argu);
      /** var1 should be of type MInt */
      err = MTypeList.Int().CheckType(var1.getType());
      if (err != null)
         ErrorHandler.Error(err, var1.getRow(), var1.getCol());
      err = null;
      
      n.f1.accept(this, argu);
      
      MVar var2 = (MVar) n.f2.accept(this, argu);
      /** var2 should be of type MInt */
      err = MTypeList.Int().CheckType(var2.getType());
      if (err != null)
         ErrorHandler.Error(err, var2.getRow(), var2.getCol());
      err = null;

      /** return an anonymous int variable */
      _ret = new MVar(var1.getName() + " + " + var2.getName(), "int", var1.getRow(), var1.getCol());
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public MSymbol visit(MinusExpression n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      MVar var1 = (MVar) n.f0.accept(this, argu);
      /** var1 should be of type MInt */
      err = MTypeList.Int().CheckType(var1.getType());
      if (err != null)
         ErrorHandler.Error(err, var1.getRow(), var1.getCol());
      err = null;

      n.f1.accept(this, argu);

      MVar var2 = (MVar) n.f2.accept(this, argu);
      /** var2 should be of type MInt */
      err = MTypeList.Int().CheckType(var2.getType());
      if (err != null)
         ErrorHandler.Error(err, var2.getRow(), var2.getCol());
      err = null;

      /** return an anonymous int variable */
      _ret = new MVar(var1.getName() + " - " + var2.getName(), "int", var1.getRow(), var1.getCol());
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public MSymbol visit(TimesExpression n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      MVar var1 = (MVar) n.f0.accept(this, argu);
      /** var1 should be of type MInt */
      err = MTypeList.Int().CheckType(var1.getType());
      if (err != null)
         ErrorHandler.Error(err, var1.getRow(), var1.getCol());
      err = null;

      n.f1.accept(this, argu);
      MVar var2 = (MVar) n.f2.accept(this, argu);
      /** var2 should be of type MInt */
      err = MTypeList.Int().CheckType(var2.getType());
      if (err != null)
         ErrorHandler.Error(err, var2.getRow(), var2.getCol());
      err = null;

      /** return an anonymous int variable */
      _ret = new MVar(var1.getName() + " * " + var2.getName(), "int", var1.getRow(), var1.getCol());
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public MSymbol visit(ArrayLookup n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      MVar var1 = (MVar) n.f0.accept(this, argu);
      /** var1 should be of type MArray */
      err = MTypeList.Array().CheckType(var1.getType());
      if (err != null)
         ErrorHandler.Error(err, var1.getRow(), var1.getCol());
      err = null;

      n.f1.accept(this, argu);

      MVar var2 = (MVar) n.f2.accept(this, argu);
      /** var2 should be of type MInt */
      err = MTypeList.Int().CheckType(var2.getType());
      if (err != null)
         ErrorHandler.Error(err, var2.getRow(), var2.getCol());
      err = null;
      
      n.f3.accept(this, argu);

      /** return an anonymous int variable */
      _ret = new MVar(var1.getName() + "[" + var2.getName() + "]", "int", var1.getRow(), var1.getCol());
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public MSymbol visit(ArrayLength n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      MVar var = (MVar) n.f0.accept(this, argu);

      /** var should be of type MArray */
      err = MTypeList.Array().CheckType(var.getType());
      if (err != null)
         ErrorHandler.Error(err, var.getRow(), var.getCol());
      err = null;

      n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      /** return an anonymous int variable */
      _ret = new MVar(var.getName() + ".length", "int", var.getRow(), var.getCol());
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
   public MSymbol visit(MessageSend n, MSymbol argu) {
      MSymbol _ret=null;

      MVar var = (MVar) n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MSymbol id = n.f2.accept(this, argu);
      MType type = var.getType();
      MMethod method=null;

      /** whether type is a class */
      if (!(type instanceof MClass))
         ErrorHandler.Error("\33[31mCannot invoke \33[34;4m" + id.getName() + "\33[0m\33[31m on type \33[33;4m" + var.getTypeString() + "\33[0m", var.getRow(), var.getCol());
      else {
         method = ((MClass) type).getMethod(id.getName());
         /** whether class contains method(identifier) */
         if (method == null)
            ErrorHandler.Error("\33[31mMethod \33[34;4m" + id.getName() + "\33[0m\33[31m undefined for type \33[33;4m" + var.getTypeString() + "\33[0m", var.getRow(), var.getCol());
      }

      n.f3.accept(this, argu);

      /**
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
         public void visit(Expression n, ExprList e) {
            MVar var = (MVar) n.accept(e.visitor, e.argu);
            e.argTypeList.add(var.getType());
         }
      }

      ExprList argList = new ExprList(this, argu);
      n.f4.accept(new ExprListVisitor(), argList);

      /** check args' legality */
      if (method != null)
         if (!method.CheckArgs(argList.argTypeList))
            ErrorHandler.Error("\33[31mInvalid args \33[30m" + argList.ErrInfo() + "\33[31m for method \33[30m" + method.ErrInfo() + "\33[31m in type \33[33;4m" + type.getName() + "\33[30m", id.getRow(), id.getCol());

      n.f5.accept(this, argu);

      /** return an anonymous variable */
      if (method != null)
         _ret = new MVar(var.getName() + "." + method.getName() + "()", method.getTypeString(), var.getRow(), var.getCol());
      else
         _ret = new MVar("ErrorMethodCall", "(undefined)", var.getRow(), var.getCol());
      
      return _ret;
   }

   

   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
   public MSymbol visit(ExpressionList n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public MSymbol visit(ExpressionRest n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
   public MSymbol visit(PrimaryExpression n, MSymbol argu) {
      MSymbol _ret=null;
      _ret = n.f0.accept(this, argu);

      /** check whether an variable(identifier) is defined */
      if (!(_ret instanceof MVar)) {
         String varName = _ret.getName();
         MVar var = ((MMethod) argu).getVar(varName);
         if (var == null) {
            ErrorHandler.Error("\33[31mVariable \33[32;4m" + varName + "\33[0m\33[31m undefined for method \33[34;4m" + argu.getName() + "\33[0m", _ret.getRow(), _ret.getCol());
            _ret = new MVar(varName, "(undefined)", _ret.getRow(), _ret.getCol());
         }
         else
            _ret = new MVar(varName, var.getTypeString(), _ret.getRow(), _ret.getCol());
      }

      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public MSymbol visit(IntegerLiteral n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);

      /** return an anonymous int variable */
      _ret = new MVar(n.f0.toString(), "int", n.f0.beginLine, n.f0.beginColumn);
      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public MSymbol visit(TrueLiteral n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);

      /** return an anonymous boolean variable "true" */
      _ret = new MVar("true", "boolean", n.f0.beginLine, n.f0.beginColumn);
      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public MSymbol visit(FalseLiteral n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);

      /** return an anonymous boolean variable "false" */
      _ret = new MVar("false", "boolean", n.f0.beginLine, n.f0.beginColumn);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public MSymbol visit(Identifier n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);

      /** return an MSymbol instance and pass upward */
      _ret = new MSymbol(n.f0.toString(), n.f0.beginLine, n.f0.beginColumn);
      return _ret;
   }

   /**
    * f0 -> "this"
    */
   public MSymbol visit(ThisExpression n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);

      /** return an anonymous variable "this" */
      _ret = new MVar("this", ((MMethod) argu).getOwner().getName(), n.f0.beginLine, n.f0.beginColumn);
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   public MSymbol visit(ArrayAllocationExpression n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      MVar var = (MVar) n.f3.accept(this, argu);
      /** var should be of type MInt */
      err = MTypeList.Int().CheckType(var.getType());
      if (err != null)
         ErrorHandler.Error(err, var.getRow(), var.getCol());
      err = null;

      n.f4.accept(this, argu);

      /** return an anonymous int[] variable */
      _ret = new MVar("new int[" + var.getName() + "]", "int[]", n.f0.beginLine, n.f0.beginColumn);
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public MSymbol visit(AllocationExpression n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      n.f0.accept(this, argu);

      MSymbol id = n.f1.accept(this, argu);
      String typeName = id.getName();
      /** check whether class(identifier) is defined */
      err = MTypeList.CheckDef(typeName);
      if (err != null)
         ErrorHandler.Error(err, id.getRow(), id.getCol());
      err = null;

      n.f2.accept(this, argu);
      n.f3.accept(this, argu);

      /** return an anonymous class instance */
      _ret = new MVar("new " + typeName + "()", typeName, n.f0.beginLine, n.f0.beginColumn);
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public MSymbol visit(NotExpression n, MSymbol argu) {
      MSymbol _ret=null;
      String err=null;

      n.f0.accept(this, argu);
      MVar var = (MVar) n.f1.accept(this, argu);
      /** var should be of type MBoolean */
      err = MTypeList.Boolean().CheckType(var.getType());
      if (err != null)
         ErrorHandler.Error(err, var.getRow(), var.getCol());
      err = null;

      /** return an anonymous boolean variable */
      _ret = new MVar("!" + var.getName(), "boolean", n.f0.beginLine, n.f0.beginColumn);
      return _ret;
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public MSymbol visit(BracketExpression n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      _ret = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

}
