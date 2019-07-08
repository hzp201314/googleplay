package com.hzp.googleplay.util;

import android.content.Intent;
import android.net.Uri;

import com.hzp.googleplay.global.MyApp;

/**
 * created by hzp on 2019/6/27 16:07
 * 作者：codehan
 * 描述：
 */
public class ApkUtil {

    public static void install(String apkFilePath){
        Intent intent = new Intent( Intent.ACTION_VIEW );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        //
        intent.setDataAndType( Uri.parse( "file://"+apkFilePath ),"application/vhd.android.package-archive" );
        MyApp.context.startActivity( intent );
    }
}
