package com.hzp.googleplay.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hzp.googleplay.global.UILOption;
import com.hzp.googleplay.http.Url;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * created by hzp on 2019/6/26 10:31
 * 作者：codehan
 * 描述：
 */
public class HomePagerAdapter extends BasePagerAdapter{

    public HomePagerAdapter(ArrayList<String> urlList) {
        super( urlList );
    }

    @Override
    public int getCount() {
        return urlList.size()*1000000;//FUCK What's mean?
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView( container.getContext() );
        //设置ImageView的宽高可以铺满四边
        imageView.setScaleType( ImageView.ScaleType.FIT_XY );
        //加载图片
        ImageLoader.getInstance().displayImage( Url.IMG_PREFIX+urlList.get( position%urlList.size() ),imageView, UILOption.options );
        container.addView( imageView );
        return imageView;
    }
}
