package src.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Function;

import src.models.Action;
import src.models.ActionType;

public class ActionBuilder implements Runnable {
    private Function<Action, Void> actionCallback;
    private boolean listen;
    Thread thread;

    public ActionBuilder(Function<Action, Void> actionCallback) {
        this.actionCallback = actionCallback;
        this.listen = false;
        this.thread = new Thread(this);
    }

    public void listenToInput() {
        listen = true;
        thread.start();
    }

    public void close() {
        listen = false;
        thread.interrupt();
    }

    private Action buildAction(String input) {
        int spIdx = input.indexOf(" ");
        String cmd = input.substring(0, spIdx == -1 ? input.length() : spIdx);
        String data = spIdx == -1 ? null : input.substring(spIdx + 1);

        switch (cmd) {
            case "C":
                return new Action(ActionType.CURSOR, data);
            case "I":
                return new Action(ActionType.INSERT, data);
            case "D":
                return new Action(ActionType.DELETE);
            case "S":
                return new Action(ActionType.SAVE);
            case "E":
                return new Action(ActionType.EXIT);
            default:
                System.out.println("Invalid action");
                return null;
        }
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(
                new InputStreamReader(System.in, "UTF-8"))) {
            String s = "";

            while (listen) {
                s = input.readLine();
                Action action = buildAction(s);

                if (action != null) {
                    // Call the callback function in editor
                    actionCallback.apply(action);
                }
            }

            input.close();
        } catch (Exception e) {
            System.out.println("IOException: " + e);
        }
    }
}
