package src.services;

import src.models.Action;
import src.models.ActionType;
import src.models.Cursor;
import src.models.Page;
import src.models.Snapshot;

public class PageEdit {
    private Page page;
    private Cursor cursor;
    private PageHistory history;

    private boolean updateCursorPos(int row, int col) {
        if (row == cursor.getRow() && col == cursor.getCol()) {
            return false;
        }

        cursor.setRow(row);
        cursor.setCol(col);
        return true;
    }

    private boolean perform(Action action) {
        StringBuilder builder = page.getLineBuilder(cursor.getRow());
        int row = cursor.getRow(), col = cursor.getCol();

        switch (action.getType()) {
            case CURSOR_ROW:
                row = action.getDataAsInt();
                col = Math.min(col, page.getLineLength(row) + 1);
                break;
            case CURSOR_COL:
                col = Math.min(action.getDataAsInt(), page.getLineLength(row) + 1);
                break;
            case INSERT:
                if (builder == null) {
                    page.insertLine(row, action.getData());
                    col = action.getData().length();
                } else {
                    builder.insert(col - 1, action.getData());
                    col += action.getData().length();
                }
                break;
            case DELETE:
                if (builder == null) {
                    row--;
                } else if (col >= 2) {
                    builder.deleteCharAt(col - 2);
                    col--;
                } else if (row > 1) {
                    StringBuilder prevLine = page.getLineBuilder(row - 1);
                    col = prevLine.length() + 1;
                    prevLine.append(builder);
                    page.deleteLine(row);
                    row--;
                }
                break;
            case UNDO:
                Snapshot undo = history.undo();
                if (undo != null) {
                    page = undo.getPage();
                    row = undo.getCursor().getRow();
                    col = undo.getCursor().getCol();
                }
                break;
            case REDO:
                Snapshot redo = history.redo();
                if (redo != null) {
                    page = redo.getPage();
                    row = redo.getCursor().getRow();
                    col = redo.getCursor().getCol();
                }
                break;
            default:
                break;
        }

        return updateCursorPos(row, col);
    }

    public PageEdit(Page page) {
        this.page = page;
        this.cursor = new Cursor();
        this.history = new PageHistory(10, 4);
        history.initState(page, cursor);
    }

    public Page getPage() {
        return page;
    }

    public String buildLineNo(int line) {
        return String.format("%2d", line) + " |";
    }

    public String editView(int lines) {
        StringBuilder pageText = new StringBuilder();

        int start = Math.max(cursor.getRow() - lines / 2, 1);
        for (int i = start; i <= cursor.getRow(); i++) {
            pageText.append(buildLineNo(i) + page.getLine(i) + "\n");
            lines--;
        }

        // Build Cursor line
        pageText.append(String.format("%2d", cursor.getCol()) + " <");
        int cursorPad = cursor.getCol();
        pageText.append(String.format("%" + cursorPad + "c", '^') + "\n");

        for (int i = cursor.getRow() + 1; i <= cursor.getRow() + lines; i++) {
            pageText.append(buildLineNo(i) + page.getLine(i) + "\n");
        }

        return pageText.toString();
    }

    public void applyAction(Action action) {
        boolean res = perform(action);

        if (res && (action.getType() == ActionType.INSERT || action.getType() == ActionType.DELETE)) {
            history.saveState(page, cursor);
        }
    }

    public void close() {
        history.dispose();
    }
}
