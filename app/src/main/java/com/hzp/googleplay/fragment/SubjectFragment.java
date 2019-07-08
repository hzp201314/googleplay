package com.hzp.googleplay.fragment;

import com.google.gson.reflect.TypeToken;
import com.hzp.googleplay.adapter.MyBaseAdapter;
import com.hzp.googleplay.adapter.SubjectAdapter;
import com.hzp.googleplay.bean.Subject;
import com.hzp.googleplay.http.Url;
import com.hzp.googleplay.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;



/**
 * created by hzp on 2019/6/26 11:01
 * 作者：codehan
 * 描述：
 */
public class SubjectFragment extends PtrListFragment<Subject>{
    @Override
    public MyBaseAdapter<Subject> getAdapter() {
        return new SubjectAdapter(list);
    }

    @Override
    protected void parseDataAndUpdate(String result) {
        //解析json
         ArrayList<Subject> subjects=(ArrayList<Subject>)GsonUtil.parseJsonToList( result, new TypeToken<List<Subject>>() {
        }.getType() );
         if(subjects!=null){
             list.addAll( subjects );
             //更新adapter
             baseAdapter.notifyDataSetChanged();
         }

    }

    @Override
    protected String getUrl() {
        return Url.Subject+list.size();
    }
}
