package spiglet.spiglet2kanga;

import utils.CodeWriter;

public class KangaWriter extends CodeWriter {

    /**
     * Register numbers
     */
    public static final int a0 = 0, a3 = 3, t0 = 4, t9 = 13, s0 = 14, s7 = 21,
            v0 = 22, v1 = 23;
    /**
     * Registers
     */
    protected static final String[] regs = {"a0", "a1", "a2", "a3",
            "t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8", "t9",
            "s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "v0", "v1"};
    /**
     * Label number
     */
    protected static int label = 1;

    public static String Reg(int reg) {
        return regs[reg];
    }

    public static int NewLabel() {
        return label++;
    }

    public static void label(int label) {
        emit("L" + label, "");
    }

    public static void error() {
        emit("ERROR");
    }

    public static void jump(int label, int cond) {
        if (label < 0) return; // due to redundant label elimination
        if (cond < 0) emit("JUMP L" + label);
        else emit("CJUMP " + regs[cond] + " L" + label);
    }

    public static void move(int reg, String exp) {
        if (exp.equals(regs[reg])) return;
        emit("MOVE " + regs[reg] + " " + exp);
    }

    public static void print(String exp) {
        emit("PRINT " + exp);
    }

    public static void hstore(int addr, int offset, int val) {
        emit("HSTORE " + regs[addr] + " " + offset + " " + regs[val]);
    }

    public static void hload(int reg, int addr, int offset) {
        emit("HLOAD " + regs[reg] + " " + regs[addr] + " " + offset);
    }

    public static void astore(int offset, int reg) {
        emit("ASTORE SPILLEDARG " + offset + " " + regs[reg]);
    }

    public static void aload(int reg, int offset) {
        emit("ALOAD " + regs[reg] + " SPILLEDARG " + offset);
    }

    public static void passarg(int offset, int reg) {
        emit("PASSARG " + offset + " " + regs[reg]);
    }

    public static void call(String exp) {
        emit("CALL " + exp);
    }

}
