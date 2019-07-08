package com.hzp.googleplay.util;

import com.hzp.googleplay.global.MyApp;

/**
 * created by hzp on 2019/6/26 22:17
 * 作者：codehan
 * 描述：
 */
public class DimenUtil {
    /**
     * 获取dimes.xml中定义的dp值，并会自动转为像素
     * @param resId
     * @return
     */
    public static int getDimens(int resId){
        return MyApp.context.getResources().getDimensionPixelSize( resId );
    }
}
