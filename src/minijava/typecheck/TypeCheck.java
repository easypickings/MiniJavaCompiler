<<<<<<< HEAD
package minijava.typecheck;

import minijava.MiniJavaParser;
import minijava.TokenMgrError;
import minijava.symbol.MSymbol;
import minijava.symbol.MTypeList;
import minijava.syntaxtree.Node;
import utils.ErrorHandler;

import java.io.FileInputStream;
import java.io.InputStream;

public class TypeCheck {
    public static void Check(String file) {
        try {
            InputStream in = new FileInputStream(file);
            Node root = new MiniJavaParser(in).Goal();

            MSymbol typeList = new MTypeList();
            root.accept(new BuildSymbolTableVisitor(), typeList);
            // ErrorHandler.PrintSymbolTable();
            root.accept(new TypeCheckVisitor(), typeList);
            ErrorHandler.Print();
        } catch (TokenMgrError | Exception e) {
            e.printStackTrace();
        }
    }
=======
package minijava.typecheck;

import minijava.syntaxtree.Node;
import utils.ErrorHandler;

public class TypeCheck {
    public static boolean Check(String name, Node n) {
        System.out.print("\33[32m[INFO] Type checking ... ");
        n.accept(new BuildSymbolTableVisitor(), null);
        // MTypeList.Print(); // print symbol table
        n.accept(new TypeCheckVisitor(), null);
        return ErrorHandler.Error();
    }
>>>>>>> 9c71cfa... feat(src/spiglet/*): implement register allocation
}