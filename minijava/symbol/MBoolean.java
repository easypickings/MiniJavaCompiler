/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 15:42:51
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-05 17:43:36
 * @Description  : Class for boolean type
 * @FilePath     : \Compiler\minijava\symbol\MBoolean.java
 */

package symbol;

/**
 * Class for boolean type
 */
public class MBoolean extends MType {

    public MBoolean() {
        name = "boolean";
    }

    public MBoolean(int _row, int _col) {
        super(_row, _col);
        name = "boolean";
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     * 
     * @param type    instance of MType
     * @param verbose verbose mode on true
     * @return: true on match, false otherwise
     */
    public boolean CheckType(MType type, boolean verbose) {
        if (type instanceof MBoolean)
            return true;

        if (verbose)
            ; // Handle error
        return false;
    }
}