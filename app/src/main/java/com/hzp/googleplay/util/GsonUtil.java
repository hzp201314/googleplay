package com.hzp.googleplay.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * created by hzp on 2019/6/26 09:50
 * 作者：codehan
 * 描述： 解析Json的封装
 */
public class GsonUtil {
    /**
     * 把一个json字符串变成对象
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T parseJsonToBean(String json,Class<T> cls){
        Gson gson = new Gson();
        T t=null;
        try {
            t=gson.fromJson( json,cls );
        } catch (Exception e) {
            e.printStackTrace();
        }

       return t;
    }

    /**
     * 把json字符串变成集合
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static List<?> parseJsonToList(String json,Type type){
        Gson gson = new Gson();
        List<?> list = gson.fromJson( json, type );
        return list;
    }
}
