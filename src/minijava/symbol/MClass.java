package minijava.symbol;

import utils.ErrorHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static minijava.minijava2piglet.PigletWriter.*;

/**
 * Class for user-declared types
 */
public class MClass extends MType {

    /**
     * Parent name
     */
    protected String parent;
    /**
     * Member variables
     */
    protected HashMap<String, MVar> vars = new HashMap<>();
    /**
     * Member methods
     */
    protected HashMap<String, MMethod> methods = new HashMap<>();
    /**
     * Total size of (peculiar) member variables
     */
    protected int size = 0;
    /**
     * Total size of variables inherited from superclasses
     */
    protected int inheritedSize = 0;
    /**
     * Offsets of member variables in VTable
     */
    protected HashMap<String, Integer> varOffsets = new HashMap<>();
    /**
     * Offsets of member methods in DTable
     */
    protected HashMap<String, Integer> methodOffsets = new HashMap<>();
    /**
     * Status of member methods: 0->peculiar; 1->override by a subclass;
     * 2->override a superclass; 3->both 1 and 2
     */
    protected HashMap<String, Integer> methodStatus = new HashMap<>();
    /**
     * First method offset available
     */
    protected int maxMethodOffset = -1;

    public MClass(String _name, int _row, int _col) {
        super(_name, _row, _col);
    }

    public MClass(String _name, String _parent, int _row, int _col) {
        super(_name, _row, _col);
        parent = _parent;
    }

    public MClass getParent() {
        if (parent == null) return null;
        return (MClass) MTypeList.getType(parent);
    }

    public int instanceSize() {
        return size + inheritedSize;
    }

    /**
     * Add a member variable to vars
     *
     * @param var instance of MVar
     */
    public void AddVar(MVar var) {
        if (vars.containsKey(var.name))
            ErrorHandler.Error("Variable " + var.name
                    + " duplicate declaration", var.row, var.col);
        else {
            vars.put(var.name, var);
            size += 4; // type size is always 4 (either as a value or a pointer)
        }
    }

    /**
     * Add a member method to methods
     *
     * @param method instance of MMethod
     */
    public void AddMethod(MMethod method) {
        if (methods.containsKey(method.name))
            ErrorHandler.Error("Method " + method.name
                    + " duplicate declaration", method.row, method.col);
        else methods.put(method.name, method);
    }

    /**
     * Get a member variable (declared in this or superclasses)
     *
     * @param varName name of the variable
     * @return MVar instance if found, null otherwise
     */
    public MVar getVar(String varName) {
        if (vars.containsKey(varName)) {
            MVar var = vars.get(varName);
            var.isMember = true; // var is a member variable
            return var;
        }

        MClass c = getParent();
        if (c != null) return c.getVar(varName);

        return null;
    }

    /**
     * Get a member method (declared in this or superclasses)
     *
     * @param methodName name of the method
     * @return MMethod instance if found, null otherwise
     */
    public MMethod getMethod(String methodName) {
        if (methods.containsKey(methodName)) return methods.get(methodName);

        MClass c = getParent();
        if (c != null) return c.getMethod(methodName);

        return null;
    }

    /**
     * Check whether parent class (if specified) is defined
     *
     * @param _row row of declared parent (after "extends")
     * @param _col col of declared parent (after "extends")
     */
    public void CheckParent(int _row, int _col) {
        if (parent != null)
            if (MTypeList.getType(parent) == MTypeList.Undef) {
                ErrorHandler.Error("Type " + parent + " undefined", _row, _col);
                parent = null; // remove parent
            }
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     *
     * @param type instance of MType
     * @return true on success, false on fail
     */
    @Override
    public boolean CheckType(MType type) {
        if (!(type instanceof MClass)) return false;

        /* type should be the subclass of this */
        MClass c = (MClass) type;
        while (c != null) {
            if (c == this) return true;
            c = c.getParent();
        }
        return false;
    }


    /**
     * Check if the type of an MVar instance matches this (can assign to this)
     *
     * @param var instance of MVar
     */
    @Override
    public void CheckType(MVar var) {
        MType type = var.getType();
        if (type instanceof MClass) {
            MClass c = (MClass) type;
            while (c != null) {
                if (c == this) return;
                c = c.getParent();
            }
        }
        /* avoid handling undefine error twice */
        if (type instanceof MUndef) return;
        ErrorHandler.Error("Type mismatch: cannot convert from " + var.type
                + " to " + name, var.row, var.col);
    }

    /**
     * Check whether a cycle exists in the type hierarchy of this
     */
    public void CheckCycle() {
        MClass c = getParent();
        while (c != null) {
            if (Objects.equals(c.parent, name)) {
                ErrorHandler.Error("Cycle detected: a cycle exists "
                        + "in the type hierarchy between " + c.parent
                        + " and " + name, row, col);
                return;
            }
            c = c.getParent();
        }
    }

    /**
     * Check whether a method is compatible with methods in superclasses
     * (override parent's method is allowed, while overload is not)
     *
     * @param methodName name of a method
     */
    public void CheckMethod(String methodName) {
        setMethodStatus(methodName, 0);
        MMethod method = methods.get(methodName);
        MClass c = getParent();
        while (c != null) {
            if (c.methods.containsKey(methodName)) {
                MMethod _method = c.methods.get(methodName);

                /* check return type and args */
                if (!Objects.equals(method.type, _method.type)
                        || !_method.CheckArgs(method.args))
                    ErrorHandler.Error("Method " + method.ErrInfo()
                                    + " collides with " + _method.ErrInfo(),
                            method.row, method.col);
                else {
                    /* method in superclass is override by a subclass(this) */
                    c.setMethodStatus(methodName, 1);
                    /* method in subclass(this) override a superclass */
                    setMethodStatus(methodName, 2);
                }
            }
            c = c.getParent();
        }
    }

    public void Print() {
        System.out.print("class " + name);
        if (parent != null) System.out.print(" extends " + parent);
        System.out.println(" {");

        /* print vars */
        if (vars.size() > 0) {
            System.out.println();
            System.out.println("\t/* VARS */");
            for (MVar var : vars.values()) {
                var.Print("\t");
                System.out.println();
            }
        }

        /* print methods */
        System.out.println();
        if (methods.size() > 0) {
            System.out.println("\t/* METHODS */");
            for (MMethod method : methods.values()) method.Print("\t");
        }

        System.out.println("}");
        System.out.println();
    }

    public void setVarOffset() {
        MClass c = getParent();
        while (c != null) {
            inheritedSize += c.size;
            c = c.getParent();
        }

        int offset = 4; // reserve first 4 bytes for DTable pointer
        for (MVar var : vars.values()) {
            varOffsets.put(var.name, inheritedSize + offset);
            offset += 4; // type size is always 4
        }
    }

    public void setMethodStatus(String method, int status) {
        if (!methodStatus.containsKey(method))
            methodStatus.put(method, status);
        else methodStatus.put(method,
                methodStatus.get(method) | status);
    }

    public int setMethodOffset() {
        if (maxMethodOffset >= 0) return maxMethodOffset;
        maxMethodOffset = 0;
        if (parent != null) maxMethodOffset = getParent().setMethodOffset();

        for (Map.Entry<String, Integer> e : methodStatus.entrySet()) {
            String methodName = e.getKey();

            /* inherit method offset from superclasses  */
            if (e.getValue() >= 2) {
                MClass c = getParent();
                while (c != null) {
                    if (c.methodStatus.containsKey(methodName)) {
                        methodOffsets.put(methodName,
                                c.methodOffsets.get(methodName));
                        break;
                    }
                    c = c.getParent();
                }
            } else if (e.getValue() == 1) {
                methodOffsets.put(methodName, maxMethodOffset);
                maxMethodOffset += 4;
            }
            /* offsets of peculiar methods (status == 0) are not set
            because they can be invoked directly by name */
        }
        return maxMethodOffset;
    }

    public void BuildDTable() {
        /* no polymorphism in member methods, DTable is unnecessary */
        if (maxMethodOffset <= 0) return;

        emit(name + " [0]\nBEGIN\n", "");
        allocate(0, Integer.toString(maxMethodOffset));

        for (Map.Entry<String, Integer> e : methodOffsets.entrySet()) {
            String methodName = e.getKey();
            int methodId = methods.get(methodName).id;

            /* methods are renamed as "className_methodName_methodId" */
            move(1, name + "_" + methodName + "_" + methodId);
            store(0, e.getValue(), 1);
        }

        /* return the address of DTable */
        emit("RETURN\n\tTEMP 0\nEND\n\n", "");
    }

    public int DTableSize() {
        return maxMethodOffset;
    }
}