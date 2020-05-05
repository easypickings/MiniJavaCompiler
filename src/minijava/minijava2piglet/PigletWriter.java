package minijava.minijava2piglet;

import utils.CodeWriter;

public class PigletWriter extends CodeWriter {

    /**
     * Maximum number of args allowed in Piglet
     */
    public static final int maxArgs = 20;
    /**
     * Label number
     */
    protected static int label = 1;
    /**
     * TEMP number
     */
    protected static int temp = 1;

    public static int NewLabel() {
        return label++;
    }

    public static int NewTemp() {
        return temp++;
    }

    public static void ResetTemp() {
        temp = 1;
    }

    public static String T(int temp) {
        return "TEMP " + temp;
    }

    public static void label(int label) {
        emit("L" + label + "\tNOOP\n", "");
    }

    public static void error() {
        emit("ERROR");
    }

    public static void jump(int label, int cond) {
        if (cond < 0) emit("JUMP L" + label);
        else emit("CJUMP " + T(cond) + " L" + label);
    }

    public static void store(int addr, int offset, int val) {
        emit("HSTORE " + T(addr) + " " + offset + " " + T(val));
    }

    public static void load(int temp, int addr, int offset) {
        emit("HLOAD " + T(temp) + " " + T(addr) + " " + offset);
    }

    public static void print(String exp) {
        emit("PRINT " + exp);
    }

    public static void move(int temp, String exp) {
        emit("MOVE " + T(temp) + " " + exp);
    }

    public static void lt(int dest, int temp, String exp) {
        move(dest, "LT " + T(temp) + " " + exp);
    }

    public static void plus(int dest, int temp, String exp) {
        move(dest, "PLUS " + T(temp) + " " + exp);
    }

    public static void minus(int dest, int temp, String exp) {
        move(dest, "MINUS " + T(temp) + " " + exp);
    }

    public static void times(int dest, int temp, String exp) {
        move(dest, "TIMES " + T(temp) + " " + exp);
    }

    public static void allocate(int dest, String exp) {
        move(dest, "HALLOCATE " + exp);
    }

    public static void call(int dest, String exp, int... _args) {
        StringBuilder args = new StringBuilder(" (");
        for (int arg : _args) args.append(" " + T(arg));
        args.append(" )");
        move(dest, "CALL " + exp + args.toString());
    }
}