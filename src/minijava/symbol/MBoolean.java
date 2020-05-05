package minijava.symbol;

import static utils.ErrorHandler.Error;

/**
 * Class for boolean type
 */
public class MBoolean extends MType {

    public MBoolean() {
        name = "boolean";
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     *
     * @param type instance of MType
     * @return true on success, false on fail
     */
    @Override
    public boolean CheckType(MType type) {
        return type instanceof MBoolean;
    }

    /**
     * Check if the type of an MVar instance matches this (can assign to this)
     *
     * @param var instance of MVar
     */
    @Override
    public void CheckType(MVar var) {
        MType type = var.getType();
        if (type instanceof MBoolean) return;
        /* avoid handling undefine error twice */
        if (type instanceof MUndef) return;
        Error("Type mismatch: cannot convert from " + type.name + " to boolean",
                var.row, var.col);
    }
}