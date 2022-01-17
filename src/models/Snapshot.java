package src.models;

public class Snapshot {
    private Page page;
    private Cursor cursor;

    public Snapshot(Page page, Cursor cursor) {
        this.page = page;
        this.cursor = cursor;
    }

    public Page getPage() {
        return page;
    }

    public Cursor getCursor() {
        return cursor;
    }
}
