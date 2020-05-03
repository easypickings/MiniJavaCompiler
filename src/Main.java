import minijava.MiniJavaParser;
import minijava.TokenMgrError;
import minijava.minijava2piglet.MiniJava2Piglet;
import minijava.syntaxtree.Node;
import minijava.typecheck.TypeCheck;
import spiglet.spiglet2kanga.Spiglet2Kanga;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        String file = args[0];
        String fileName = file.substring(0, file.lastIndexOf("."));
        String fileExt = file.substring(file.lastIndexOf("."));
        boolean raw = true;
        switch (fileExt) {
            case ".java":
                try {
                    InputStream in = new FileInputStream(file);
                    Node root = new MiniJavaParser(in).Goal();
                    if (!TypeCheck.Check(fileName, root)) return;
                    MiniJava2Piglet.Piglet(fileName, root);
                } catch (TokenMgrError | Exception e) {
                    e.printStackTrace();
                }
                raw = false;
            case ".spg":
                Spiglet2Kanga.Kanga(fileName);
                if (!raw) new File(fileName + ".spg").delete();
                raw = false;
            default:
                if (raw)
                    System.err.println("Unsupported source file");
        }


    }
}
