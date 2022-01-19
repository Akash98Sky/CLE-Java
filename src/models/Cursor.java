package src.models;

public class Cursor {
    private int row;
    private int col;

    public Cursor() {
        this.row = 1;
        this.col = 1;
    }

    public Cursor(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Cursor clone(Cursor cursor) {
        return new Cursor(cursor.getRow(), cursor.getCol());
    }

    public void setRow(int row) {
        this.row = Math.max(row, 1);
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = Math.max(col, 1);
    }

    public int getCol() {
        return col;
    }
}
