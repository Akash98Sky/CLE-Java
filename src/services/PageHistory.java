package src.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import src.models.Cursor;
import src.models.Page;
import src.models.Snapshot;

public class PageHistory {
    private ArrayList<Snapshot> snapshots;
    private Timer snapshotTimer;
    private int noOfSnaps;
    private int snapIntervalSec;
    
    public PageHistory(int len, int delaySec) {
        snapshots = new ArrayList<Snapshot>();
        noOfSnaps = len;
        snapIntervalSec = delaySec;
    }

    public void initState(Page page, Cursor cursor) {
        if (snapshots.size() > 0) {
            snapshots.clear();
        }
        snapshots.add(new Snapshot(page, cursor));
    }

    public void saveState(Page page, Cursor cursor) {
        if(snapshotTimer != null) {
            snapshotTimer.cancel();
            snapshotTimer = null;
        }

        snapshotTimer = new Timer();
        snapshotTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                snapshots.add(new Snapshot(page, cursor));
                if(snapshots.size() >= noOfSnaps) {
                    snapshots.remove(0);
                }
                snapshotTimer = null;
            }
        }, Date.from(new Date().toInstant().plusSeconds(snapIntervalSec)));
    }

    public void dispose() {
        if(snapshotTimer != null) {
            snapshotTimer.cancel();
            snapshotTimer = null;
        }
        snapshots.clear();
    }
}
