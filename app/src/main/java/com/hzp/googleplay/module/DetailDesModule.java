package com.hzp.googleplay.module;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hzp.googleplay.R;
import com.hzp.googleplay.anim.HeightAnim;
import com.hzp.googleplay.bean.AppInfo;

import butterknife.Bind;

/**
 * created by hzp on 2019/6/27 11:03
 * 作者：codehan
 * 描述：
 */
public class DetailDesModule extends BaseModule<AppInfo> implements View.OnClickListener {
    @Bind(R.id.tv_des)
    TextView tvDes;
    @Bind(R.id.tv_author)
    TextView tvAuthor;
    @Bind(R.id.iv_des_arrow)
    ImageView ivDesArrow;
    private int maxHeight;//全部的高度
    private int minHeight;//5行时候的高度
    boolean isOpen=false;//是否是展开的

    @Override
    public int getLayoutId() {
        return R.layout.layout_detail_des;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        moduleView.setOnClickListener( this );
        tvDes.setText( appInfo.des );
        tvAuthor.setText( appInfo.author );

        //只需要计算出tvDes的全部高度和5行的高度即可，
        tvDes.getViewTreeObserver().addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tvDes.getViewTreeObserver().removeOnGlobalLayoutListener( this );
                //1.获取全部的高度
                maxHeight = tvDes.getHeight();
                //2.获取5行的高度
                getMinHeight();
            }
        } );

    }

    /**
     * 获取5行的高度
     */
    private void getMinHeight() {
        tvDes.setMaxLines( 5 );//这个操作会引起tvDes进行重新的测量和layout
        //再次监听layout
        tvDes.getViewTreeObserver().addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tvDes.getViewTreeObserver().removeOnGlobalLayoutListener( this );
                //此时就可以获取到5行的高度了
                minHeight = tvDes.getHeight();
                //LogUtil.e("maxHeight: "+maxHeight  +  "   minHeight: "+minHeight);
                //取消maxLine的设定，直接去将textView设置为5行的高度
                tvDes.setMaxLines( Integer.MAX_VALUE );
                ViewGroup.LayoutParams params = tvDes.getLayoutParams();
                params.height=minHeight;
                tvDes.setLayoutParams( params );
            }
        } );
    }

    @Override
    public void onClick(View v) {
        HeightAnim heightAnim=null;
        if(isOpen){
            //关闭
            heightAnim=new HeightAnim( maxHeight,minHeight,tvDes );
        }else {
            //打开
            heightAnim=new HeightAnim( minHeight,maxHeight,tvDes );

            heightAnim.setOnHeightUpdateListener( new HeightAnim.OnHeightUpdateListener() {
                @Override
                public void onHeightUpdate(int animatedValue) {
                    //让scrollView向上滚动
                    scrollView.scrollBy( 0,100 );
                }
            } );
        }
        heightAnim.start( 600 );

        isOpen=!isOpen;
        //旋转箭头
        ViewCompat.animate(ivDesArrow)
                .rotationBy( 180 )
                .setDuration( 600 )
                .start();
    }
    ScrollView scrollView;
    public void setScrollView(ScrollView scrollView){
        this.scrollView=scrollView;
    }
}
