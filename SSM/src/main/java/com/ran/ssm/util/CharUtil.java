package com.ran.ssm.util;

import java.util.Random;

/**
 * Created by Administrator on 2017/4/10.
 */
public class CharUtil {
    static Random r = new Random();
    /**
     *得到随机三个中文字符，形成名字
     */
    public static String getName3zh(){
        int start = 0x4e00;
        int end  = 0x9fa5 ;
        int count = end - start + 1 ;
        int first = r.nextInt(count) + start;
        int sec = r.nextInt(count) + start;
        int three= r.nextInt(count) + start;
        String name = (char)first + "" + (char) sec + "" + (char) three ;
        return name ;

    }
}
