package minijava.symbol;

/**
 * Class for undefined types
 */
public class MUndef extends MType {

    public MUndef() {
        name = "<undefined>";
    }

    /**
     * Check if a given MType instance matches this (can assign to this)
     *
     * @param type instance of MType
     * @return true on success, false on fail
     */
    @Override
    public boolean CheckType(MType type) {
        return false;
    }

    /**
     * Check if the type of an MVar instance matches this (can assign to this)
     *
     * @param var instance of MVar
     */
    @Override
    public void CheckType(MVar var) {
        /* avoid handling undefine error twice */
    }
}