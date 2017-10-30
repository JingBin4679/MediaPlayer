package com.easedroid.mplayer.utils;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bin.jing on 2017/10/30.
 */

public class TimeTracker {
    private static final Map<String, Long> trackers = new HashMap<>();
    private static final String TAG = "TimeTracker";

    private TimeTracker() {
    }

    public static void time(String tag) {
        long time = System.nanoTime();
        synchronized (trackers) {
            if (trackers.containsKey(tag)) {
                long remove = trackers.remove(tag);
                printTime(tag, (time - remove));
                return;
            }
            trackers.put(tag, time);
        }
    }

    private static final void printTime(String tag, long duration) {
        Log.d(TAG, String.format("%s : %s ms", tag, duration / 1000000));
    }

}
