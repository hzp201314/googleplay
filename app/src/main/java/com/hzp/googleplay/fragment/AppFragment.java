package com.hzp.googleplay.fragment;

import com.google.gson.reflect.TypeToken;
import com.hzp.googleplay.adapter.HomeAdapter;
import com.hzp.googleplay.adapter.MyBaseAdapter;
import com.hzp.googleplay.bean.AppInfo;
import com.hzp.googleplay.http.Url;
import com.hzp.googleplay.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * created by hzp on 2019/6/26 10:59
 * 作者：codehan
 * 描述：
 */
public class AppFragment extends PtrListFragment<AppInfo> {
    @Override
    public MyBaseAdapter<AppInfo> getAdapter() {
        return new HomeAdapter( list );
    }

    @Override
    protected void parseDataAndUpdate(String result) {
        //将result解析为装有AppInfo的集合
        ArrayList<AppInfo> appInfos = (ArrayList<AppInfo>) GsonUtil.parseJsonToList( result, new TypeToken<List<AppInfo>>() {
        }.getType() );
        if(appInfos!=null){
            //       list = appInfos;//注意，Do not do this!
            list.addAll( appInfos );
            baseAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected String getUrl() {
        return Url.App+list.size();
    }
}
