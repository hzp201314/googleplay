package com.hzp.googleplay.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.hzp.googleplay.R;
import com.hzp.googleplay.bean.AppInfo;
import com.hzp.googleplay.http.HttpHelper;
import com.hzp.googleplay.http.Url;
import com.hzp.googleplay.module.DetailDesModule;
import com.hzp.googleplay.module.DetailDownloadModule;
import com.hzp.googleplay.module.DetailInfoModule;
import com.hzp.googleplay.module.DetailSafeModule;
import com.hzp.googleplay.module.DetailScreenModule;
import com.hzp.googleplay.util.GsonUtil;
import com.hzp.googleplay.view.StateLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @Bind(R.id.ll_container)
    LinearLayout llContainer;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.fl_download)
    FrameLayout flDownload;
    private String packageName;
    private StateLayout stateLayout;
    private DetailInfoModule infoModule;
    private DetailSafeModule safeModule;
    private DetailScreenModule screenModule;
    private DetailDesModule desModule;
    private DetailDownloadModule downloadModule;
    private AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        packageName = getIntent().getStringExtra( "packageName" );

        setActionBar(  );

        stateLayout = new StateLayout( this );
        setContentView( stateLayout );

        stateLayout.bindSuccessView( getSuccessView() );
        stateLayout.showLoadingView();

        loadData();

    }


    public View getSuccessView() {
        View view = View.inflate( this, R.layout.activity_detail, null );
        ButterKnife.bind( this );
        //1.加入info 模块
        infoModule = new DetailInfoModule();
        llContainer.addView( infoModule.getModuleView() );
        //2.加入safe 模块
        safeModule = new DetailSafeModule();
        llContainer.addView( safeModule.getModuleView() );
        //3.加入screen 模块
        screenModule = new DetailScreenModule();
        llContainer.addView( screenModule.getModuleView() );
        //4.加入des 模块
        desModule = new DetailDesModule();
        llContainer.addView( desModule.getModuleView() );
        desModule.setScrollView( scrollView );
        //5.加入download 模块
        downloadModule = new DetailDownloadModule();
        flDownload.addView( downloadModule.getModuleView() );

        return view;
    }

    private void loadData() {
        String url = String.format( Url.Detail, packageName );
        HttpHelper.create()
                .get( url, new HttpHelper.HttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        stateLayout.showSuccessView();

                        appInfo = GsonUtil.parseJsonToBean( result, AppInfo.class );
                        if(appInfo!=null){
                            updateUI();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                } );
    }

    private void updateUI() {
        infoModule.bindData( appInfo );
        safeModule.bindData( appInfo );
        screenModule.bindData( appInfo );
        desModule.bindData( appInfo );
        downloadModule.bindData( appInfo );
    }


    private void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle( getResources().getString( R.string.app_detail ) );

        actionBar.setDisplayShowHomeEnabled( true );
        actionBar.setDisplayHomeAsUpEnabled( true );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downloadModule.removeObserver();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected( item );
    }
}
