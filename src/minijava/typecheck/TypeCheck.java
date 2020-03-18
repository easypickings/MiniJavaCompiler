package minijava;

import utils.*;
import minijava.symbol.*;
import minijava.syntaxtree.*;
import minijava.visitor.*;
import java.io.*;

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
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (TokenMgrError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}