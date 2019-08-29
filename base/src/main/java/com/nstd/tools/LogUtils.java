package com.nstd.tools;

import android.util.Log;

/**
 * Created by Nstd on 2019-08-23 11:31.
 */
public class LogUtils {

    private static boolean logEnabled = true;
    private static String TAG = "SimpleRVAdapter";
    private static int LEVEL = Log.VERBOSE;

    public static void init(String tag) {
        LogUtils.TAG = tag;
    }

    public static void setLogLevel(int level) {
        LogUtils.LEVEL = level;
    }

    public static void setEnable(boolean enableLog) {
        LogUtils.logEnabled = enableLog;
    }

    /**
     * Get The Current Function Name
     *
     * @return
     */
    private static String[] getLogInfo() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(LogUtils.class.getName())) {
                continue;
            }

            String tag = st.getFileName();
            if (tag == null) {
                tag = TAG;
            }

            String msgPrefix = "[ " + Thread.currentThread().getName() + "::" + st.getMethodName() + ":" + st.getLineNumber() + " ]";

            return new String[]{tag, msgPrefix};
        }

        return new String[]{TAG, ""};
    }

    /**
     * The Log LEVEL:i
     *
     * @param str
     */
    public static void i(Object str) {
        i(null, str);
    }

    @SuppressWarnings("unused")
    public static void i(String tag, Object str) {

        if (!logEnabled || LEVEL > Log.INFO) {
            return;
        }

        String[] logInfo = getLogInfo();
        Log.i(TAG, (tag == null ? logInfo[0] : tag + " -- " + logInfo[0]) + " : " + logInfo[1] + " -- " + safeToString(str));
    }


    /**
     * The Log LEVEL:d
     *
     * @param str
     */
    public static void d(Object str) {
        d(null, str);
    }

    @SuppressWarnings("unused")
    public static void d(String tag, Object str) {

        if (!logEnabled || LEVEL > Log.DEBUG) {
            return;
        }

        String[] logInfo = getLogInfo();

        Log.d(TAG, (tag == null ? logInfo[0] : tag + " -- " + logInfo[0]) + " : " + logInfo[1] + " -- " + safeToString(str));
    }

    /**
     * The Log LEVEL:V
     *
     * @param str
     */
    public static void v(Object str) {
        v(null, str);
    }

    @SuppressWarnings("unused")
    public static void v(String tag, Object str) {

        if (!logEnabled || LEVEL > Log.VERBOSE) {
            return;
        }

        String[] logInfo = getLogInfo();
        Log.v(TAG, (tag == null ? logInfo[0] : tag + " -- " + logInfo[0]) + " : " + logInfo[1] + " -- " + safeToString(str));
    }

    /**
     * The Log LEVEL:w
     *
     * @param str
     */
    public static void w(Object str) {
        w(null, str);
    }

    @SuppressWarnings("unused")
    public static void w(String tag, Object str) {

        if (!logEnabled || LEVEL > Log.WARN) {
            return;
        }

        String[] logInfo = getLogInfo();
        Log.w(TAG, (tag == null ? logInfo[0] : tag + " -- " + logInfo[0]) + " : " + logInfo[1] + " -- " + safeToString(str));
    }

    /**
     * The Log LEVEL:e
     *
     * @param str
     */
    public static void e(Object str) {
        e(null, str);
    }

    @SuppressWarnings("unused")
    public static void e(String tag, Object str) {

        if (!logEnabled || LEVEL > Log.ERROR) {
            return;
        }

        String[] logInfo = getLogInfo();
        String errormsg = (tag == null ? logInfo[0] : tag + " -- " + logInfo[0]) + " : " + logInfo[1] + " -- " + safeToString(str);
        Log.e(TAG, errormsg);
    }

    /**
     * The Log LEVEL:e
     *
     * @param tr
     */
    public static void e(Throwable tr) {
        e(null, "Exception: ", tr);
    }

    /**
     * The Log LEVEL:e
     *
     * @param log
     * @param tr
     */
    public static void e(String log, Throwable tr) {
        e(null, log, tr);
    }

    public static void e(String tag, String log, Throwable tr) {

        if (!logEnabled || LEVEL > Log.ERROR) {
            return;
        }

        String[] logInfo = getLogInfo();
        String errormsg = (tag == null ? logInfo[0] : tag + " -- " + logInfo[0]) + " : " + logInfo[1] + " -- " + log;
        Log.e(TAG, errormsg, tr);
    }


    /**
     * The Log LEVEL:e
     *
     * @param str
     */
    public static void l(Object str) {
        l(null, str);
    }

    public static void l(String tag, Object str) {

        if (!logEnabled || LEVEL > Log.ERROR) {
            return;
        }

        String[] logInfo = getLogInfo();
        Log.e(TAG, (tag == null ? logInfo[0] : tag + " -- " + logInfo[0]) + " : " + logInfo[1] + " -- " + safeToString(str));
    }

    private static String safeToString(Object obj) {
        return obj == null ? "null" : obj.toString();
    }
}
