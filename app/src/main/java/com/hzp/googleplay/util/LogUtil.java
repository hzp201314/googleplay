package com.hzp.googleplay.util;

import android.util.Log;

/**
 * created by hzp on 2019/6/27 14:13
 * 作者：codehan
 * 描述：Log管理类
 */
public class LogUtil {
    private static final String TAG = "LogUtil";
    //是否是开发调试环境，当项目上线的时候，将该变量置为false即可
    private static boolean isDebug = true;

    /**
     * 打印含tag的d级别的log
     * @param tag
     * @param msg
     */
    public static void d(String tag,String msg){
        if(isDebug){
            Log.d( tag, msg );
        }
    }
    /**
     * 打印含tag的e级别的log
     * @param tag
     * @param msg
     */
    public static void e(String tag,String msg){
        if(isDebug){
            Log.e(tag,msg);
        }
    }

    /**
     * 打印d级别的log，不需要传tag
     * @param msg
     */
    public static void d(String msg){
        if(isDebug){
            Log.d(TAG,msg);
        }
    }

    /**
     * 打印e级别的log，不需要传tag
     * @param msg
     */
    public static void e(String msg){
        if(isDebug){
            Log.e(TAG,msg);
        }
    }

}
