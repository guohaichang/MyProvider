package com.hdib.provider;

import android.util.Log;

/**
 * @author: hdib
 * @time: 2018/5/20 11:20
 * @e-mail: guohaichang@163.com
 * @des:
 */
public class LogUtil {
    private static final String TAG = "MyProvider";
    private static final boolean isDebug = true;

    public static void v(String content) {
        if (isDebug) {
            Log.v(TAG, content);
        }
    }

    public static void d(String content) {
        if (isDebug) {
            Log.d(TAG, content);
        }
    }

    public static void i(String content) {
        if (isDebug) {
            Log.i(TAG, content);
        }
    }

    public static void w(String content) {
        if (isDebug) {
            Log.w(TAG, content);
        }
    }

    public static void e(String content) {
        if (isDebug) {
            Log.e(TAG, content);
        }
    }

}
