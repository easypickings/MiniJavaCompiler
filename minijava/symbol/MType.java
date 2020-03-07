/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 12:45:10
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-05 14:36:56
 * @Description  : Abstract class for types
 * @FilePath     : \Compiler\minijava\symbol\MType.java
 */

package symbol;

/**
 * Abstract class for types
 */
public abstract class MType extends MSymbol {

    public MType() {
    }

    public MType(int _row, int _col) {
        super(_row, _col);
    }

    public MType(String _name, int _row, int _col) {
        super(_name, _row, _col);
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     * 
     * @param type    instance of MType
     * @param verbose verbose mode on true
     * @return: true on match, false otherwise
     */
    public abstract boolean CheckType(MType type, boolean verbose);
}