package src.models;

public class Cursor {
    private int row;
    private int col;

    public Cursor () {
        this.row = 1;
        this.col = 1;
    }

    public Cursor (int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }
}
