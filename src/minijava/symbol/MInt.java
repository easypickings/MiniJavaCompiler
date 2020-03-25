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
}