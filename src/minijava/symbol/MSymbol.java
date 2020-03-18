/*
 * @Author       : Can Su
 * @Date         : 2020-03-04 11:06:25
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-05 15:21:34
 * @Description  : Entrance for all classes in symbol
 * @FilePath     : \Compiler\minijava\symbol\MSymbol.java
 */

package minijava.symbol;

/**
 * Entrance for all classes in symbol
 */
public class MSymbol {

    /** Name */
    protected String name;
    /** Row/Col of appearance */
    protected int row = 0, col = 0;

    public MSymbol() {
    }

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

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setName(String _name) {
        name = _name;
    }

    public void setRow(int _row) {
        row = _row;
    }

    public void setCol(int _col) {
        col = _col;
    }
}