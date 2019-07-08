package com.hzp.googleplay.global;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * created by hzp on 2019/6/25 16:12
 * 作者：codehan
 * 描述：自定义的application
 */
public class MyApp extends Application{
    //全局的上下文,就是封装了公共模块api的对象，比如可以获取包名，可以获取Resource对象，SP对象，Window对象
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        //初始化UniversalImageLoader，
        ImageLoader.getInstance().init( ImageLoaderConfiguration.createDefault(this));
    }
}
