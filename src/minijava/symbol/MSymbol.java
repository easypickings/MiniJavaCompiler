<<<<<<< HEAD
package minijava.symbol;

/**
 * Entrance for all classes in symbol
 */
public class MSymbol {

    /**
     * Name
     */
    protected String name;
    /**
     * Row/Col of appearance
     */
    protected int row = 0, col = 0;

    public MSymbol() {}

    public MSymbol(int _row, int _col) {
        row = _row;
        col = _col;
    }

    public MSymbol(String _name, int _row, int _col) {
        name = _name;
        row = _row;
        col = _col;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
=======
package minijava.symbol;

/**
 * Entrance for all classes in symbol
 */
public class MSymbol {
    protected String name;
    protected int row = -1, col = -1;

    public MSymbol() {}

    public MSymbol(String _name, int _row, int _col) {
        name = _name;
        row = _row;
        col = _col;
    }

    public String getName() {
        return name;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
>>>>>>> 9c71cfa... feat(src/spiglet/*): implement register allocation
}