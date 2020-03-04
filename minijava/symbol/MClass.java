/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 15:52:59
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-05 21:48:15
 * @Description  : Class for user-declared types
 * @FilePath     : \Compiler\minijava\symbol\MClass.java
 */

package symbol;

import java.util.*;

/**
 * Class for user-declared types
 */
public class MClass extends MType {

    /** Parent name */
    protected String parent;
    /** Member variables */
    protected HashMap<String, MVar> vars = new HashMap<String, MVar>();
    /** Member methods */
    protected HashMap<String, MMethod> methods = new HashMap<String, MMethod>();

    public MClass(String _name, int _row, int _col) {
        super(_row, _col);
        name = _name;
        parent = null;
    }

    public MClass(String _name, String _parent, int _row, int _col) {
        super(_row, _col);
        name = _name;
        parent = _parent;
    }

    public MClass getParent() {
        if (parent == null)
            return null;
        return (MClass) MTypeList.getType(parent);
    }

    /**
     * Check whether a given MType instance matches this (can assign to this)
     * 
     * @param type    instance of MType
     * @param verbose verbose mode on true
     * @return: true on match, false otherwise
     */
    public boolean CheckType(MType type, boolean verbose) {
        if (!(type instanceof MClass)) {
            if (verbose)
                ; // Handle error
            return false;
        }

        /** type should be the subclass of this */
        MClass c = (MClass) type;
        while (c != null) {
            if (c == this)
                return true;
            c = c.getParent();
        }

        if (verbose)
            ; // Handle error
        return false;
    }

    /**
     * Add a member variable to vars
     * 
     * @param var instance of MVar
     * @return: true on success, false on fail
     */
    public boolean AddVar(MVar var) {
        if (vars.containsKey(var.name))
            return false;
        vars.put(var.name, var);
        return true;
    }

    /**
     * Add a member method to methods
     * 
     * @param method instance of MMethod
     * @return: true on success, false on fail
     */
    public boolean AddMethod(MMethod method) {
        if (methods.containsKey(method.name))
            return false;
        methods.put(method.name, method);
        return true;
    }

    /**
     * Check whether a method from a subclass is compatible (not a overload)
     * 
     * @param _method a method from a subclass
     * @return: true on compatible, false otherwise
     */
    public boolean CheckMethod(MMethod _method) {
        if (methods.containsKey(_method.name)) {
            MMethod method = methods.get(_method.name);
            /** check return type */
            if (!method.getType().CheckType(_method.getType(), false))
                return false;
            /** check args */
            if (!method.CheckArgs(_method.args))
                return false;
        }
        /** check parent's member methods */
        if (parent != null)
            return this.getParent().CheckMethod(_method);
        return true;
    }

    public void Print() {
        System.out.print("class \33[33;4m" + name + "\33[0m");
        if (parent != null)
            System.out.print(" extends \33[33;4m" + parent + "\33[0m");
        System.out.println(" {");

        /** print vars */
        if (vars.size() > 0) {
            System.out.println();
            System.out.println("    \33[36;1m// VARS\33[0m");
            for (MVar var : vars.values()) {
                var.Print("    ");
                System.out.println();
            }
        }

        /** print methods */
        System.out.println();
        if (methods.size() > 0) {
            System.out.println("    \33[36;1m// METHODS\33[0m");
            for (MMethod method : methods.values())
                method.Print("    ");
        }

        System.out.println("}");
        System.out.println();
    }
}