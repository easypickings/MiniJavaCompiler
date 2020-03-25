package minijava.symbol;

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
     * @return null on success, error message on fail
     */
    @Override
    public String CheckType(MType type) {
        if (type instanceof MBoolean) return null;
        return "\33[31mType mismatch: cannot convert from \33[33;4m"
                + type.name + "\33[0m\33[31m to \33[33;" + "4mboolean\33[0m";
    }
}