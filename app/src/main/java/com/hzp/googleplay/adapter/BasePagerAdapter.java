package com.hzp.googleplay.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * created by hzp on 2019/6/26 10:32
 * 作者：codehan
 * 描述：
 */
public class BasePagerAdapter extends PagerAdapter{
    ArrayList<String> urlList;
    public BasePagerAdapter(ArrayList<String> urlList) {
        this.urlList=urlList;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem( container, position, object );
        container.removeView( (View) object );
    }
}
