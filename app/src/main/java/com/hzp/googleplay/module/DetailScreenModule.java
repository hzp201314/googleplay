package com.hzp.googleplay.module;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hzp.googleplay.R;
import com.hzp.googleplay.activity.DetailActivity;
import com.hzp.googleplay.activity.ImageScaleActivity;
import com.hzp.googleplay.bean.AppInfo;
import com.hzp.googleplay.global.MyApp;
import com.hzp.googleplay.global.UILOption;
import com.hzp.googleplay.http.Url;
import com.hzp.googleplay.util.DimenUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * created by hzp on 2019/6/27 10:39
 * 作者：codehan
 * 描述：
 */
public class DetailScreenModule extends BaseModule<AppInfo> {

    DetailActivity activity;
    @Bind(R.id.ll_image)
    LinearLayout llImage;

    public void setActivity(DetailActivity activity) {
        this.activity = activity;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_detail_screen;
    }
    int width,height,margin;
    @Override
    public void bindData(AppInfo appInfo) {
        width = DimenUtil.getDimens(R.dimen.dp90);
        height = DimenUtil.getDimens(R.dimen.dp150);
        margin = DimenUtil.getDimens(R.dimen.dp12);

        final ArrayList<String> screen = appInfo.screen;
        //遍历集合动态创建IMageVIew，添加进来
        for (int i = 0; i < screen.size(); i++) {
            ImageView imageView = new ImageView( MyApp.context );

            //设置宽高以及margin
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( width, height );
            params.leftMargin=(i==0?0:margin);
            imageView.setLayoutParams( params );

            ImageLoader.getInstance().displayImage( Url.IMG_PREFIX+appInfo.screen.get( i ),imageView, UILOption.options );

            //添加点击事件
            final int finalI = i;//定义临时变量记录i
            imageView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( activity, ImageScaleActivity.class );
                    intent.putStringArrayListExtra( "urlList",screen );
                    intent.putExtra( "currentItem", finalI );
                    activity.startActivity( intent );
                }
            } );

            llImage.addView( imageView );
        }
    }
}
