package com.ran.calllog.consumer;

import java.io.InputStream;
import java.util.Properties;
/**
 *
 * 使用类加载器加载外部属性文件，返回一个输入流
 */
public class PropertiesUtil {
    public static Properties props ;
    static{
        try {
            //加载外部属性文件
            InputStream in = ClassLoader.getSystemResourceAsStream("kafka.properties");
            props = new Properties();
            //利用load方法将流内的属性传入配置对象
            props.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getProp(String key){
        return props.getProperty(key) ;
    }
}