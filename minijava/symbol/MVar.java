/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 17:12:16
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-05 21:41:02
 * @Description  : Class for variables
 * @FilePath     : \Compiler\minijava\symbol\MVar.java
 */

package symbol;

/**
 * Class for variables
 */
public class MVar extends MSymbol {

    /** Type string */
    protected String type;
    /**
     * Class/Method belonging to (WARN: ONLY instance of MClass or MMethod are
     * acceptable)
     */
    protected MSymbol owner;

    /** for temporary (anonymous) variable */
    public MVar(String _type, int _row, int _col) {
        super(_row, _col);
        name = null;
        type = _type;
        owner = null;
    }

    public MVar(String _name, String _type, MSymbol _owner, int _row, int _col) {
        super(_name, _row, _col);
        type = _type;
        /** check owner's legitimacy */
        if (!(_owner instanceof MClass || _owner instanceof MMethod)) {
            System.out.println("FATAL: instance of Mvar is constructed with illegal param 'owner'. Exiting...");
            System.exit(-1);
        }
        owner = _owner;
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
        System.out.print(indent + "\33[33;4m" + type + " \33[32;4m" + name + "\33[0m");
    }
}