package src.models;

import java.util.Map;

public class Action {
    public static final String[] CMD_LIST = {
            "Row: R y",
            "Col: C x",
            "Insert: I \"text\"",
            "Delete: D",
            "Undo: UD",
            "Redo: RD",
            "Save: S",
            "Exit: E" };

    private static final Map<String, ActionType> cmdMap = Map.of(
            "C", ActionType.CURSOR_COL,
            "R", ActionType.CURSOR_ROW,
            "I", ActionType.INSERT,
            "D", ActionType.DELETE,
            "UD", ActionType.UNDO,
            "RD", ActionType.REDO,
            "S", ActionType.SAVE,
            "E", ActionType.EXIT);

    private ActionType type;
    private String data;

    public Action(ActionType type) {
        this.type = type;
    }

    public Action(ActionType type, String data) {
        this.type = type;
        this.data = data;
    }

    public ActionType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public int getDataAsInt() {
        return Integer.parseInt(data);
    }

    public static Action fromCmd(String cmd, String args) {
        if (args != null) {
            args = args.trim();
            int s, e;
            if ((s = args.indexOf("\"")) != -1 && (e = args.lastIndexOf("\"")) != -1) {
                args = args.substring(s + 1, e);
            }
        }

        if (cmdMap.get(cmd.toUpperCase()) == null) {
            System.out.println("Invalid action");
            return null;
        }

        return new Action(cmdMap.get(cmd.toUpperCase()), args);
    }
}
