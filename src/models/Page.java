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

    public void appendLine(String line) {
        this.lines.add(new StringBuilder(line));
    }

    public String getLine(int line) {
        return lines.get(line - 1).toString();
    }

    public StringBuilder getLineBuilder(int line) {
        return lines.get(line - 1);
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

    public int getLineCount() {
        return lines.size();
    }

    public int getLineLength(int line) {
        return lines.get(line - 1).length();
    }
}
