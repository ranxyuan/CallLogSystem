package com.ran.callloggen;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 *
 */
public class GenDataAppMain {
    static Random r = new Random();

    public static List<String> phoneNumbers = new ArrayList<String>();
    public static Map<String,String> callers = new HashMap<String, String>();
    static{

        callers.put("15810092493", "史玉龙");
        callers.put("18000696806", "赵贺彪");
        callers.put("15151889601", "张倩 ");
        callers.put("13269361119", "王世昌");
        callers.put("15032293356", "张涛");
        callers.put("17731088562", "张阳");
        callers.put("15338595369", "李进全");
        callers.put("15733218050", "杜泽文");
        callers.put("15614201525", "任宗阳");
        callers.put("15778423030", "梁鹏");
        callers.put("18641241020", "郭美彤");
        callers.put("15732648446", "刘飞飞");
        callers.put("13341109505", "段光星");
        callers.put("13560190665", "唐会华");
        callers.put("18301589432", "杨力谋");
        callers.put("13520404983", "温海英");
        callers.put("18332562075", "朱尚宽");
        callers.put("18620192711", "刘能宗");
        phoneNumbers.addAll(callers.keySet());
    }



    public static void main(String[] args) throws Exception {
        genCallLog();
    }

    public static void genCallLog() throws Exception {
//        FileWriter fw = new FileWriter("/home/centos/calllog/calllog.log",true);
        FileWriter fw = new FileWriter(PropertiesUtil.getString("log.file"),true);
        while(true){
            //取主叫
            String caller = phoneNumbers.get(r.nextInt(callers.size()));
            //主叫名字
            String callerName = callers.get(caller);

            //被叫号
            String callee = null;
            String calleeName = null;
            while (true) {
                callee = phoneNumbers.get(r.nextInt(callers.size()));
                if (!caller.equals(callee)) {
                    break;
                }
            }
            calleeName = callers.get(callee);
            //通话时长
            int duration = r.nextInt(PropertiesUtil.getInt("call.duration.max")) + 1;
            DecimalFormat df = new DecimalFormat();
            df.applyPattern(PropertiesUtil.getString("call.duration.format"));
            String durStr = df.format(duration);

            //通话时间
            int year = PropertiesUtil.getInt("call.year");
            //月份(0~11)
            int month = r.nextInt(12);
            //天,范围(1~31)
            int day = r.nextInt(29) + 1;
            int hour = r.nextInt(24);
            int min = r.nextInt(60);
            int sec = r.nextInt(60);

            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, min);
            c.set(Calendar.SECOND, sec);
            Date date = c.getTime();


            //如果时间超过今天就重新qushijian取时间.
            Date now = new Date();
            if (date.compareTo(now) > 0) {
                continue ;
            }
            SimpleDateFormat dfs = new SimpleDateFormat();
            dfs.applyPattern(PropertiesUtil.getString("call.time.format"));
            //通话时间
            String dateStr = dfs.format(date);

            //String log = caller + "," + callerName + "," + callee + "," + calleeName + "," + dateStr + "," + durStr  ;
            String log = caller + "," + callee + "," + dateStr + "," + durStr;
            System.out.println(log);
            fw.write(log + "\r\n");
            fw.flush();
            Thread.sleep(PropertiesUtil.getInt("gen.data.interval.ms"));
        }
    }
 }
