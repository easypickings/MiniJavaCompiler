/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 15:49:00
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-05 17:44:07
 * @Description  : Class for undefined types
 * @FilePath     : \Compiler\minijava\symbol\MUndef.java
 */

package symbol;

/**
 * Class for undefined types
 */
public class MUndef extends MType {

    public MUndef() {
        name = "";
    }

    public MUndef(int _row, int _col) {
        super(_row, _col);
        name = "";
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     * 
     * @param type    instance of MType
     * @param verbose verbose mode on true
     * @return: true on match, false otherwise
     */
    public boolean CheckType(MType type, boolean verbose) {
        return false;
    }
}