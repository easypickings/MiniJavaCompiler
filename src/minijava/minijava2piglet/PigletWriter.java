package minijava.minijava2piglet;

import utils.CodeWriter;

public class PigletWriter extends CodeWriter {

    public static void error() {
        emit("ERROR");
    }

    public static void jump(String label, int cond) {
        if (cond < 0) emit("JUMP " + label);
        else emit("CJUMP " + T(cond) + " " + label);
    }

    public static void store(int addr, int offset, int val) {
        emit("HSTORE " + T(addr) + " " + offset + "" + T(val));
    }

    public static void load(int temp, int addr, int offset) {
        emit("HLOAD " + T(temp) + "" + T(addr) + "" + offset);
    }

    public static void print(String exp) {
        emit("PRINT " + exp);
    }

    public static void move(int temp, String exp) {
        emit("MOVE " + T(temp) + exp);
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
        // set to 0?
    }

    public static void call(int dest, String exp, int... _args) {
        String args = " (";
        for (int arg : _args) args += " " + arg;
        args += " )";
        move(dest, "CALL " + exp + args);
    }

    public static String T(int temp) {
        return "TEMP " + temp;
    }

    public static void Label(int label) {
        emit("L" + label, "\t");
    }
}