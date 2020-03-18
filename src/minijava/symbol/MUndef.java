/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 15:49:00
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-07 11:32:44
 * @Description  : Class for undefined types
 * @FilePath     : \Compiler\minijava\symbol\MUndef.java
 */

package minijava.symbol;

/**
 * Class for undefined types
 */
public class MUndef extends MType {

    public MUndef() {
        name = "(undefined)";
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     * 
     * @param type instance of MType
     * @return null on success, error message on fail
     */
    public String CheckType(MType type) {
        return "\33[31mType mismatch: cannot convert from \33[33;4m" + type.name + "\33[0m\33[31m to \33[33;4m(undefined)\33[0m";
    }
}