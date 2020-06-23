package com.ran.ssm.util;

import com.ran.ssm.domain.CallLogRange;

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
    public static String getHashcode(String caller, String callTime, int partitions) {
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
     * 计算查询时间范围
     */
    public static List<CallLogRange> getCallLogRanges(String startStr ,String endStr){
        try{
            SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdfYM = new SimpleDateFormat("yyyyMM");
            DecimalFormat df00 = new DecimalFormat("00");

            //
            List<CallLogRange> list = new ArrayList<CallLogRange>();
            //字符串时间
            String startPrefix = startStr.substring(0, 6);

            String endPrefix = endStr.substring(0, 6);
            int endDay = Integer.parseInt(endStr.substring(6, 8));
            //结束点
            String endPoint = endPrefix + df00.format(endDay + 1);

            //日历对象
            Calendar c = Calendar.getInstance();

            //同年月
            if (startPrefix.equals(endPrefix)) {
                CallLogRange range = new CallLogRange();
                range.setStartPoint(startStr);          //设置起始点

                range.setEndPoint(endPoint);            //设置结束点
                list.add(range);
            } else {
                //1.起始月
                CallLogRange range = new CallLogRange();
                range.setStartPoint(startStr);

                //设置日历的时间对象
                c.setTime(sdfYMD.parse(startStr));
                c.add(Calendar.MONTH, 1);
                range.setEndPoint(sdfYM.format(c.getTime()));
                list.add(range);

                //是否是最后一月
                while (true) {
                    //到了结束月份
                    if (endStr.startsWith(sdfYM.format(c.getTime()))) {
                        range = new CallLogRange();
                        range.setStartPoint(sdfYM.format(c.getTime()));
                        range.setEndPoint(endPoint);
                        list.add(range);
                        break;
                    } else {
                        range = new CallLogRange();
                        //起始时间
                        range.setStartPoint(sdfYM.format(c.getTime()));

                        //增加月份
                        c.add(Calendar.MONTH, 1);
                        range.setEndPoint(sdfYM.format(c.getTime()));
                        list.add(range);
                    }
                }
            }
            return list ;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null ;
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
