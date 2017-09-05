package com.example.wangjinchao_pc.library.util;

import android.util.Log;

/**
 * Created by wangjinchao-PC on 2017/9/5.
 */

public class Logger {
    public static final boolean IS_DEBUG=true;

    public static int LOG_LEVEL = 0;
    public static int ERROR = 1;
    public static int WARN = 2;
    public static int INFO = 3;
    public static int DEBUG = 4;
    public static int VERBOS = 5;


    public static void e(Class<?> clzz, String msg) {
        if (IS_DEBUG&&LOG_LEVEL > ERROR)
            Log.e(clzz.getSimpleName(), msg);
    }

    public static void w(Class<?> clzz, String msg) {
        if (IS_DEBUG&&LOG_LEVEL > WARN)
            Log.w(clzz.getSimpleName(), msg);
    }

    public static void i(Class<?> clzz, String msg) {
        if (IS_DEBUG&&LOG_LEVEL > INFO)
            Log.i(clzz.getSimpleName(), msg);
    }

    public static void d(Class<?> clzz, String msg) {
        if (IS_DEBUG&&LOG_LEVEL > DEBUG)
            Log.d(clzz.getSimpleName(), msg);
    }

    public static void v(Class<?> clzz, String msg) {
        if (IS_DEBUG&&LOG_LEVEL > VERBOS)
            Log.v(clzz.getSimpleName(), msg);
    }
}
