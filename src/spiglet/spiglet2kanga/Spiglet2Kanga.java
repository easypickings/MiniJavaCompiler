package spiglet.spiglet2kanga;

import spiglet.SpigletParser;
import spiglet.TokenMgrError;
import spiglet.syntaxtree.Node;

import java.io.FileInputStream;
import java.io.InputStream;

public class Spiglet2Kanga {
    public static void Kanga(String name) {
        System.out.print("\33[32m[INFO] Compiling to Kanga ... ");
        try {
            InputStream in = new FileInputStream(name + ".spg");
            Node root = new SpigletParser(in).Goal();
            KangaWriter.open(name + ".kg");
            root.accept(new BuildGraphVisitor(), null);
            KangaWriter.close();
        } catch (TokenMgrError | Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done\33[0m");
    }
}

