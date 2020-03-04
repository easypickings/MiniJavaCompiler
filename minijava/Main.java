import java.io.*;
import visitor.*;
import syntaxtree.*;
import error.*;
import symbol.*;

public class Main {
	public static void main(String args[]) {
		try {
			InputStream in = new FileInputStream(args[0]);
			Node root = new MiniJavaParser(in).Goal();

			MSymbol typeList = new MTypeList();
			root.accept(new BuildSymbolTableVisitor(), typeList);
			ErrorHandler.PrintSymbolTable();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (TokenMgrError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
