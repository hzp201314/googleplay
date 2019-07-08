package com.hzp.googleplay.view.randomlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

/**
 * created by hzp on 2019/6/26 13:19
 * 作者：codehan
 * 描述：
 */
public class StellarMap extends FrameLayout implements OnGestureListener, View.OnTouchListener, Animation.AnimationListener {

    private Adapter mAdapter;
    private RandomLayout.Adapter mShownGroupAdapter;
    private RandomLayout.Adapter mHiddenGroupAdapter;

    private int mShownGroupIndex;// 显示的组
    private int mHidenGroupIndex;// 隐藏的组
    private int mGroupCount;// 组数
    private RandomLayout mHidenGroup;
    private RandomLayout mShownGroup;
    private GestureDetector mGestureDetector;
    private Animation zoomInNearAnim;
    private Animation zoomInAwayAnim;
    private Animation zoomOutNearAnim;
    private Animation zoomOutAwayAnim;
    private Animation panOutAnim;
    private Animation panInAnim;

    /*构造方法*/
    public StellarMap(@NonNull Context context) {
        super( context );
        init();
    }

    public StellarMap(@NonNull Context context, @Nullable AttributeSet attrs) {
        super( context, attrs );
        init();
    }

    public StellarMap(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super( context, attrs, defStyleAttr );
        init();
    }

    /*初始化方法*/
    private void init() {
        mGroupCount=0;
        mHidenGroupIndex=-1;
        mShownGroupIndex=-1;
        mHidenGroup = new RandomLayout( getContext() );
        mShownGroup = new RandomLayout( getContext() );

        addView( mHidenGroup,new LayoutParams( RandomLayout.LayoutParams.FILL_PARENT, RandomLayout.LayoutParams.FILL_PARENT ) );
        mHidenGroup.setVisibility( GONE );
        addView( mShownGroup,new LayoutParams( RandomLayout.LayoutParams.FILL_PARENT, RandomLayout.LayoutParams.FILL_PARENT ) );

        mGestureDetector = new GestureDetector( this );
        setOnTouchListener( this );

        //设置动画
        zoomInNearAnim = AnimationUtil.createZoomInNearAnim();
        zoomInNearAnim.setAnimationListener( this );
        zoomInAwayAnim = AnimationUtil.createZoomInAwayAnim();
        zoomInAwayAnim.setAnimationListener( this );
        zoomOutNearAnim = AnimationUtil.createZoomOutNearAnim();
        zoomOutNearAnim.setAnimationListener( this );
        zoomOutAwayAnim = AnimationUtil.createZoomOutAwayAnim();
        zoomOutAwayAnim.setAnimationListener( this );
    }

    /**
     * 设置隐藏组和显示组的x和y的规则
     * @param xRegularity
     * @param yRegularity
     */
    public void setRegularity(int xRegularity,int yRegularity){
        mHidenGroup.setRegularity(xRegularity,yRegularity);
        mShownGroup.setRegularity(xRegularity,yRegularity);
    }

    /**
     * 设置孩子适配器
     */
    private void setChildAdapter(){
        if(null==mAdapter){
            return;
        }

        mHiddenGroupAdapter=new RandomLayout.Adapter() {

            @Override
            public int getCount() {
                return mAdapter.getCount( mHidenGroupIndex );
            }
            //取出本Adapter的View对象给HidenGroup的Adapter
            @Override
            public View getView(int position, View convertView) {
                return mAdapter.getView( mHidenGroupIndex,position,convertView );
            }
        };
        mHidenGroup.setAdapter( mHiddenGroupAdapter );
        mShownGroupAdapter=new RandomLayout.Adapter() {
            @Override
            public int getCount() {
                return mAdapter.getCount( mShownGroupIndex );
            }
            //取出本Adapter的View对象给ShownGroup的Adapter
            @Override
            public View getView(int position, View convertView) {
                return mAdapter.getView( mShownGroupIndex,position,convertView );
            }
        };
        mShownGroup.setAdapter( mShownGroupAdapter );
    }

    /**
     * 设置适配器
     * @param adapter
     */
    public void setAdapter(Adapter adapter){
        mAdapter=adapter;
        mGroupCount=mAdapter.getGroupCount();
        if(mGroupCount>0){
            mShownGroupIndex=0;
        }
        setChildAdapter();
    }

    /**
     * 设置显示区域
     * @param paddingLeft
     * @param paddingTop
     * @param paddingRight
     * @param paddingBottom
     */
    public void setInnerPadding(int paddingLeft,int paddingTop,int paddingRight,int paddingBottom){
        mHidenGroup.setPadding( paddingLeft,paddingTop,paddingRight,paddingBottom );
        mShownGroup.setPadding( paddingLeft,paddingTop,paddingRight,paddingBottom );
    }

    /**
     * 给指定的Group设置动画
     * @param groupIndex
     * @param playAnimation
     */
    public void setGroup(int groupIndex,boolean playAnimation){
        switchGroup(groupIndex,playAnimation,zoomInNearAnim,zoomInAwayAnim);
    }

    /**
     * 获取当前显示的group角标
     * @return
     */
    public int getCurrentGroup(){
        return mShownGroupIndex;
    }

    /**
     * 给Group设置动画入
     */
    public void zoomIn(){
        final int nextGroupIndex=mAdapter.getNextGroupOnZoom( mShownGroupIndex,true );
        switchGroup( nextGroupIndex,true,zoomOutNearAnim,zoomOutAwayAnim );
    }

    /**
     * 给Group设置出动画
     */
    public void zoomOut(){
        final int nextGroupIndex=mAdapter.getNextGroupOnZoom( mShownGroupIndex,false );
        switchGroup( nextGroupIndex,true,zoomOutNearAnim,zoomOutAwayAnim );
    }

    /**
     * 给Group设置动画
     * @param degree
     */
    public void pan(float degree){
        final int nextGroupIndex=mAdapter.getNextGroupOnPan( mShownGroupIndex,degree );
        panInAnim = AnimationUtil.creatPanInAnim( degree );
        panInAnim.setAnimationListener( this );
        panOutAnim = AnimationUtil.createPanOutAnim( degree );
        panOutAnim.setAnimationListener( this );
        switchGroup( nextGroupIndex,true,panInAnim,panOutAnim );
    }

    /**
     * 给下一个Group设置进出动画
     * @param newGroupIndex
     * @param playAnimation
     * @param inAnim
     * @param outAnim
     */
    private void switchGroup(int newGroupIndex, boolean playAnimation, Animation inAnim, Animation outAnim) {
        if(newGroupIndex<0||newGroupIndex>=mGroupCount){
            return;
        }
        //把当前显示Group角标设置为隐藏的
        mHidenGroupIndex=mShownGroupIndex;
        //把下一个Group角标设置为显示的
        mShownGroupIndex=newGroupIndex;
        // 交换两个Group
        RandomLayout temp = this.mShownGroup;
        mShownGroup=mHidenGroup;
        mShownGroup.setAdapter( mShownGroupAdapter );
        mHidenGroup=temp;
        mHidenGroup.setAdapter( mHiddenGroupAdapter );
        //刷新显示的Group
        mShownGroup.refresh();
        //显示Group
        mShownGroup.setVisibility( VISIBLE );

        //启动动画
        if(playAnimation){
            if(mShownGroup.hasLayouted()){
                mShownGroup.startAnimation( inAnim );
            }
            mHidenGroup.startAnimation( outAnim );
        }else {
            mHidenGroup.setVisibility( GONE );
        }
    }

    /**
     * 重新分配显示区域
     */
    public void redistribute(){
        mShownGroup.redistribute();
    }

    /**
     * 动画监听
     * @param animation
     */
    @Override
    public void onAnimationStart(Animation animation) {
        // 当动画启动
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // 当动画结束
        if(animation==zoomInAwayAnim||animation==zoomOutAwayAnim||animation==panOutAnim){
            mHidenGroup.setVisibility( GONE );
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        //当动画重复
    }

    /**
     * 定位
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //用以判断ShownGroup是否onLayout的变量
        boolean hasLayoutedBefore = mShownGroup.hasLayouted();
        super.onLayout( changed, left, top, right, bottom );
        if(!hasLayoutedBefore&&mShownGroup.hasLayouted()){
            mShownGroup.startAnimation( zoomInNearAnim );//第一次layout的时候启动动画
        }else {
            mShownGroup.setVisibility( VISIBLE );
        }
    }

    /**
     * 重写onTouch事件，把onTouch事件分配给手势识别
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent( event );
    }

    /**
     * 消费掉onDown事件
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    /**
     * 空实现
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int centerX = getMeasuredWidth() / 2;
        int centerY = getMeasuredHeight() / 2;

        int x1= (int) (e1.getX()-centerX);
        int y1= (int) (e1.getY()-centerY);
        int x2= (int) (e2.getX()-centerX);
        int y2= (int) (e2.getY()-centerY);

        if((x1*x1+y1*y1)>(x2*x2+y2*y2)){
            zoomOut();
        }else {
            zoomIn();
        }
        return true;
    }

    /** 内部类、接口 */
    public static interface Adapter {
        public abstract int getGroupCount();

        public abstract int getCount(int group);

        public abstract View getView(int group, int position, View convertView);

        public abstract int getNextGroupOnPan(int group, float degree);

        public abstract int getNextGroupOnZoom(int group, boolean isZoomIn);
    }
}
