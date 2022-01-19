package src.models;

public class Snapshot {
    private Page page;
    private Cursor cursor;

    public Snapshot(Page page, Cursor cursor) {
        this.page = Page.clone(page);
        this.cursor = Cursor.clone(cursor);
    }

    public Page getPage() {
        return page;
    }

    public Cursor getCursor() {
        return cursor;
    }
}
