package com.hzp.googleplay.adapter;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hzp.googleplay.R;
import com.hzp.googleplay.bean.AppInfo;
import com.hzp.googleplay.global.MyApp;
import com.hzp.googleplay.global.UILOption;
import com.hzp.googleplay.http.Url;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * created by hzp on 2019/6/26 09:15
 * 作者：codehan
 * 描述：
 */
public class HomeAdapter extends MyBaseAdapter<AppInfo> {

    public HomeAdapter(ArrayList<AppInfo> list) {
        super( list );
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.adapter_home;
    }

    @Override
    public Object createViewHolder(View convertView, int position) {
        return new HomeHolder( convertView );
    }

    @Override
    protected void bindViewHolder(AppInfo appInfo, Object holder, int position) {
        HomeHolder homeHolder = (HomeHolder) holder;
        homeHolder.tvName.setText( appInfo.name );
        homeHolder.rbStar.setRating( appInfo.stars );
        homeHolder.tvSize.setText( Formatter.formatFileSize( MyApp.context,appInfo.size) );
        homeHolder.tvDes.setText( appInfo.des );

        //加载图片
        ImageLoader.getInstance().displayImage( Url.IMG_PREFIX+appInfo.iconUrl,homeHolder.ivImage, UILOption.options );
    }

    //    @Override
    //    public View getView(int position, View convertView, ViewGroup parent) {
    //        HomeHolder holder;
    //        if (convertView == null) {
    //            convertView = View.inflate(parent.getContext(), R.layout.adapter_home, null);
    //            holder = new HomeHolder(convertView);
    //            //设置tag
    //            convertView.setTag(holder);
    //        } else {
    //            holder = (HomeHolder) convertView.getTag();
    //        }
    //        //绑定数据
    //        AppInfo appInfo = list.get(position);
    //        holder.tvName.setText(appInfo.name);
    //        holder.rbStar.setRating(appInfo.stars);
    //        holder.tvSize.setText(Formatter.formatFileSize(parent.getContext(),appInfo.size));
    //        holder.tvDes.setText(appInfo.des);
    //
    //        return convertView;
    //    }

    static class HomeHolder {
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.rb_star)
        RatingBar rbStar;
        @Bind(R.id.tv_size)
        TextView tvSize;
        @Bind(R.id.tv_des)
        TextView tvDes;

        HomeHolder(View view) {
            ButterKnife.bind( this, view );
        }
    }
}
