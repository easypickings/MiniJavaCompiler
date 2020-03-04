/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 15:24:54
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-05 17:43:26
 * @Description  : Class for array type
 * @FilePath     : \Compiler\minijava\symbol\MArray.java
 */

package symbol;

/**
 * Class for array type
 */
public class MArray extends MType {

    public MArray() {
        name = "int[]";
    }

    public MArray(int _row, int _col) {
        super(_row, _col);
        name = "int[]";
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     * 
     * @param type    instance of MType
     * @param verbose verbose mode on true
     * @return: true on match, false otherwise
     */
    public boolean CheckType(MType type, boolean verbose) {
        if (type instanceof MArray)
            return true;

        if (verbose)
            ; // Handle error
        return false;
    }
}