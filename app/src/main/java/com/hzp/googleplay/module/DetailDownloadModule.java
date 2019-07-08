package com.hzp.googleplay.module;

import android.widget.Button;
import android.widget.ProgressBar;

import com.hzp.googleplay.R;
import com.hzp.googleplay.bean.AppInfo;
import com.hzp.googleplay.http.download.DownloadInfo;
import com.hzp.googleplay.http.download.DownloadManager;
import com.hzp.googleplay.util.ApkUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * created by hzp on 2019/6/27 11:27
 * 作者：codehan
 * 描述：
 */
public class DetailDownloadModule extends BaseModule<AppInfo> implements DownloadManager.DownloadObserver {
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.btn_download)
    Button btnDownload;

    @Override
    public int getLayoutId() {
        return R.layout.layout_detail_download;
    }

    private AppInfo appInfo;

    @Override
    public void bindData(AppInfo appInfo) {
        this.appInfo = appInfo;
        DownloadManager.create().registerDownloadObserver( this );

        DownloadInfo downloadInfo = DownloadManager.create().getDownloadInfo( appInfo );
        if (downloadInfo != null) {
            onDownloadUpdate( downloadInfo );
        }


    }

    @Override
    public void onDownloadUpdate(DownloadInfo downloadInfo) {
        if (appInfo == null || appInfo.id != downloadInfo.id) {

            return;
        }
        switch (downloadInfo.state) {
            case DownloadManager.STATE_NONE:
                btnDownload.setText( "下载" );
                break;
            case DownloadManager.STATE_DOWNLOADING:
                int progress = caculateProgress( downloadInfo );
                btnDownload.setText( progress + "%" );
                break;
            case DownloadManager.STATE_PAUSE:
                btnDownload.setText( "继续下载" );
                caculateProgress( downloadInfo );
                break;
            case DownloadManager.STATE_FINISH:
                btnDownload.setText( "安装" );
                break;
            case DownloadManager.STATE_ERROR:
                btnDownload.setText( "失败，重下" );
                break;
            case DownloadManager.STATE_WAITING:
                btnDownload.setText( "等待中..." );
                break;
        }
    }

    private int caculateProgress(DownloadInfo downloadInfo) {
        int progress = (int) (downloadInfo.currentLength * 100f / downloadInfo.size + 0.5f);
        btnDownload.setBackgroundResource( 0 );
        pbProgress.setProgress( progress );
        return progress;
    }

    @OnClick(R.id.btn_download)
    public void onClick() {
        DownloadInfo downloadInfo = DownloadManager.create().getDownloadInfo( appInfo );
        if (downloadInfo == null) {
            DownloadManager.create().pause( appInfo );
        } else {
            if (downloadInfo.state == DownloadManager.STATE_DOWNLOADING || downloadInfo.state == DownloadManager.STATE_WAITING) {
                DownloadManager.create().pause( appInfo );
            } else if (downloadInfo.state == DownloadManager.STATE_PAUSE || downloadInfo.state == DownloadManager.STATE_ERROR) {
                DownloadManager.create().download( appInfo );
            } else if (downloadInfo.state == DownloadManager.STATE_FINISH) {
                ApkUtil.install(downloadInfo.path);
            }
        }
    }

    public void removeObserver() {
        DownloadManager.create().unregisterDownloadObserver( this );
    }
}
