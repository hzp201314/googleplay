package com.hzp.googleplay.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.hzp.googleplay.R;
import com.hzp.googleplay.bean.Subject;
import com.hzp.googleplay.http.HttpHelper;
import com.hzp.googleplay.http.Url;
import com.hzp.googleplay.util.ColorUtil;
import com.hzp.googleplay.util.DimenUtil;
import com.hzp.googleplay.util.GsonUtil;
import com.hzp.googleplay.util.ToastUtil;
import com.hzp.googleplay.view.randomlayout.StellarMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * created by hzp on 2019/6/26 11:01
 * 作者：codehan
 * 描述：
 */
public class RecommendFragment extends BaseFragment{

    private StellarMap stellarMap;
    private ArrayList<String> list;

    @Override
    public View getSuccessView() {
        stellarMap = new StellarMap( getContext() );
        //1.设置子View距离边框的距离
        int padding = DimenUtil.getDimens( R.dimen.dp15 );
        stellarMap.setInnerPadding( padding,padding,padding,padding );
        return stellarMap;
    }

    @Override
    public void loadData() {
        HttpHelper.create()
                .get( Url.Recommend, new HttpHelper.HttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        stateLayout.showSuccessView();
                        //解析数据
                        list = (ArrayList<String>) GsonUtil.parseJsonToList( result, new TypeToken<List<String>>() {
                        }.getType() );
                        //更新数据
                        stellarMap.setAdapter( new MyAdapter() );
                        //设置刚进来显示第几组的数据
                        stellarMap.setGroup( 0,true );
                        //设置x和y方向显示的密度，一般的话x*y应该大于每组的数量
                        stellarMap.setRegularity( 4,4 );
                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                } );
    }

    class MyAdapter implements StellarMap.Adapter {
        /**
         * 返回有多少组
         * @return
         */
        @Override
        public int getGroupCount() {
            return list.size()/getCount( 0 );
        }

        /**
         * group这个组有多少个数据
         * @param group
         * @return
         */
        @Override
        public int getCount(int group) {
            return 11;
        }

        /**
         * 返回每个子View对象
         * @param group 当前是第几组
         * @param position 当前组中的position
         * @param convertView
         * @return
         */
        @Override
        public View getView(int group, int position, View convertView) {
            final TextView textView = new TextView( getContext() );

            //list: 0->32
            //position: 0->10
            //group0: 0->10
            //group1: 11->21
            //group2: 22->32

            //1.设置文字
            int listPosition = group * getCount( group ) + position;
            textView.setText( list.get( position ) );
            //2.设置随机的字体大小
            Random random = new Random();
            textView.setTextSize( random.nextInt(15)+12 );//12-26
            //3.上色，设置随机颜色
            //#FF0000
            textView.setTextColor( ColorUtil.randomBeautifulColor() );
            //4.给文字设置点击事件
            textView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast( textView.getText().toString() );
                }
            } );
            return textView;
        }

        /**
         * 该方法在源码中定义了，但是从未使用过 ，因此并没有什么用
         * @param group
         * @param degree
         * @return
         */
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        /**
         * 当缩放动画完毕后下一组加载哪一组的数据
         * @param group 表示当前是第几组的数据
         * @param isZoomIn 是否是播放动画
         * @return 表示下一组将要加载的数据了
         */
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            //0->1->2->0
            return (group+1)%getGroupCount();
        }
    }
}