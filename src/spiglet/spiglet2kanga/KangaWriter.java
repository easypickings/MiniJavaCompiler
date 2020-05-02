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
    // TODO: revert to protected?
    public static final String[] reg = {"a0", "a1", "a2", "a3",
            "t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8", "t9",
            "s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "v0", "v1"};
    /**
     * Label number
     */
    protected static int label = 1;

    public static int NewLabel() {
        return label++;
    }
}
