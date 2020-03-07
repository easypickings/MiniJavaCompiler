/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 15:24:54
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-07 11:31:41
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

    /**
     * Check if a given MType instance matches this (can assign to this)
     * 
     * @param type instance of MType
     * @return null on success, error message on fail
     */
    public String CheckType(MType type) {
        if (type instanceof MArray)
            return null;
        return "\33[31mType mismatch: cannot convert from \33[33;4m" + type.name
                + "\33[0m\33[31m to \33[33;4mint[]\33[0m";
    }
}