<<<<<<< HEAD
package minijava.symbol;

/**
 * Class for int type
 */
public class MInt extends MType {

    public MInt() {
        name = "int";
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     *
     * @param type instance of MType
     * @return null on success, error message on fail
     */
    @Override
    public String CheckType(MType type) {
        if (type instanceof MInt) return null;
        return "\33[31mType mismatch: cannot convert from \33[33;4m"
                + type.name + "\33[0m\33[31m to \33[33;4mint\33[0m";
    }
=======
package minijava.symbol;

import static utils.ErrorHandler.Error;

/**
 * Class for int type
 */
public class MInt extends MType {

    public MInt() {
        name = "int";
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     *
     * @param type instance of MType
     * @return true on success, false on fail
     */
    @Override
    public boolean CheckType(MType type) {
        return type instanceof MInt;
    }

    /**
     * Check if the type of an MVar instance matches this (can assign to this)
     *
     * @param var instance of MVar
     */
    @Override
    public void CheckType(MVar var) {
        MType type = var.getType();
        if (type instanceof MInt) return;
        /* avoid handling undefine error twice */
        if (type instanceof MUndef) return;
        Error("Type mismatch: cannot convert from " + type.name + " to int",
                var.row, var.col);
    }
>>>>>>> 9c71cfa... feat(src/spiglet/*): implement register allocation
}