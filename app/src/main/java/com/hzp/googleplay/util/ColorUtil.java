package com.hzp.googleplay.util;

import android.graphics.Color;

import java.util.Random;

/**
 * created by hzp on 2019/6/26 22:29
 * 作者：codehan
 * 描述：
 */
public class ColorUtil {

    /**
     * 随机生成rgb颜色
     * @return
     */
    public static int randomBeautifulColor(){
        Random random = new Random();
        int red = random.nextInt( 200 );
        int green = random.nextInt( 200 );
        int blue = random.nextInt( 200 );
        return Color.rgb( red,green,blue );
    }
}
