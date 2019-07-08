package com.hzp.googleplay.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;

/**
 * created by hzp on 2019/6/26 10:52
 * 作者：codehan
 * 描述：
 */
public class MainAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> fragments;
    String[] titles;

    public MainAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
        super( fm );
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get( position );
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
