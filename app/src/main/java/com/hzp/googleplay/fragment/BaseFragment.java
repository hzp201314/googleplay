package com.hzp.googleplay.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzp.googleplay.view.StateLayout;

/**
 * created by hzp on 2019/6/25 11:20
 * 作者：codehan
 * 描述：fragment 基类
 */
public abstract class BaseFragment extends Fragment implements StateLayout.OnReloadListener {

    StateLayout stateLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //判断如果stateLayout为空，才进行创建,这种做法实现了对Fragment界面的缓存
        if(stateLayout==null) {
            stateLayout = new StateLayout( getContext() );
            //设置成功的View
            stateLayout.bindSuccessView( getSuccessView() );
            //一开始应该显示loading界面
            stateLayout.showLoadingView();
            //设置重新加载按钮被点击的监听器
            stateLayout.setOnReloadListener( this );

            //加载数据
            loadData();
        }
        return stateLayout;
    }
    @Override
    public void onReload() {
        //处理数据的重新加载
        loadData();
    }

    /**
     * 获取成功的View
     * @return
     */
    public abstract View getSuccessView();

    /**
     * 加载数据的方法
     */
    public abstract void loadData();


}
