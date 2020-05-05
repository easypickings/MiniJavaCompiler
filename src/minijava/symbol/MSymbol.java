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
}