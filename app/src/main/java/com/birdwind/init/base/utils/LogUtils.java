package com.birdwind.init.base.utils;

import com.birdwind.init.BuildConfig;
import android.text.TextUtils;
import android.util.Log;

/**
 * Log日誌
 *
 */
public class LogUtils {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final int DEBUG_LEVEL = 0;

    private static final int WRITE_LEVEL = 1;

    private static final int ERROR_LEVEL = 2;

    private static final int INFORMATION_LEVEL = 3;

    /**
     * 獲取當前Class名稱
     *
     * @return String
     */
    private static String getClassName() {
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        String result = thisMethodStack.getClassName();
        result = result.substring(result.lastIndexOf(".") + 1);
        return result;
    }

    /**
     * System.out.println輸出
     *
     * @param tag tag名稱
     * @param message 輸出內容
     */
    public static void print(String tag, String message) {
        if (DEBUG) {
            System.out.println(getClassName() + " : " + message);
        }
    }

    /**
     * System.out.println輸出
     *
     * @param message 輸出內容
     */
    public static void print(String message) {
        print(getClassName(), message);
    }

    /**
     * Log輸出
     *
     * @param level log的等級
     * @param tag tag名稱
     * @param message log輸出內容
     */
    private static void log(int level, String tag, String message) {
        if (DEBUG) {
            switch (level) {
                case DEBUG_LEVEL:
                    Log.d(tag, message);
                    break;
                case WRITE_LEVEL:
                    Log.w(tag, message);
                    break;
                case ERROR_LEVEL:
                    Log.e(tag, message);
                    break;
                case INFORMATION_LEVEL:
                    Log.i(tag, message);
                    break;
            }
        }
    }

    public static void exceptionTAG(String tag, Throwable e) {
        if (!TextUtils.isEmpty(e.getMessage())) {
            log(ERROR_LEVEL, tag, e.getMessage());
        }
    }

    public static void exception(Throwable e) {
        log(ERROR_LEVEL, getClassName(), e.getMessage());
    }

    /**
     * Log.d輸出
     *
     * @param message log輸出內容
     */
    public static void d(String message) {
        log(DEBUG_LEVEL, getClassName(), message);
    }

    /**
     * Log.d輸出
     *
     * @param title title
     * @param message log輸出內容
     */
    public static void d(String title, String message) {
        log(DEBUG_LEVEL, getClassName(), title + " : " + message);
    }

    /**
     * Log.d輸出
     *
     * @param tag tag名稱
     * @param message log輸出內容
     */
    public static void dTAG(String tag, String message) {
        log(DEBUG_LEVEL, tag, message);
    }

    /**
     * Log.w輸出
     *
     * @param message log輸出內容
     */
    public static void w(String message) {
        log(WRITE_LEVEL, getClassName(), message);
    }

    /**
     * Log.w輸出
     *
     * @param title title
     * @param message log輸出內容
     */
    public static void w(String title, String message) {
        log(WRITE_LEVEL, getClassName(), title + " : " + message);
    }

    /**
     * Log.w輸出
     *
     * @param tag tag名稱
     * @param message log輸出內容
     */
    public static void wTAG(String tag, String message) {
        log(WRITE_LEVEL, tag, message);
    }

    /**
     * Log.e輸出
     *
     * @param message log輸出內容
     */
    public static void e(String message) {
        log(ERROR_LEVEL, getClassName(), message);
    }

    /**
     * Log.e輸出
     *
     * @param title title
     * @param message log輸出內容
     */
    public static void e(String title, String message) {
        log(ERROR_LEVEL, getClassName(), title + " : " + message);
    }

    /**
     * Log.e輸出
     *
     * @param tag tag名稱
     * @param message log輸出內容
     */
    public static void eTAG(String tag, String message) {
        log(ERROR_LEVEL, tag, message);
    }

    /**
     * Log.i輸出
     *
     * @param message log輸出內容
     */
    public static void i(String message) {
        log(INFORMATION_LEVEL, getClassName(), message);
    }

    /**
     * Log.i輸出
     *
     * @param title title
     * @param message log輸出內容
     */
    public static void i(String title, String message) {
        log(INFORMATION_LEVEL, getClassName(), title + " : " + message);
    }

    /**
     * Log.i輸出
     *
     * @param tag tag名稱
     * @param message log輸出內容
     */
    public static void iTAG(String tag, String message) {
        log(INFORMATION_LEVEL, tag, message);
    }
}
