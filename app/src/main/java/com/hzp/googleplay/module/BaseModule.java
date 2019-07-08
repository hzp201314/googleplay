package com.hzp.googleplay.module;

import android.view.View;

import com.hzp.googleplay.global.MyApp;

import butterknife.ButterKnife;

/**
 * created by hzp on 2019/6/27 09:02
 * 作者：codehan
 * 描述：负责完成模块类的公共方法定义
 */
public abstract class BaseModule<T> {

    View moduleView;

    public BaseModule() {
        moduleView = View.inflate( MyApp.context, getLayoutId(), null );
        ButterKnife.bind(this,moduleView);
    }
    public View getModuleView(){
        return moduleView;
    }
    /**
     * 初始化模块的View
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 绑定数据
     * @param data
     */
    public abstract void bindData(T data);
}
