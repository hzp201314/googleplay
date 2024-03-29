package com.hzp.googleplay.global;

import com.hzp.googleplay.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * created by hzp on 2019/6/26 09:47
 * 作者：codehan
 * 描述：
 */
public class UILOption {
    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading( R.mipmap.ic_default)//设置加载中显示的图片
            .showImageForEmptyUri(R.mipmap.ic_default)//设置url为空显示哪个图
            .showImageOnFail(R.mipmap.ic_default)
            .cacheInMemory(true)//默认会在内存缓存
            .cacheOnDisk(true)//默认会在硬盘缓存
            .displayer(new FadeInBitmapDisplayer(1000)).build();//配置渐渐显示的动画
    //            .displayer(new RoundedBitmapDisplayer(100)).build();//配置圆角的
}
