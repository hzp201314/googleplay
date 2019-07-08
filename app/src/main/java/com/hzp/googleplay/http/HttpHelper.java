package com.hzp.googleplay.http;

import android.text.TextUtils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * created by hzp on 2019/6/25 15:00
 * 作者：codehan
 * 描述：对HttpUtils进行的简单的封装
 */
public class HttpHelper {

    private final HttpUtils httpUtils;
    private static HttpHelper mInstance=new HttpHelper();
    private HttpHelper() {
        httpUtils = new HttpUtils();
        //httpUtil自带一个数据的内存缓存，也就是说它会把json数据缓存在内存中1分钟
        httpUtils.configHttpCacheSize( 0 );//禁用xuitl的数据的内存缓存
    }
    public static HttpHelper create(){
        return mInstance;
    }

    /**
     * 执行get请求的方法
     * @param url
     * @param callback
     */
    public void get(final String url,final HttpCallback callback){
        //优先读取缓存数据
        String cacheData = CacheManager.create().getCacheData( url );
        if(TextUtils.isEmpty(cacheData)){
            //说明没有缓存数据,那么应该从网络读取
            requestDataFromNet(url,callback);
        }else {
            //直接将缓存数据交给外界
            callback.onSuccess( cacheData );
        }
    }

    /**
     * 从网络请求数据
     * @param url
     * @param callback
     */
    private void requestDataFromNet(final String url, final HttpCallback callback) {
        httpUtils.send( HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                //对数据进行缓存操作，以及其他的统一处理操作
                CacheManager.create().saveCacheData( url,result );

                callback.onSuccess( result );
            }

            @Override
            public void onFailure(HttpException e, String s) {
                //将异常带给外界
                callback.onFail( e );
            }
        } );
    }

    //我们定义的回调接口，目的就是将返回的数据带给外界
    public interface HttpCallback{
        void onSuccess(String result);
        void onFail(Exception e);
    }
}
