package minijava.symbol;

/**
 * Abstract class for types
 */
public abstract class MType extends MSymbol {

    public MType() {
    }

    public MType(String _name, int _row, int _col) {
        super(_name, _row, _col);
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     *
     * @param type instance of MType
     * @return true on success, false on fail
     */
    public abstract boolean CheckType(MType type);

    /**
     * Check if the type of an MVar instance matches this (can assign to this)
     *
     * @param var instance of MVar
     */
    public abstract void CheckType(MVar var);
}