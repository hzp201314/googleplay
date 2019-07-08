package com.hzp.googleplay.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.hzp.googleplay.global.MyApp;
import com.hzp.googleplay.global.UILOption;
import com.hzp.googleplay.http.Url;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * created by hzp on 2019/6/27 10:54
 * 作者：codehan
 * 描述：
 */
public class ImageScaleAdapter extends BasePagerAdapter{
    public ImageScaleAdapter(ArrayList<String> urlList) {
        super( urlList );
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PhotoView imageView = new PhotoView( MyApp.context );
        ImageLoader.getInstance().displayImage( Url.IMG_PREFIX+urlList.get( position ),imageView, UILOption.options );
        container.addView( imageView );
        return imageView;
    }
}
