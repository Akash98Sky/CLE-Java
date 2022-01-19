package src.models;

import java.util.ArrayList;

public class Page {
    private ArrayList<StringBuilder> lines;

    public Page(String text) {
        this.lines = new ArrayList<StringBuilder>();
        for (String line : text.split("\n")) {
            this.lines.add(new StringBuilder(line));
        }
    }

    public Page() {
        this.lines = new ArrayList<StringBuilder>();
    }

    public static Page clone(Page page) {
        return new Page(page.getText());
    }

    public void appendLine(String line) {
        this.lines.add(new StringBuilder(line));
    }

    public void insertLine(int line, String text) {
        if (text == null)
            text = "";
        while (lines.size() < line - 1) {
            lines.add(new StringBuilder());
        }

        lines.add(line - 1, new StringBuilder(text));
    }

    public void deleteLine(int line) {
        if (line <= getLinesCount())
            this.lines.remove(line - 1);
    }

    public String getLine(int line) {
        return line <= getLinesCount() ? lines.get(line - 1).toString() : "";
    }

    public StringBuilder getLineBuilder(int line) {
        return line <= getLinesCount() ? lines.get(line - 1) : null;
    }

    public String getText() {
        StringBuilder text = new StringBuilder();
        for (StringBuilder line : lines) {
            text.append(line);
            text.append("\n");
        }
        text.deleteCharAt(text.length() - 1);
        return text.toString();
    }

    public int getLinesCount() {
        return lines.size();
    }

    public int getLineLength(int line) {
        return line <= getLinesCount() ? lines.get(line - 1).length() : 0;
    }
}
