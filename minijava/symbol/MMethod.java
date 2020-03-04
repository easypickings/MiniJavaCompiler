/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 17:32:02
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-05 21:57:19
 * @Description  : Class for methods 
 * @FilePath     : \Compiler\minijava\symbol\MMethod.java
 */

package symbol;

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
     * @return: true on success, false on fail
     */
    public boolean AddArg(MVar arg) {
        if (args.containsKey(arg.name))
            return false;
        args.put(arg.name, arg);
        return true;
    }

    /**
     * Add a local variable to vars
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
     * Check whether the args list from a method is identical (in number and type)
     * to the args of this
     * 
     * @param _args an args list from a method
     * @return: true on identical, false otherwise
     */
    public boolean CheckArgs(LinkedHashMap<String, MVar> _args) {

        /** check the number of args */
        if (args.size() != _args.size())
            return false;

        Iterator<Map.Entry<String, MVar>> it = args.entrySet().iterator();
        Iterator<Map.Entry<String, MVar>> _it = _args.entrySet().iterator();
        while (it.hasNext() && _it.hasNext()) {
            Map.Entry<String, MVar> entry = it.next();
            Map.Entry<String, MVar> _entry = _it.next();
            /** check the type of each arg */
            if (entry.getValue().type != _entry.getValue().type)
                return false;
        }

        return true;
    }

    public void Print(String indent) {
        System.out.print(indent + "\33[33;4m" + type + " \33[34;4m" + name + "\33[0m ( ");

        /** print args */
        Iterator<Map.Entry<String, MVar>> it = args.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, MVar> entry = it.next();
            entry.getValue().Print("");
            System.out.print(", ");
        }
        System.out.println(") {");

        /** print vars */
        if (vars.size() > 0) {
            System.out.println();
            System.out.println(indent + "    \33[36;1m// LOCAL VARS\33[0m");
            for (MVar var : vars.values()) {
                var.Print(indent + "    ");
                System.out.println();
            }
        }

        System.out.println(indent + "}");
        System.out.println();
    }
}