package kanga.kanga2mips;

import kanga.KangaParser;
import kanga.TokenMgrError;
import kanga.syntaxtree.Node;

import java.io.FileInputStream;
import java.io.InputStream;

public class Kanga2MIPS {
    public static void MIPS(String name) {
        System.out.print("\33[32m[INFO] Compiling to MIPS ... ");
        try {
            InputStream in = new FileInputStream(name + ".kg");
            Node root = new KangaParser(in).Goal();
            MIPSWriter.open(name + ".s");
            root.accept(new Kanga2MIPSVisitor(), null);
            MIPSWriter.close();
        } catch (TokenMgrError | Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done\33[0m");
    }
}
