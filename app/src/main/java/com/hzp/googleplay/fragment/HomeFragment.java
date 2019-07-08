package com.hzp.googleplay.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;

import com.hzp.googleplay.R;
import com.hzp.googleplay.activity.DetailActivity;
import com.hzp.googleplay.adapter.HomeAdapter;
import com.hzp.googleplay.adapter.HomePagerAdapter;
import com.hzp.googleplay.adapter.MyBaseAdapter;
import com.hzp.googleplay.bean.AppInfo;
import com.hzp.googleplay.bean.Home;
import com.hzp.googleplay.http.Url;
import com.hzp.googleplay.util.GsonUtil;

import java.net.URL;


/**
 * created by hzp on 2019/6/25 11:19
 * 作者：codehan
 * 描述：
 */
public class HomeFragment extends PtrListFragment<AppInfo>{

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage( msg );
            //接着发
            handler.sendEmptyMessageDelayed( 0,2000 );
        }
    };
    private ViewPager viewPager;

    @Override
    public void onStart() {
        super.onStart();
        //发送一个延时消息
        handler.sendEmptyMessageDelayed( 0,2000 );
    }

    @Override
    public void onStop() {
        super.onStop();
        //停止自动轮播的行为
        handler.removeMessages( 0 );
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent( getActivity(), DetailActivity.class );

        intent.putExtra( "packageName",list.get( position-2 ).packageName );
        startActivity( intent );
    }

    @Override
    protected void addHeaderView() {
        View headerView = View.inflate( getContext(), R.layout.layout_home_header, null );
        viewPager = (ViewPager) headerView.findViewById( R.id.viewPager );
        listView.addHeaderView( headerView );
    }

    @Override
    public MyBaseAdapter<AppInfo> getAdapter() {
        return new HomeAdapter(list);
    }

    @Override
    protected void parseDataAndUpdate(String result) {
        Home home = GsonUtil.parseJsonToBean( result, Home.class );
        if(home!=null){
            //给ViewPager设置adapter
            if(home.picture!=null&&home.picture.size()>0){
                viewPager.setAdapter( new HomePagerAdapter(home.picture) );
            }
            //添加数据
            list.addAll( home.list );
            baseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected String getUrl() {
        return Url.Home+list.size();
    }
}
