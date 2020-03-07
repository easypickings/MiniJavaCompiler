/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 16:09:12
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-06 20:09:04
 * @Description  : Container of all MType instances
 * @FilePath     : \Compiler\minijava\symbol\MTypeList.java
 */

package minijava.symbol;

import java.util.*;

/**
 * Container of all MType instances
 */
public class MTypeList extends MSymbol {

    /** Built-in type: Array */
    protected static MType Array = new MArray();
    /** Built-in type: Boolean */
    protected static MType Boolean = new MBoolean();
    /** Built-in type: Int */
    protected static MType Int = new MInt();
    /** Built-in type: Undefined */
    protected static MType Undef = new MUndef();
    /** User-declared classes */
    protected static HashMap<String, MType> Classes = new HashMap<String, MType>();

    /**
     * Add a user-declared class to Classes
     * 
     * @param type instance of MClass
     * @return null on success, error message on fail
     */
    public static String AddClass(MClass type) {
        if (Classes.containsKey(type.name))
            return "\33[31mClass \33[33;4m" + type.name + "\33[0m\33[31m duplicate declaration\33[0m";
        Classes.put(type.name, type);
        return null;
    }

    public static MType getType(String type) {
        switch (type) {
            case "int[]":
                return Array;
            case "boolean":
                return Boolean;
            case "int":
                return Int;
            default:
                if (Classes.containsKey(type))
                    return Classes.get(type);
                else
                    return Undef;
        }

    }

    /**
     * Check whether a type is defined
     * @param type name of a type
     * @return null on defined, error message on undefined
     */
    public static String CheckDef(String type) {
        if (getType(type) == Undef)
            return "\33[31mType \33[33;4m" + type + "\33[0m\33[31m undefined\33[0m";
        return null;
    }

    public static MType Array() {
        return Array;
    }

    public static MType Boolean() {
        return Boolean;
    }

    public static MType Int() {
        return Int;
    }

    public static MType Undef() {
        return Undef;
    }

    public static HashMap<String, MType> Classes() {
        return Classes;
    }
}