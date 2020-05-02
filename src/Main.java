import minijava.MiniJavaParser;
import minijava.TokenMgrError;
import minijava.minijava2piglet.MiniJava2Piglet;
import minijava.syntaxtree.Node;
import minijava.typecheck.TypeCheck;
import spiglet.spiglet2kanga.Spiglet2Kanga;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        String file = args[0];
        String fileName = file.substring(0, file.lastIndexOf("."));
        String fileExt = file.substring(file.lastIndexOf("."));

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
            case ".spg":
                Spiglet2Kanga.Kanga(fileName);
            default:
                return;
        }


    }
}
