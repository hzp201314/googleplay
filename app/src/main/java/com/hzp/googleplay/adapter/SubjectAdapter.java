package com.hzp.googleplay.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzp.googleplay.R;
import com.hzp.googleplay.bean.Subject;
import com.hzp.googleplay.global.UILOption;
import com.hzp.googleplay.http.Url;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * created by hzp on 2019/6/26 11:48
 * 作者：codehan
 * 描述：
 */
public class SubjectAdapter extends MyBaseAdapter<Subject> {
    public SubjectAdapter(ArrayList<Subject> list) {
        super( list );
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.adapter_subject;
    }

    @Override
    public Object createViewHolder(View convertView, int position) {
        return new SubjectHolder( convertView );
    }

    @Override
    protected void bindViewHolder(Subject subject, Object holder, int position) {
        SubjectHolder subjectHolder = (SubjectHolder) holder;
        subjectHolder.tvTitle.setText( subject.des );
        ImageLoader.getInstance().displayImage( Url.IMG_PREFIX+subject.url,subjectHolder.ivImage, UILOption.options );
    }

    static class SubjectHolder {
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_title)
        TextView tvTitle;

        SubjectHolder(View view) {
            ButterKnife.bind( this, view );
        }
    }
}
