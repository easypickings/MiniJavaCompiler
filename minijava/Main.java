
/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 10:40:01
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-07 12:17:25
 * @Description  : 
 * @FilePath     : \Compiler\minijava\Main.java
 */
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
			// ErrorHandler.PrintSymbolTable();
			root.accept(new TypeCheckVisitor(), typeList);
			ErrorHandler.PrintAll();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (TokenMgrError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
