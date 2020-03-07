/*
 * @Author       : Can Su
 * @Date         : 2020-03-05 14:49:46
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-07 17:05:30
 * @Description  : Build-symbol-table visitor
 * @FilePath     : \Compiler\minijava\visitor\BuildSymbolTableVisitor.java
 */

package minijava.visitor;

import minijava.error.*;
import minijava.symbol.*;
import minijava.syntaxtree.*;
import java.util.*;

/**
 * Build-symbol-table visitor
 */
public class BuildSymbolTableVisitor extends GJDepthFirst<MSymbol, MSymbol> {
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
      String err=null;

      n.f0.accept(this, argu);
      
      MSymbol id1 = n.f1.accept(this, argu);
      MClass mainClass = new MClass(id1.getName(), id1.getRow(), id1.getCol());
      /** add main class to MTypeList */
      err = MTypeList.AddClass(mainClass);
      if (err != null)
         ErrorHandler.Error(err, id1.getRow(), id1.getCol());
      err = null;

      MMethod mainMethod = new MMethod("main", "void", mainClass, n.f6.beginLine, n.f6.beginColumn);
      /** add main method to mainClass */
      err = mainClass.AddMethod(mainMethod);
      if (err != null)
         ErrorHandler.Error(err, mainMethod.getRow(), mainMethod.getCol());
      err = null;

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
      MVar arg = new MVar(id2.getName(), "String[]", mainMethod, id2.getRow(), id2.getCol());
      /** add arg to mainMethod */
      err = mainMethod.AddArg(arg);
      if (err != null)
         ErrorHandler.Error(err, id2.getRow(), id2.getCol());
      err = null;

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
      String err=null;

      n.f0.accept(this, argu);

      MSymbol id = n.f1.accept(this, argu);
      MClass simpleClass = new MClass(id.getName(), id.getRow(), id.getCol());
      /** add class to MTypeList */
      err = MTypeList.AddClass(simpleClass);
      if (err != null)
         ErrorHandler.Error(err, id.getRow(), id.getCol());
      err = null;

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
      n.f2.accept(this, argu);
      MSymbol id2 = n.f3.accept(this, argu);
      /**
       * subclass can be defined before superclass
       * so only the name of superclass is avaliable for now
       */
      MClass extendClass = new MClass(id1.getName(), id2.getName(), id1.getRow(), id1.getCol());
      /** add class to MTypeList */
      err = MTypeList.AddClass(extendClass);
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
      String err=null;

      /** 
       * type can be Array, Boolean, Int (of MType)
       * or an identifier (of MSymbol)
       */
      MSymbol type = n.f0.accept(this, argu);
      MSymbol id =  n.f1.accept(this, argu);

      /**
       * type of a variable can be defined after var declaration
       * so only the name of type is avaliable for now
       */
      MVar var = new MVar(id.getName(), type.getName(), argu, id.getRow(), id.getCol());

      if (argu instanceof MClass) {
         /** add var to argu(owner) */
         err = ((MClass) argu).AddVar(var);
         if (err != null)
            ErrorHandler.Error(err, id.getRow(), id.getCol());
         err = null;
      } else if (argu instanceof MMethod) {
         /** add var to argu(owner) */
         err = ((MMethod) argu).AddVar(var);
         if (err != null)
            ErrorHandler.Error(err, id.getRow(), id.getCol());
         err = null;
      } else /** unlikely to fall into this */
         ErrorHandler.Error("\33[31;1mIllegal position of variable declaration\33[0m", id.getRow(), id.getCol());

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

      MSymbol type = n.f1.accept(this, argu);
      MSymbol id = n.f2.accept(this, argu);
      /**
       * return type of a method can be defined after method declaration
       * so only the name of return type is avaliable for now
       */
      MMethod method = new MMethod(id.getName(), type.getName(), (MClass) argu, id.getRow(), id.getCol());
      /** add method to argu(owner) */
      err = ((MClass) argu).AddMethod(method);
      if (err != null)
         ErrorHandler.Error(err, id.getRow(), id.getCol());
      err = null;

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
      String err=null;

      MSymbol type = n.f0.accept(this, argu);
      MSymbol id = n.f1.accept(this, argu);
      /**
       * type of an arg can be defined after arg declaration
       * so only the name of type is avaliable for now
       */
      MVar arg = new MVar(id.getName(), type.getName(), argu, id.getRow(), id.getCol());
      /** add arg to argu(owner) */
      err = ((MMethod) argu).AddArg(arg);
      if (err != null)
         ErrorHandler.Error(err, id.getRow(), id.getCol());
      err = null;
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
      _ret = n.f0.accept(this, argu);
      return _ret;
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
   public MSymbol visit(ArrayAssignmentStatement n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(IfStatement n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(WhileStatement n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(PrintStatement n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
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
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public MSymbol visit(AndExpression n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(CompareExpression n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(PlusExpression n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(MinusExpression n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(TimesExpression n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(ArrayLookup n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(ArrayLength n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(MessageSend n, MSymbol argu) {
      MSymbol _ret=null;
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
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public MSymbol visit(IntegerLiteral n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public MSymbol visit(TrueLiteral n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public MSymbol visit(FalseLiteral n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public MSymbol visit(Identifier n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);

      /** generate an MSymbol instance and pass upward */
      _ret = new MSymbol(n.f0.toString(), n.f0.beginLine, n.f0.beginColumn);
      return _ret;
   }

   /**
    * f0 -> "this"
    */
   public MSymbol visit(ThisExpression n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(ArrayAllocationExpression n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(AllocationExpression n, MSymbol argu) {
      MSymbol _ret=null;
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
   public MSymbol visit(NotExpression n, MSymbol argu) {
      MSymbol _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
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
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

}
