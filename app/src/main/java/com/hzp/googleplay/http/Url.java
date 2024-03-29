package com.hzp.googleplay.http;

/**
 * created by hzp on 2019/6/26 09:45
 * 作者：codehan
 * 描述：
 */
public interface Url {
    //服务器的主机定义，
    String SERVER_HOST = "http://127.0.0.1:8090/";

    //图片的url前缀
    String IMG_PREFIX = SERVER_HOST + "image?name=";

    String Home = SERVER_HOST + "home?index=";
    String App = SERVER_HOST + "app?index=";
    String Game = SERVER_HOST + "game?index=";
    String Subject = SERVER_HOST + "subject?index=";
    String Recommend = SERVER_HOST + "recommend?index=0";
    String Category = SERVER_HOST + "category?index=0";
    String Hot = SERVER_HOST + "hot?index=0";
    String Detail = SERVER_HOST + "detail?packageName=%s";//占位符的方式

    //从头下载
    String Download = SERVER_HOST + "download?name=%s";
    //断点下载
    String BreadDownload = SERVER_HOST + "download?name=%s&range=%d";
}
