<<<<<<< HEAD
package minijava.symbol;

import utils.ErrorHandler;

import java.util.HashMap;

/**
 * Container of all MType instances
 */
public class MTypeList extends MSymbol {

    /**
     * Built-in type: Array
     */
    protected static MType Array = new MArray();
    /**
     * Built-in type: Boolean
     */
    protected static MType Boolean = new MBoolean();
    /**
     * Built-in type: Int
     */
    protected static MType Int = new MInt();
    /**
     * Built-in type: Undefined
     */
    protected static MType Undef = new MUndef();
    /**
     * User-declared classes
     */
    protected static HashMap<String, MType> Classes = new HashMap<>();

    /**
     * Add a user-declared class to Classes
     *
     * @param type instance of MClass
     */
    public static void AddClass(MClass type) {
        if (Classes.containsKey(type.name))
            ErrorHandler.Error("\33[31mClass \33[33;4m" + type.name
                            + "\33[0m\33[31m duplicate declaration\33[0m",
                    type.row, type.col);
        else Classes.put(type.name, type);
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
                if (Classes.containsKey(type)) return Classes.get(type);
                else return Undef;
        }

    }

    /**
     * Check whether a type is defined
     *
     * @param type name of a type
     * @return null on defined, error message on undefined
     */
    public static String CheckDef(String type) {
        if (getType(type) == Undef)
            return "\33[31mType \33[33;4m" + type
                    + "\33[0m\33[31m undefined\33[0m";
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

    public static void Print() {
        System.out.println();
        for (MType node : Classes.values()) ((MClass) node).Print();
    }
=======
package minijava.symbol;

import minijava.syntaxtree.*;

import java.util.HashMap;

import static utils.ErrorHandler.Error;

/**
 * Container of all MType instances
 */
public class MTypeList extends MSymbol {

    /**
     * Built-in type: Array
     */
    protected static MType Array = new MArray();
    /**
     * Built-in type: Boolean
     */
    protected static MType Boolean = new MBoolean();
    /**
     * Built-in type: Int
     */
    protected static MType Int = new MInt();
    /**
     * Built-in type: Undefined
     */
    protected static MType Undef = new MUndef();
    /**
     * User-declared classes
     */
    protected static HashMap<String, MClass> Classes = new HashMap<>();

    /**
     * Add a user-declared class to Classes
     *
     * @param type instance of MClass
     */
    public static void AddClass(MClass type) {
        if (Classes.containsKey(type.name))
            Error("Class " + type.name + " duplicate declaration",
                    type.row, type.col);
        else Classes.put(type.name, type);
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
                if (Classes.containsKey(type)) return Classes.get(type);
                else return Undef;
        }
    }

    public static MType getType(Node type) {
        if (type instanceof ArrayType) return Array;
        else if (type instanceof BooleanType) return Boolean;
        else if (type instanceof IntegerType) return Int;
        else if (type instanceof Identifier) {
            NodeToken id = ((Identifier) type).f0;
            String typeName = id.tokenImage;
            if (Classes.containsKey(typeName)) return Classes.get(typeName);
            else
                Error("Type " + typeName + " undefined", id.beginLine,
                        id.beginColumn);
        }
        return Undef;
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

    public static void setVarOffset() {
        for (MClass c : Classes.values()) c.setVarOffset();
    }

    public static void setMethodOffset() {
        for (MClass c : Classes.values()) c.setMethodOffset();
    }

    public static void BuildDTable() {
        for (MClass c : Classes.values()) c.BuildDTable();
    }

    public static void Print() {
        System.out.println();
        for (MClass c : Classes.values()) c.Print();
    }
>>>>>>> 9c71cfa... feat(src/spiglet/*): implement register allocation
}