package kanga.kanga2mips;

import utils.CodeWriter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MIPSWriter extends CodeWriter {

    /*
     * NOTE: MIPS limits immediate integer to 16 bits, so those greater than
     * 16 bits should be loaded to registers first.
     *
     * Instructions influenced: lw/sw/addi/subi/muli/slti
     */

    protected static final Set<String> spillRegs = Stream.of("v0", "v1", "t0").
            collect(Collectors.toCollection(HashSet::new));

    protected static final String format2 = "%-6s%s\n",
            format3 = "%-6s%-4s%s\n",
            format4 = "%-6s%-4s%-4s%s\n";

    protected static boolean indent = false;

    public static void emit(String format, Object... args) {
        if (!indent) file.print("    ");
        file.printf(format, args);
        indent = false;
    }

    protected static boolean int16(int i) {
        return Short.MIN_VALUE <= i && i <= Short.MAX_VALUE;
    }

    protected static String reg(String r1, String r2) {
        for (String r : spillRegs)
            if (!r.equals(r1) && !r.equals(r2)) return r;
        return null;
    }

    protected static String $(String reg) {
        return "$" + reg;
    }

    public static void label(String label) {
//        emit(label + ":\n", "");
        file.printf("%-4s", label + ":");
        indent = true;
    }

    public static void push(String reg) {
        emit(format4, "addi", "$sp", "$sp", "-4");
        emit(format3, "sw", $(reg), "($sp)");
    }

    public static void pop(String reg) {
        emit(format3, "lw", $(reg), "($sp)");
        emit(format4, "addi", "$sp", "$sp", "4");
    }

    public static void move(String dst, String src) {
        if (!dst.equals(src)) emit(format3, "move", $(dst), $(src));
    }

    public static void la(String dst, String label) {
        emit(format3, "la", $(dst), label);
    }

    public static void li(String dst, int i) {
        emit(format3, "li", $(dst), i);
    }

    public static void lw(String dst, String addr, int offset) {
        if (offset == 0) emit(format3, "lw", $(dst), "(" + $(addr) + ")");
        else if (int16(offset))
            emit(format3, "lw", $(dst), offset + "(" + $(addr) + ")");
        else {
            String reg = reg(dst, addr);
            push(reg);
            li(reg, offset);
            add(reg, addr, reg);
            lw(dst, reg, 0);
            pop(reg);
        }
    }

    public static void sw(String src, String addr, int offset) {
        if (offset == 0) emit(format3, "sw", $(src), "(" + $(addr) + ")");
        else if (int16(offset))
            emit(format3, "sw", $(src), offset + "(" + $(addr) + ")");
        else {
            String reg = reg(src, addr);
            push(reg);
            li(reg, offset);
            add(reg, addr, reg);
            sw(src, reg, 0);
            pop(reg);
        }
    }

    public static void add(String dst, String src1, String src2) {
        emit(format4, "add", $(dst), $(src1), $(src2));
    }

    public static void addi(String dst, String src, int i) {
        if (int16(i)) emit(format4, "addi", $(dst), $(src), i);
        else {
            String reg = reg(dst, src);
            push(reg);
            li(reg, i);
            add(dst, src, reg);
            pop(reg);
        }
    }

    public static void sub(String dst, String src1, String src2) {
        emit(format4, "sub", $(dst), $(src1), $(src2));
    }

    public static void subi(String dst, String src, int i) {
        if (int16(i)) emit(format4, "addi", $(dst), $(src), -i);
        else {
            String reg = reg(dst, src);
            push(reg);
            li(reg, i);
            sub(dst, src, reg);
            pop(reg);
        }
    }

    public static void mul(String dst, String src1, String src2) {
        emit(format4, "mul", $(dst), $(src1), $(src2));
    }

    public static void muli(String dst, String src, int i) {
        String reg = reg(dst, src);
        push(reg);
        li(reg, i);
        mul(dst, src, reg);
        pop(reg);
    }

    public static void slt(String dst, String src1, String src2) {
        emit(format4, "slt", $(dst), $(src1), $(src2));
    }

    public static void slti(String dst, String src, int i) {
        if (int16(i)) emit(format4, "slti", $(dst), $(src), i);
        else {
            String reg = reg(dst, src);
            push(reg);
            li(reg, i);
            slt(dst, src, reg);
            pop(reg);
        }
    }

    public static void b(String label) {
        emit(format2, "b", label);
    }

    public static void bne(String reg, String label) {
        /* to accord with CJUMP semantics */
        emit(format4, "bne", $(reg), "1", label);
    }

    public static void j(String label) {
        emit(format2, "j", label);
    }

    public static void jal(String label) {
        emit(format2, "jal", label);
    }

    public static void jalr(String reg) {
        emit(format2, "jalr", $(reg));
    }

    public static void begin(String name, int stacks) {
        emit(".text\n", "    ");
        emit(".globl  " + name + "\n", "    ");
        emit(name + ":\n", "");
        emit(format3, "sw", "$ra", "-4($sp)"); // store $ra
        emit(format3, "sw", "$fp", "-8($sp)"); // store $fp
        emit(format3, "move", "$fp", "$sp"); // set new $fp
        emit(format4, "addi", "$sp", "$sp", -(stacks + 2) * 4);
    }

    public static void end(int stacks) {
        emit(format3, "lw", "$ra", "-4($fp)"); // restore $ra
        emit(format3, "lw", "$fp", "-8($fp)"); // restore $fp
        emit(format4, "addi", "$sp", "$sp", (stacks + 2) * 4);
        emit(format2, "jr", "$ra\n"); // return
    }

    public static void halloc() {
        emit(".text\n", "    ");
        emit(".globl  _halloc\n", "    ");
        emit("_halloc:\n", "");
        emit(format3, "li", "$v0", "9");
        emit("syscall\n", "    ");
        emit(format2, "jr", "$ra\n");
    }

    public static void print() {
        emit(".text\n", "    ");
        emit(".globl  _print\n", "    ");
        emit("_print:\n", "");
        emit(format3, "li", "$v0", "1");
        emit("syscall\n", "    ");
        emit(format3, "la", "$a0", "endl");
        emit(format3, "li", "$v0", "4");
        emit("syscall\n", "    ");
        emit(format2, "jr", "$ra\n");
    }

    public static void error() {
        emit(".text\n", "    ");
        emit(".globl  _error\n", "    ");
        emit("_error:\n", "");
        emit(format3, "la", "$a0", "strerr");
        emit(format3, "li", "$v0", "4");
        emit("syscall\n", "    ");
        emit(format3, "li", "$v0", "10");
        emit("syscall\n\n", "    ");
    }

    public static void endl() {
        emit(".data\n", "    ");
        emit(".align  0\n", "    ");
        emit("endl:\n", "");
        emit(".asciiz \"\\n\"\n\n", "    ");
    }

    public static void strerr() {
        emit(".data\n", "    ");
        emit(".align  0\n", "    ");
        emit("strerr:\n", "");
        emit(".asciiz \"ERROR\\n\"\n\n", "    ");
    }
}
