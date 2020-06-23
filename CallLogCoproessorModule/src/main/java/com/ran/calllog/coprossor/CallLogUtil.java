package com.ran.calllog.coprossor;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */
public class CallLogUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat sdfFriend = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    //格式化
    private static DecimalFormat df = new DecimalFormat();

    /**
     * 获取hash值,默认分区数100
     */
    public static String getHashcode(String caller, String callTime,int partitions) {
        int len = caller.length();
        //取出后四位电话号码
        String last4Code = caller.substring(len - 4);
        //取出时间单位,年份和月份.
        String mon = callTime.substring(0, 6);
        //
        int hashcode = (Integer.parseInt(mon) ^ Integer.parseInt(last4Code)) % partitions;
        return df.format(hashcode);
    }

    /**
     * 起始时间
     */
    public static String getStartRowkey(String caller, String startTime, int partitions){
        String hashcode = getHashcode(caller, startTime,partitions);
        return hashcode + "," + caller + "," + startTime ;
    }

    /**
     * 结束时间
     */
    public static String getStopRowkey(String caller, String startTime,String endTime, int partitions){
        String hashcode = getHashcode(caller, startTime,partitions);
        return hashcode + "," + caller + "," + endTime ;
    }

    /**
     * 对时间进行格式化
     */
    public static String formatDate(String timeStr){
        try {
            return sdfFriend.format(sdf.parse(timeStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }
}
