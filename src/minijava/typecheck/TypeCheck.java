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
}