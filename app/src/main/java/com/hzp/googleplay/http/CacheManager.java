package com.hzp.googleplay.http;

import android.os.Environment;

import com.hzp.googleplay.global.MyApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * created by hzp on 2019/6/25 16:03
 * 作者：codehan
 * 描述：缓存数据的管理类：
 * 1. 将json数据存入sd，以及取出来
 * 2. 维护缓存数据的有效期
 */
public class CacheManager {
    //定义缓存目录： /mnt/sdcard/包名/cache
    public static final String CACHE_DIR= Environment.getExternalStorageDirectory()
            + File.separator+ MyApp.context.getPackageName()
            + File.separator+ "cache";
    //缓存文件的有效期限
//    public static final long CACHE_DURATION=1000*60*60*2;//2小时
    public static final long CACHE_DURATION=1000*60;//1分钟

    public static CacheManager mInstance=new CacheManager();

    private CacheManager(){
        //创建缓存的文件目录
        File dir =new File( CACHE_DIR );
        if(!dir.exists()){
            dir.mkdirs();//创建多级目录
        }
    }

    public static CacheManager create(){
        return mInstance;
    }

    /**
     * 保存缓存数据
     * @param url
     * @param json
     */
    public void saveCacheData(String url,String json){
        try {
            //1.创建缓存文件
            File file = new File( CACHE_DIR, URLEncoder.encode( url ) );
            if(!file.exists()){
                file.createNewFile();
            }
            //2.将json数据写入到file中
            FileWriter writer = new FileWriter( file );
            writer.write( json );
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据url获取缓存数据
     * @param url
     * @return
     */
    public String getCacheData(String url){
        StringBuilder builder = new StringBuilder();
        try {
            File file = new File( CACHE_DIR, URLEncoder.encode( url ) );
            if(file.exists()){
                //1.构建要读取的文件
                if(isValid(file)){
                    //2.从file中读取出json数据
                    FileReader reader = new FileReader( file );
                    BufferedReader br = new BufferedReader( reader );
                    String line=null;
                    while ((line=br.readLine())!=null){
                        //拼接字符串
                        builder.append( line );
                    }
                    reader.close();

                }else {
                    //删除无效的缓存文件
                    file.delete();
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * 判断该文件是否有效
     * @param file
     * @return
     */
    private boolean isValid(File file){
        //文件存在了多长时间
        long existDuration = System.currentTimeMillis() - file.lastModified();
        return existDuration<=CACHE_DURATION;
    }


}
