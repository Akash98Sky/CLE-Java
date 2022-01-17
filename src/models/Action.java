package src.models;

public class Action {
    private Cursor cursor;
    private ActionType type;
    private String data;

    public Action(ActionType type) {
        this.type = type;
    }

    public Action(ActionType type, String data) {
        this.type = type;
        this.data = data;
    }

    public void setCurrentCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public ActionType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public boolean applyOnPage(Page page) {
        switch (type) {
            case INSERT:
                page.getLineBuilder(cursor.getRow()).insert(cursor.getCol() - 1, data);
                return true;
            case DELETE:
                page.getLineBuilder(cursor.getRow()).delete(cursor.getCol() - 1, cursor.getCol());
                return true;
            default:
                return false;
        }
    }
}
