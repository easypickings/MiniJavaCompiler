package minijava.symbol;

import static utils.ErrorHandler.Error;

/**
 * Class for array type
 */
public class MArray extends MType {

    public MArray() {
        name = "int[]";
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     *
     * @param type instance of MType
     * @return true on success, false on fail
     */
    @Override
    public boolean CheckType(MType type) {
        return type instanceof MArray;
    }

    /**
     * Check if the type of an MVar instance matches this (can assign to this)
     *
     * @param var instance of MVar
     */
    @Override
    public void CheckType(MVar var) {
        MType type = var.getType();
        if (type instanceof MArray) return;
        /* avoid handling undefine error twice */
        if (type instanceof MUndef) return;
        Error("Type mismatch: cannot convert from " + type.name + " to int[]",
                var.row, var.col);
    }
}