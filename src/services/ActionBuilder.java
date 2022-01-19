package src.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Function;

import src.models.Action;

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

        return Action.fromCmd(cmd, data);
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
            e.printStackTrace();
        }
    }
}
