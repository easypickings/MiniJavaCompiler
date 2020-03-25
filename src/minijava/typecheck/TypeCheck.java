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
}