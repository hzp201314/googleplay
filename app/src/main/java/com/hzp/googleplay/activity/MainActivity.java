package com.hzp.googleplay.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.hzp.googleplay.R;
import com.hzp.googleplay.adapter.ExamplePagerAdapter;
import com.hzp.googleplay.adapter.MainAdapter;
import com.hzp.googleplay.ext.titles.ScaleTransitionPagerTitleView;
import com.hzp.googleplay.fragment.AppFragment;
import com.hzp.googleplay.fragment.CategoryFragment;
import com.hzp.googleplay.fragment.GameFragment;
import com.hzp.googleplay.fragment.HomeFragment;
import com.hzp.googleplay.fragment.HotFragment;
import com.hzp.googleplay.fragment.RecommendFragment;
import com.hzp.googleplay.fragment.SubjectFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @Bind(R.id.magic_indicator6)
    MagicIndicator magicIndicator;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    private List<String> mDataList =new ArrayList<String>(  );
    private ExamplePagerAdapter mExamplePagerAdapter;
    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ButterKnife.bind( this );

        setActionBar();
        initData();
//        initMagicIndicator6();


    }

    /**
     *初始化数据
     */
    private void initData() {
        //获取标题数据
        String[] titles = getResources().getStringArray(R.array.tab_names);
        mDataList=Arrays.asList( titles );
        mExamplePagerAdapter = new ExamplePagerAdapter( mDataList );
        viewPager.setAdapter( mExamplePagerAdapter );
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new AppFragment());
        fragments.add(new GameFragment());
        fragments.add(new SubjectFragment());
        fragments.add(new RecommendFragment());
        fragments.add(new CategoryFragment());
        fragments.add(new HotFragment());

        //1.给ViewPager填充数据
        MainAdapter mainAdapter = new MainAdapter( getSupportFragmentManager(), fragments, titles );
        viewPager.setAdapter( mainAdapter );

        //2.让ViewPager和指示器绑定
        initMagicIndicator6();

    }

    private void initMagicIndicator6() {
        magicIndicator.setBackgroundColor( Color.WHITE );
        CommonNavigator commonNavigator = new CommonNavigator( this );
        commonNavigator.setAdapter( new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView( context );
                simplePagerTitleView.setText( mDataList.get( index ) );
                simplePagerTitleView.setTextSize( 18 );
                simplePagerTitleView.setNormalColor( Color.GRAY );
                simplePagerTitleView.setSelectedColor( Color.BLACK );
                simplePagerTitleView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem( index );
                    }
                } );
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator( context );
                indicator.setColors( Color.parseColor( "#ff4a42" ), Color.parseColor( "#fcde64" ), Color.parseColor( "#73e8f4" ), Color.parseColor( "#76b0ff" ), Color.parseColor( "#c683fe" ) );
                return indicator;
            }
        } );
        magicIndicator.setNavigator( commonNavigator );
        ViewPagerHelper.bind( magicIndicator, viewPager );
    }

    private void setActionBar(){
        //1.获取ActionBar对象
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle( getResources().getString( R.string.app_name ) );//修改标题
        //设置图标的，但是该方法在高版本无效，低版本是有效的
        //        actionBar.setIcon(R.mipmap.ic_launcher);

        //2.让ActionBar可以点击
        actionBar.setDisplayShowHomeEnabled( true );//让home按钮可以被点击
        actionBar.setDisplayHomeAsUpEnabled( true );//显示home按钮

        //3.让ActionBar的home按钮变身为汉堡包按钮
        drawerToggle = new ActionBarDrawerToggle( this, drawerLayout, 0, 0 );
        drawerToggle.syncState();//变身为汉堡包

        //4.在菜单滑动过程中让汉堡包按钮执行动画
        drawerLayout.addDrawerListener( drawerToggle );

    }

    /**
     * 当汉堡包被点击的时候执行的方法
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //如果drawerLayout已经打开，则关闭，
        //        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
        //            //需要关闭
        //            drawerLayout.closeDrawer(Gravity.LEFT);
        //        }else {
        //            //需要打开
        //            drawerLayout.openDrawer(Gravity.LEFT);
        //        }

        //drawerToggle内部封装了对菜单打开与关闭的判断，我们只需要调用即可
        drawerToggle.onOptionsItemSelected( item );
        return super.onOptionsItemSelected( item );
    }
}
