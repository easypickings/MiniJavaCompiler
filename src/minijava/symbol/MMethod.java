/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 17:32:02
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-09 18:58:06
 * @Description  : Class for methods 
 * @FilePath     : \Compiler\minijava\symbol\MMethod.java
 */

package minijava.symbol;

import minijava.error.*;
import java.util.*;

/**
 * Class for methods
 */
public class MMethod extends MSymbol {

    /** Return type string */
    protected String type;
    /** Class belonging to */
    protected MClass owner;
    /** Args */
    protected LinkedHashMap<String, MVar> args = new LinkedHashMap<String, MVar>();
    /** Local variables */
    protected HashMap<String, MVar> vars = new HashMap<String, MVar>();

    public MMethod(String _name, String _type, MClass _owner, int _row, int _col) {
        super(_name, _row, _col);
        type = _type;
        owner = _owner;
    }

    public String getTypeString() {
        return type;
    }

    public MType getType() {
        return MTypeList.getType(type);
    }

    public MClass getOwner() {
        return owner;
    }

    /**
     * Add an arg to args
     * 
     * @param arg instance of MVar
     */
    public void AddArg(MVar arg) {
        if (args.containsKey(arg.name))
            ErrorHandler.Error("\33[31mArg \33[32;4m" + arg.name + "\33[0m\33[31m duplicate declaration\33[0m", arg.row,
                    arg.col);
        else
            args.put(arg.name, arg);
    }

    /**
     * Add a local variable to vars
     * 
     * @param var instance of MVar
     */
    public void AddVar(MVar var) {
        if (vars.containsKey(var.name) || args.containsKey(var.name))
            ErrorHandler.Error("\33[31mVariable \33[32;4m" + var.name + "\33[0m\33[31m duplicate declaration\33[0m",
                    var.row, var.col);
        else
            vars.put(var.name, var);
    }

    /**
     * Get a variable valid in the method's scope
     * 
     * @param varName name of the variable
     * @return MVar instance if found, null otherwise
     */
    public MVar getVar(String varName) {
        if (vars.containsKey(varName))
            return vars.get(varName);
        if (args.containsKey(varName))
            return args.get(varName);

        return owner.getVar(varName);
    }

    /**
     * Check whether the args list from a method is IDENTICAL (in number and type)
     * to the args of this
     * 
     * @param _args an args list from a method
     * @return true on identical, false otherwise
     */
    public boolean CheckArgs(LinkedHashMap<String, MVar> _args) {
        /** check the number of args */
        if (args.size() != _args.size())
            return false;

        Iterator<Map.Entry<String, MVar>> it = args.entrySet().iterator();
        Iterator<Map.Entry<String, MVar>> _it = _args.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, MVar> entry = it.next();
            Map.Entry<String, MVar> _entry = _it.next();
            /** check the type of each arg */
            if (entry.getValue().type != _entry.getValue().type)
                return false;
        }

        return true;
    }

    /**
     * Check whether the args' type list from a method call is legal
     * 
     * @param _args an args' type list
     * @return true on legal, false otherwise
     */
    public boolean CheckArgs(ArrayList<MType> _args) {
        /** check the number of args */
        if (args.size() != _args.size())
            return false;

        Iterator<Map.Entry<String, MVar>> it = args.entrySet().iterator();
        Iterator<MType> _it = _args.iterator();
        while (it.hasNext()) {
            MType argType = it.next().getValue().getType();
            /** check whether the types of each arg pair match */
            if (argType.CheckType(_it.next()) != null)
                return false;
        }
        return true;
    }

    /**
     * Check whether there are unused variables
     */
    public void CheckUse() {
        for (MVar var : vars.values())
            if (!var.used)
                ErrorHandler.Warn("\33[33mVariable \33[32;4m" + var.name + "\33[0m\33[33m is not used\33[0m", var.row,
                        var.col);
    }

    /**
     * Check whether a local variable is initiated (args are excluded)
     * 
     * @param var MVar instance of a local variable
     * @return true on initiated, false otherwise
     */
    public boolean CheckInit(MVar var) {
        if (!var.inited && !args.containsValue(var))
            return false;
        return true;
    }

    public void Print(String indent) {
        System.out.print(indent + "\33[33;4m" + type + "\33[0m \33[34;4m" + name + "\33[0m(");

        /** print args */
        Iterator<Map.Entry<String, MVar>> it = args.entrySet().iterator();
        if (it.hasNext())
            it.next().getValue().Print("");
        while (it.hasNext()) {
            System.out.print(", ");
            it.next().getValue().Print("");
        }
        System.out.print(") {");

        /** print vars */
        if (vars.size() > 0) {
            System.out.println();
            for (MVar var : vars.values()) {
                var.Print(indent + "    ");
                System.out.println();
            }
            System.out.println(indent + "}");
        } else
            System.out.println("}");
        System.out.println();
    }

    public String ErrInfo() {
        String info = "\33[34;4m" + name + "\33[0m\33[31m(";

        /** print args */
        Iterator<Map.Entry<String, MVar>> it = args.entrySet().iterator();
        if (it.hasNext())
            info = info + "\33[33;4m" + it.next().getValue().type;
        while (it.hasNext())
            info = info + "\33[0m\33[31m, \33[33;4m" + it.next().getValue().type;
        return info + "\33[0m\33[31m)\33[0m";
    }
}