package minijava.symbol;

import java.util.*;

import static minijava.minijava2piglet.PigletWriter.*;
import static utils.ErrorHandler.Error;
import static utils.ErrorHandler.Warn;

/**
 * Class for methods
 */
public class MMethod extends MSymbol {

    /**
     * Count the number of methods
     */
    protected static int num = 0;
    /**
     * Return type string
     */
    protected String type;
    /**
     * Class belonging to
     */
    protected MClass owner;
    /**
     * Args
     */
    protected LinkedHashMap<String, MVar> args = new LinkedHashMap<>();
    /**
     * Local variables
     */
    protected HashMap<String, MVar> vars = new HashMap<>();
    /**
     * Unique method id (for method rename in Piglet)
     */
    protected int id;

    public MMethod(String _name, String _type, MClass _owner, int _row,
                   int _col) {
        super(_name, _row, _col);
        type = _type;
        owner = _owner;
        id = num++;
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

    public int getStatus() {
        return owner.methodStatus.get(name);
    }

    public int getOffset() { return owner.methodOffsets.get(name);}

    /**
     * Add an arg to args
     *
     * @param arg instance of MVar
     */
    public void AddArg(MVar arg) {
        if (args.containsKey(arg.name))
            Error("Arg " + arg.name + " duplicate declaration",
                    arg.row, arg.col);
        else args.put(arg.name, arg);
    }

    /**
     * Add a local variable to vars
     *
     * @param var instance of MVar
     */
    public void AddVar(MVar var) {
        if (vars.containsKey(var.name) || args.containsKey(var.name))
            Error("Variable " + var.name + " duplicate declaration",
                    var.row, var.col);
        else vars.put(var.name, var);
    }

    /**
     * Get a variable valid in the method's scope
     *
     * @param varName name of the variable
     * @return MVar instance if found, null otherwise
     */
    public MVar getVar(String varName) {
        if (args.containsKey(varName)) return args.get(varName);
        if (vars.containsKey(varName)) return vars.get(varName);
        return owner.getVar(varName);
    }

    /**
     * Check whether a method's args list is IDENTICAL
     * (in number and type) to the args of this
     *
     * @param _args an args list from a method
     * @return true on identical, false otherwise
     */
    public boolean CheckArgs(LinkedHashMap<String, MVar> _args) {
        /* check the number of args */
        if (args.size() != _args.size()) return false;

        Iterator<Map.Entry<String, MVar>> it = args.entrySet().iterator();
        Iterator<Map.Entry<String, MVar>> _it = _args.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, MVar> entry = it.next();
            Map.Entry<String, MVar> _entry = _it.next();
            /* check the type of each arg */
            if (!(Objects.equals(entry.getValue().type,
                    _entry.getValue().type)))
                return false;
        }

        return true;
    }

    /**
     * Check whether the args' type list from a method call is LEGAL
     * (can assign to the args of this)
     *
     * @param _args an args' type list
     * @return true on legal, false otherwise
     */
    public boolean CheckArgs(ArrayList<MType> _args) {
        /* check the number of args */
        if (args.size() != _args.size()) return false;

        Iterator<Map.Entry<String, MVar>> it = args.entrySet().iterator();
        Iterator<MType> _it = _args.iterator();
        while (it.hasNext()) {
            MType argType = it.next().getValue().getType();
            /* check whether the types of each arg pair match */
            if (!argType.CheckType(_it.next())) return false;
        }
        return true;
    }

    /**
     * Check whether there are unused variables
     */
    public void CheckUse() {
        for (MVar var : vars.values())
            if (!var.used)
                Warn("Variable " + var.name + " is not used",
                        var.row, var.col);
    }

    /**
     * Check whether there are uninitiated variables
     */
    public void CheckInit() {
        for (MVar var : vars.values())
            if (!var.initiated)
                Warn("Variable " + var.name + " may not have been initialized",
                        var.row, var.col);
    }

    public void Print(String indent) {
        System.out.print(indent + type + " " + name + "(");

        /* print args */
        Iterator<Map.Entry<String, MVar>> it = args.entrySet().iterator();
        if (it.hasNext()) it.next().getValue().Print("");
        while (it.hasNext()) {
            System.out.print(", ");
            it.next().getValue().Print("");
        }
        System.out.print(") {");

        /* print vars */
        if (vars.size() > 0) {
            System.out.println();
            for (MVar var : vars.values()) {
                var.Print(indent + "\t");
                System.out.println();
            }
            System.out.println(indent + "}");
        } else System.out.println("}");
        System.out.println();
    }

    public String ErrInfo() {
        StringBuilder info = new StringBuilder(type + " " + name + "(");

        /* print args */
        Iterator<Map.Entry<String, MVar>> it = args.entrySet().iterator();
        if (it.hasNext()) info.append(it.next().getValue().type);
        while (it.hasNext()) info.append(", " + it.next().getValue().type);
        info.append(")");
        return info.toString();
    }

    public String PigletName() {
        /* methods are renamed as "className_methodName_methodId" */
        return owner.name + "_" + name + "_" + id;
    }

    /**
     * Print Piglet prologue
     */
    public void Piglet() {
        /* methods are renamed as "className_methodName_methodId" */
        emit(owner.name + "_" + name + "_" + id, "");

        int size = args.size();
        if (size < maxArgs) // arg 0 reserved for *this
            emit(" [" + (size + 1) + "]\nBEGIN\n", "");
        else {
            emit(" [20]\nBEGIN\n", "");

            int num = size - maxArgs + 2;
            /* TEMP 19 -> | arg 19 | arg 20 | ... | */
            for (int i = 1; i < num; ++i) load(i + 19, 19, i * 4);
            load(19, 19, 0);
        }
    }
}