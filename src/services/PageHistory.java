package src.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import src.models.Cursor;
import src.models.Page;
import src.models.Snapshot;

// TODO: Fix undo and redo logic (it's not working properly)
public class PageHistory {
    private ArrayList<Snapshot> snapshots;
    private Snapshot lastSnap;
    private int currentSnapPos;
    private Timer snapshotTimer;
    private int noOfSnaps;
    private int snapIntervalSec;

    private void resetTimer() {
        if (snapshotTimer != null) {
            snapshotTimer.cancel();
            snapshotTimer = null;
        }
    }

    public PageHistory(int len, int delaySec) {
        snapshots = new ArrayList<Snapshot>();
        noOfSnaps = len;
        snapIntervalSec = delaySec;
        currentSnapPos = -1;
    }

    public void initState(Page page, Cursor cursor) {
        snapshots.clear();
        snapshots.add(new Snapshot(page, cursor));
        currentSnapPos = 0;
    }

    public void saveState(Page page, Cursor cursor) {
        resetTimer();
        // Remove trailing redo snaps if new state arrived after undo
        snapshots.removeAll(snapshots.subList(currentSnapPos + 1, snapshots.size()));

        lastSnap = new Snapshot(page, cursor);
        snapshotTimer = new Timer();
        snapshotTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (PageHistory.this) {
                    while (snapshots.size() >= noOfSnaps) {
                        snapshots.remove(0);
                        currentSnapPos--;
                    }
                    snapshots.add(lastSnap);
                    currentSnapPos++;
                    snapshotTimer = null;
                    lastSnap = null;
                }
            }
        }, Date.from(new Date().toInstant().plusSeconds(snapIntervalSec)));
    }

    public Snapshot undo() {
        if(lastSnap != null) {
            resetTimer();
            snapshots.add(lastSnap);
            currentSnapPos++;
            lastSnap = null;
        }

        if(currentSnapPos == 0)
            return null;

        return snapshots.get(--currentSnapPos);
    }

    public Snapshot redo() {
        if(currentSnapPos == snapshots.size() - 1)
            return null;

        return snapshots.get(++currentSnapPos);
    }

    public void dispose() {
        if (snapshotTimer != null) {
            snapshotTimer.cancel();
            snapshotTimer = null;
        }
        snapshots.clear();
    }
}
