package minijava.symbol;

/**
 * Abstract class for types
 */
public abstract class MType extends MSymbol {

    public MType() {
    }

    public MType(int _row, int _col) {
        super(_row, _col);
    }

    public MType(String _name, int _row, int _col) {
        super(_name, _row, _col);
    }

    public int Size() {
        /* the size of a type is always 4 (either as a value or a pointer) */
        return 4;
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     *
     * @param type instance of MType
     * @return null on success, error message on fail
     */
    public abstract String CheckType(MType type);
}