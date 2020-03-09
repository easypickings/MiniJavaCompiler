/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 15:52:59
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-09 18:02:19
 * @Description  : Class for user-declared types
 * @FilePath     : \Compiler\minijava\symbol\MClass.java
 */

package minijava.symbol;

import minijava.error.*;
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

    public void setParent(String _parent) {
        parent = _parent;
    }

    /**
     * Add a member variable to vars
     * 
     * @param var instance of MVar
     */
    public void AddVar(MVar var) {
        if (vars.containsKey(var.name))
            ErrorHandler.Error("\33[31mVariable \33[32;4m" + var.name + "\33[0m\33[31m duplicate declaration\33[0m",
                    var.row, var.col);
        else
            vars.put(var.name, var);
    }

    /**
     * Add a member method to methods
     * 
     * @param method instance of MMethod
     */
    public void AddMethod(MMethod method) {
        if (methods.containsKey(method.name))
            ErrorHandler.Error("\33[31mMethod \33[34;4m" + method.name + "\33[0m\33[31m duplicate declaration\33[0m",
                    method.row, method.col);
        else
            methods.put(method.name, method);
    }

    /**
     * Get a member variable (declared in this or superclasses)
     * 
     * @param varName name of the variable
     * @return MVar instance if found, null otherwise
     */
    public MVar getVar(String varName) {
        if (vars.containsKey(varName))
            return vars.get(varName);

        MClass c = this.getParent();
        if (c != null)
            return c.getVar(varName);

        return null;
    }

    /**
     * Get a member method (declared in this or superclasses)
     * 
     * @param methodName name of the method
     * @return MMethod instance if found, null otherwise
     */
    public MMethod getMethod(String methodName) {
        if (methods.containsKey(methodName))
            return methods.get(methodName);

        MClass c = this.getParent();
        if (c != null)
            return c.getMethod(methodName);

        return null;
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     * 
     * @param type instance of MType
     * @return null on success, error message on fail
     */
    public String CheckType(MType type) {
        String err = "\33[31mType mismatch: cannot convert from \33[33;4m" + type.name + "\33[0m\33[31m to \33[33;4m"
                + name + "\33[0m";

        if (!(type instanceof MClass))
            return err;

        /** type should be the subclass of this */
        MClass c = (MClass) type;
        while (c != null) {
            if (c == this)
                return null;
            c = c.getParent();
        }

        return err;
    }

    /**
     * Check whether a cycle exists in the type hierachy of this
     */
    public void CheckCycle() {
        MClass c = this.getParent();
        while (c != null) {
            if (c.parent == this.name) {
                ErrorHandler.Error("\33[31mCycle detected: a cycle exists in the type hierachy between \33[33;4m"
                        + c.parent + "\33[0m\33[31m and \33[33;4m" + this.name + "\33[0m", row, col);
                return;
            }
            c = c.getParent();
        }
    }

    /**
     * Check whether a method from a subclass is compatible (override parent's
     * method is allowed, while overload is not)
     * 
     * @param _method a method from a subclass
     * @return null on compatible, error message otherwise
     */
    public String CheckMethod(MMethod _method) {
        if (methods.containsKey(_method.name)) {
            MMethod method = methods.get(_method.name);

            String err = "\33[31mMethod \33[33;4m" + _method.type + "\33[0m\33[31m " + _method.ErrInfo()
                    + "\33[31m collides with \33[33;4m" + method.type + "\33[0m\33[31m " + method.ErrInfo();

            /** check return type */
            if (_method.type != method.type)
                return err;

            /** check args */
            if (!method.CheckArgs(_method.args))
                return err;
        }

        /** check parent's member methods */
        if (parent != null)
            return this.getParent().CheckMethod(_method);
        return null;
    }

    public void Print() {
        System.out.print("class \33[33;4m" + name + "\33[0m");
        if (parent != null)
            System.out.print(" extends \33[33;4m" + parent + "\33[0m");
        System.out.println(" {");

        /** print vars */
        if (vars.size() > 0) {
            System.out.println();
            System.out.println("    // VARS");
            for (MVar var : vars.values()) {
                var.Print("    ");
                System.out.println();
            }
        }

        /** print methods */
        System.out.println();
        if (methods.size() > 0) {
            System.out.println("    // METHODS");
            for (MMethod method : methods.values())
                method.Print("    ");
        }

        System.out.println("}");
        System.out.println();
    }
}