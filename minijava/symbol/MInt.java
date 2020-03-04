/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 15:45:03
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-05 17:43:51
 * @Description  : Class for int type
 * @FilePath     : \Compiler\minijava\symbol\MInt.java
 */

package symbol;

/**
 * Class for int type
 */
public class MInt extends MType {

    public MInt() {
        name = "int";
    }

    public MInt(int _row, int _col) {
        super(_row, _col);
        name = "int";
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     * 
     * @param type    instance of MType
     * @param verbose verbose mode on true
     * @return: true on match, false otherwise
     */
    public boolean CheckType(MType type, boolean verbose) {
        if (type instanceof MInt)
            return true;

        if (verbose)
            ; // Handle error
        return false;
    }
}