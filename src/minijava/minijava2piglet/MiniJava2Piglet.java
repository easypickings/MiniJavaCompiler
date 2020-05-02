package minijava.minijava2piglet;

import minijava.syntaxtree.Node;

public class MiniJava2Piglet {
    public static void Piglet(String name, Node n) {
        System.out.print("\33[32m[INFO] Compiling to Spiglet ... ");
        PigletWriter.open(name + ".spg");
        n.accept(new MiniJava2PigletVisitor(), null);
        PigletWriter.close();
        System.out.println("Done\33[0m");
    }
}
