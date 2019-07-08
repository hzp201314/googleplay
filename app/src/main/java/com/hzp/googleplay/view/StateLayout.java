package com.hzp.googleplay.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.hzp.googleplay.R;

/**
 * created by hzp on 2019/6/25 11:26
 * 作者：codehan
 * 描述：选择FrameLayout的原因：FrameLayout代码量最少，最轻量级！
 */
public class StateLayout extends FrameLayout{

    private View loadingView;
    private View errorView;
    private View successView;

    public StateLayout(@NonNull Context context) {
        this( context,null );
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this( context, attrs,0 );
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super( context, attrs, defStyleAttr );
        //初始化View
        initView();
    }

    /**
     * 添加那3个子View：加载中的，加载成功的，加载失败的
     */
    private void initView() {
        //1.加载loadingView
        loadingView = View.inflate( getContext(), R.layout.page_loading, null );
        addView( loadingView );

        //2.添加失败的View
        errorView = View.inflate( getContext(), R.layout.page_error, null );
        Button btn_reload = (Button)errorView.findViewById( R.id.btn_reload );
        btn_reload.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.先显示loadingView
                showLoadingView();

                //2.点击的时候再一次重新加载数据
                if(listener!=null){
                    listener.onReload();
                }
            }
        } );

        addView( errorView );
        //3.由于成功的View是动态的，所以提供一个方法，让外界传入


        //一开始隐藏所有的View
        hideAll();
    }

    /**
     * 设置一个成功的View进来 通过bindSuccessView()调用方法添加进来
     * @param view
     */
    public void bindSuccessView(View view){
        successView = view;

        if(successView!=null){
            successView.setVisibility( INVISIBLE );//隐藏successView
            //将它添加进来
            addView( successView );
        }
    }


    public void showSuccessView(){
        //先隐藏其他的
        hideAll();

        if(successView!=null){
            successView.setVisibility( VISIBLE );
        }
    }
    public void showErrorView(){
        //先隐藏其他的
        hideAll();

        errorView.setVisibility( VISIBLE );

    }
    public void showLoadingView(){
        //先隐藏其他的
        hideAll();

        loadingView.setVisibility( VISIBLE );
    }

    /**
     * 隐藏所有的View
     */
    public void hideAll(){
        loadingView.setVisibility( INVISIBLE );
        errorView.setVisibility( INVISIBLE );
        if(successView!=null){
            successView.setVisibility( INVISIBLE );
        }
    }


    private OnReloadListener listener;
    public void setOnReloadListener(OnReloadListener listener){
        this.listener=listener;
    }
    public interface OnReloadListener{
        /*当重新加载的按钮被点击的时候调用*/
        void onReload();
    }
}
