package com.hzp.googleplay.util;

import android.widget.Toast;

import com.hzp.googleplay.global.MyApp;

/**
 * created by hzp on 2019/6/26 22:34
 * 作者：codehan
 * 描述：
 */
public class ToastUtil {
    private static Toast toast;

    /**
     * 定义一个强大的，单例的吐司
     * @param text
     */
    public static void showToast(String text){
        if(toast==null){
            //创建吐司
            toast=Toast.makeText( MyApp.context,text,Toast.LENGTH_SHORT );
        }
        //直接更改吐司的文本
        toast.setText( text );
        //最后展示
        toast.show();
    }
}
