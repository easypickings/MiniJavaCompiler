package minijava.symbol;

import static minijava.minijava2piglet.PigletWriter.T;
import static minijava.minijava2piglet.PigletWriter.move;

/**
 * Class for variables
 */
public class MVar extends MSymbol {

    /**
     * Type string
     */
    protected String type;
    /**
     * Class/Method belonging to (WARN: either MClass or MMethod only)
     */
    protected MSymbol owner;
    /**
     * Whether this variable is initiated
     */
    protected boolean initiated = false;
    /**
     * Whether this variable is used
     */
    protected boolean used = false;
    /**
     * TEMP number bound
     */
    protected int temp = -1;
    /**
     * Literal value: integer, true(1) and false(0)
     */
    protected String value;
    /**
     * Whether value is loaded to TEMP
     */
    protected boolean valueLoad = false;
    /**
     * Whether this is a member variable
     */
    protected boolean isMember = false;

    /**
     * for temporary (anonymous) variable
     */
    public MVar(String _name, String _type) {
        name = _name;
        type = _type;
    }

    public MVar(String _name, String _type, int _row, int _col) {
        this(_name, _type);
        row = _row;
        col = _col;
    }

    public MVar(String _name, String _type, MSymbol _owner, int _row,
                int _col) {
        this(_name, _type, _row, _col);
        owner = _owner;
    }

    public MVar Initiated() {
        initiated = true;
        return this;
    }

    public MVar Used() {
        used = true;
        return this;
    }

    public String getTypeString() {
        return type;
    }

    public MType getType() {
        return MTypeList.getType(type);
    }

    public int getOffset() {
        if (owner instanceof MClass)
            return ((MClass) owner).varOffsets.get(name);
        return -1;
    }

    public int getTemp() {
        if (value != null && !valueLoad) {
            move(temp, value);
            valueLoad = true;
        }
        return temp;
    }

    public MVar setTemp(int _temp) {
        temp = _temp;
        return this;
    }

    public String getExp() {
        // simple literal values need not to move to TEMP first
        if (value != null) return value;
        return T(temp);
    }

    public MVar setValue(String _value) {
        value = _value;
        return this;
    }

    public boolean isMember() {
        return isMember;
    }

    public void Print(String indent) {
        System.out.print(indent + type + " " + name);
    }
}