package com.rasset.wflaunch.utils;

import android.util.Log;

/**
 * Created by andman on 2015-11-30.
 */
public class Logger {

    private final static String LOG_TAG = "SQApp";
    private final static boolean LOG_ENABLE = true;

    public static void v(String logMe) {
        if( true == LOG_ENABLE ){
            Log.v(LOG_TAG, /* SystemClock.uptimeMillis() + " " + */ logMe);
        }
    }

    public static void v(String tag, String logMe) {
        if( true == LOG_ENABLE ){
            Log.v(LOG_TAG, "[" + tag +"] " + /* SystemClock.uptimeMillis() + " " + */ logMe);
        }
    }
    public static void v(Object obj, String logMe) {
        if( true == LOG_ENABLE ){
            if(obj != null)
            {
                Log.v(LOG_TAG, "[" + obj.getClass().getSimpleName() + "] " + /* SystemClock.uptimeMillis() + " " + */ logMe);
            }
            else
            {
                Log.v(LOG_TAG, "[" + null + "] " + /* SystemClock.uptimeMillis() + " " + */ logMe);
            }
        }
    }

    public static void d(String logMe) {
        if( true == LOG_ENABLE ){
            Log.d(LOG_TAG, logMe);
        }
    }

    public static void d(String tag, String logMe) {
        if( true == LOG_ENABLE ){
            Log.d(LOG_TAG, "[" + tag +"] " + logMe);
        }
    }
    public static void d(Object obj, String logMe) {
        if( true == LOG_ENABLE ){
            if(obj != null)
            {
                Log.d(LOG_TAG, "[" + obj.getClass().getSimpleName() + "] " + /* SystemClock.uptimeMillis() + " " + */ logMe);
            }
            else
            {
                Log.d(LOG_TAG, "[" + null + "] " + /* SystemClock.uptimeMillis() + " " + */ logMe);
            }
        }
    }

    public static void i(String logMe) {
        if( true == LOG_ENABLE ){
            Log.i(LOG_TAG, logMe);
        }
    }

    public static void i(String tag, String logMe) {
        if( true == LOG_ENABLE ){
            Log.i(LOG_TAG, "[" + tag +"] " + logMe);
        }
    }
    public static void i(Object obj, String logMe) {
        if( true == LOG_ENABLE ){
            if(obj != null)
            {
                Log.i(LOG_TAG, "[" + obj.getClass().getSimpleName() + "] " + /* SystemClock.uptimeMillis() + " " + */ logMe);
            }
            else
            {
                Log.i(LOG_TAG, "[" + null + "] " + /* SystemClock.uptimeMillis() + " " + */ logMe);
            }
        }
    }

    public static void w(String logMe) {
        if( true == LOG_ENABLE ){
            Log.w(LOG_TAG, logMe);
        }
    }

    public static void w(String tag, String logMe) {
        if( true == LOG_ENABLE ){
            Log.w(LOG_TAG, "[" + tag +"] " + logMe);
        }
    }
    public static void w(Object obj, String logMe) {
        if( true == LOG_ENABLE ){
            if(obj != null)
            {
                Log.w(LOG_TAG, "[" + obj.getClass().getSimpleName() + "] " + /* SystemClock.uptimeMillis() + " " + */ logMe);
            }
            else
            {
                Log.w(LOG_TAG, "[" + null + "] " + /* SystemClock.uptimeMillis() + " " + */ logMe);
            }
        }
    }

    public static void e(String logMe) {
        if( true == LOG_ENABLE ){
            Log.e(LOG_TAG, logMe);
        }
    }

    public static void e(String tag, String logMe) {
        if( true == LOG_ENABLE ){
            Log.e(LOG_TAG, "[" + tag +"] " + logMe);
        }
    }
    public static void e(Object obj, String logMe) {
        if( true == LOG_ENABLE ){
            if(obj != null)
            {
                Log.e(LOG_TAG, "[" + obj.getClass().getSimpleName() + "] " + /* SystemClock.uptimeMillis() + " " + */ logMe);
            }
            else
            {
                Log.e(LOG_TAG, "[" + null + "] " + /* SystemClock.uptimeMillis() + " " + */ logMe);
            }
        }
    }

    public static void e(String logMe, Exception ex) {
        if( true == LOG_ENABLE ){
            Log.e(LOG_TAG, logMe, ex);
        }
    }

    public static void e(String tag, String logMe, Exception ex) {
        if( true == LOG_ENABLE ){
            Log.e(LOG_TAG, "[" + tag +"] " + logMe, ex);
        }
    }
    public static void e(Object obj, String logMe, Exception ex) {
        if( true == LOG_ENABLE ){
            if(obj != null)
            {
                Log.e(LOG_TAG, "[" + obj.getClass().getSimpleName() + "] " + logMe, ex);
            }
            else
            {
                Log.e(LOG_TAG, "[" + null + "] " + logMe, ex);
            }
        }
    }
}
