/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 16:09:12
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-05 21:03:09
 * @Description  : Container of all MType instances
 * @FilePath     : \Compiler\minijava\symbol\MTypeList.java
 */

package symbol;

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
     * @return: true on success, false on fail
     */
    public static boolean AddClass(MClass type) {
        if (Classes.containsKey(type.name))
            return false;
        Classes.put(type.name, type);
        return true;
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
                    return null; // or return Undef?
        }

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

    public static HashMap<String, MType> Classes() {
        return Classes;
    }
}