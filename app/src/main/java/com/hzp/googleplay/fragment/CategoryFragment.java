package com.hzp.googleplay.fragment;

import android.view.View;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.hzp.googleplay.R;
import com.hzp.googleplay.adapter.CategoryAdapter;
import com.hzp.googleplay.bean.Category;
import com.hzp.googleplay.http.HttpHelper;
import com.hzp.googleplay.http.Url;
import com.hzp.googleplay.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * created by hzp on 2019/6/26 11:02
 * 作者：codehan
 * 描述：
 */
public class CategoryFragment extends BaseFragment{

    private ListView listView;
    //存放title和SubCategory的集合
    ArrayList<Object> list=new ArrayList<>(  );

    @Override
    public View getSuccessView() {
        listView = (ListView) View.inflate( getContext(), R.layout.listview, null );
        return listView;
    }

    @Override
    public void loadData() {
        HttpHelper.create()
                .get( Url.Category, new HttpHelper.HttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        stateLayout.showSuccessView();
                        //集合中放的是大分类数据
                        //将所有Category的title和SubCategory放入list中
                        ArrayList<Category> categories = (ArrayList<Category>) GsonUtil.parseJsonToList( result, new TypeToken<List<Category>>() {
                        }.getType() );

                        for (Category category:categories){
                            //1.将title放入list中
                            list.add( category.title );
                            //2.将infos的所有SubCategory放入list
                            list.addAll( category.infos );
                        }
                        //设置adapter
                        listView.setAdapter( new CategoryAdapter(list) );
                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                } );
    }
}
