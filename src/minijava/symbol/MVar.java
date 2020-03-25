package minijava.symbol;

/**
 * Class for variables
 */
public class MVar extends MSymbol {

    /**
     * Type string
     */
    protected String type;
    /**
     * Class/Method belonging to (WARN: ONLY instance of MClass or MMethod are
     * acceptable)
     */
    protected MSymbol owner;
    /**
     * Whether this variable is initiated
     */
    protected boolean initiated;
    /**
     * Whether this variable is used
     */
    protected boolean used;

    /**
     * for temporary (anonymous) variable
     */
    public MVar(String _name, String _type, int _row, int _col) {
        super(_row, _col);
        name = _name;
        type = _type;
        owner = null;
        initiated = false;
        used = false;
    }

    public MVar(String _name, String _type, MSymbol _owner, int _row,
                int _col) {
        this(_name, _type, _row, _col);
        /* check owner's legality */
        if (!(_owner instanceof MClass || _owner instanceof MMethod)) {
            System.err.println("FATAL: instance of Mvar is constructed with "
                    + "illegal param \"owner\". Exiting...");
            System.exit(-1);
        }
        owner = _owner;
    }

    public void Initiated() {
        initiated = true;
    }

    public void Used() {
        used = true;
    }

    public String getTypeString() {
        return type;
    }

    public MType getType() {
        return MTypeList.getType(type);
    }

    public MSymbol getOwner() {
        return owner;
    }

    public void Print(String indent) {
        System.out.print(indent + "\33[33;4m" + type
                + "\33[0m \33[32;4m" + name + "\33[0m");
    }
}