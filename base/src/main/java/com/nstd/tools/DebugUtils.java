package com.nstd.tools;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Nstd on 17/5/27.
 */

public class DebugUtils {
    private static final String TAG = "DebugUtils";

    private static boolean IS_DEBUG;

    public static void setDebug(boolean isDebug) {
        IS_DEBUG = isDebug;
    }

    public static void trace() {
        toTrace("", -1);
    }

    public static void trace(int maxLevel) {
        toTrace("", maxLevel);
    }

    public static void trace(String msg) {
        toTrace(msg, -1);
    }

    public static void trace(String msg, int maxLevel) {
        toTrace(msg, maxLevel);
    }

    private static void toTrace(String msg, int maxLevel) {
        if(!IS_DEBUG) {
            return ;
        }

        if(msg == null) {
            msg = "";
        }

        String traceInfo = getStackTrace(new Throwable(msg));
        String[] ts = traceInfo.split("\\n");
        int endIdx = ts.length;

        if(maxLevel > 0) {
            if(ts.length > maxLevel + 3) {
                endIdx = maxLevel + 3;
            }
        }

        StringBuilder b = new StringBuilder(msg);
        for(int i = 3; i < endIdx; i++) {
            b.append(ts[i]);
        }
        traceInfo = b.toString().replaceAll("(\\s)+at ", "\n$1 ");

        LogUtils.d(TAG, traceInfo);
    }


    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
