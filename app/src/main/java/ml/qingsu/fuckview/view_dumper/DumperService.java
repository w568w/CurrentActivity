package ml.qingsu.fuckview.view_dumper;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import ml.w568w.currentactivity.FloatWindow;

/**
 * Created by w568w on 2017-8-1.
 */
public class DumperService extends AccessibilityService {
    private static DumperService instance;
    private static String activityName = null;
    private static String pkg = null;

    public static DumperService getInstance() {
        return instance;
    }

    public String getPkg() {
        return pkg;
    }

    public String getActivityName() {
        return activityName;
    }

    @Override
    protected void onServiceConnected() {
        instance = this;

        super.onServiceConnected();
    }

    @Override
    public AccessibilityNodeInfo getRootInActiveWindow() {
        try {
            return super.getRootInActiveWindow();
        } catch (Throwable ignored) {
        }
        return null;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        //NOTHING
        int type = accessibilityEvent.getEventType();
        switch (type) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                pkg = accessibilityEvent.getPackageName() == null ? "" : accessibilityEvent.getPackageName().toString();
                activityName = accessibilityEvent.getClassName().toString();
                sendBroadcast(new Intent(FloatWindow.getACTION()));
                break;
        }


    }

    @Override
    public void onInterrupt() {
        //NOTHING
    }
}

