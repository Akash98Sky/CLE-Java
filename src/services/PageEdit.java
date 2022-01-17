package src.services;

import src.models.Action;
import src.models.ActionType;
import src.models.Cursor;
import src.models.Page;

public class PageEdit {
    private Page page;
    private Cursor cursor;
    private PageHistory history;

    public PageEdit(Page page) {
        this.page = page;
        this.cursor = new Cursor();
        this.history = new PageHistory(10, 2);
    }

    public PageEdit(String pageContent) {
        this.page = new Page(pageContent);
        this.cursor = new Cursor();
        this.history = new PageHistory(10, 2);
    }
    
    public Page getPage() {
        return page;
    }

    public String buildLineNo(int line) {
        return String.format("%2d", line) + " |";
    }

    public String editView(int lines) {
        StringBuilder pageText = new StringBuilder();

        for (int i = 0; i < lines && cursor.getRow() + i <= page.getLineCount(); i++) {
            pageText.append(buildLineNo(cursor.getRow() + i) + page.getLine(cursor.getRow() + i) + "\n");

            // Build cursor position
            if (i == 0) {
                pageText.append(String.format("%2d", cursor.getCol()) + " <");
                for (int j = 1; j < cursor.getCol(); j++) {
                    pageText.append(" ");
                }
                pageText.append("^\n");
            }
        }

        return pageText.toString();
    }

    public void applyAction(Action action) {
        history.saveState(new Page(page.getText()), new Cursor(cursor.getRow(), cursor.getCol()));

        if (action.getType() == ActionType.CURSOR) {
            String data[] = action.getData().split(" ");

            cursor.setRow(Integer.parseInt(data[0]));
            if(data.length > 1) {
                cursor.setCol(Integer.parseInt(data[1]));
            }
        } else {
            action.setCurrentCursor(cursor);
            action.applyOnPage(page);
        }
    }

    public void close() {
        history.dispose();
    }
}
