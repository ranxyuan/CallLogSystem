package com.ran.ssm.hive;

import com.ran.ssm.domain.CallLog;
import com.ran.ssm.domain.CallLogStat;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service("hiveCallLogService")
public class HiveCallLogService {

    //hiveserver2连接串
    private static String url = "jdbc:hive2://r201:10000/" ;
    //驱动程序类
    private static String driverClass = "org.apache.hive.jdbc.HiveDriver" ;

    static{
        try {
            //静态代码块注册驱动
            Class.forName(driverClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查询最近的通话记录,使用hive进行mr查询.
     */
    public CallLog findLatestCallLog(String phoneNum){
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement st = conn.createStatement();
//            System.out.println(conn);
            String sql = "select * from ext_calllogs_in_hbase where id like '%"+ phoneNum+"%' order by callTime desc limit 1" ;
            ResultSet rs = st.executeQuery(sql);
            CallLog log = null ;
            if(rs.next()){
                log = new CallLog();
                log.setCaller(rs.getString("caller"));
                log.setCallee(rs.getString("callee"));
                log.setCallTime(rs.getString("callTime"));
                log.setCallDuration(rs.getString("callDuration"));
            }
            rs.close();
            return log ;
//            System.out.println(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    /**
     * 通过hive的hiveserver服务来进行查询，是直接通过hive进行的查询.这里是在hive里开的hive2服务
     * 查询指定人员指定年份中各个月份的通话次数
     */
    public List<CallLogStat> statCallLogsCount(String caller, String year){
        List<CallLogStat> list = new ArrayList<CallLogStat>() ;
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement st = conn.createStatement();
            String sql = "select count(*) ,substr(calltime,1,6) from ext_calllogs_in_hbase " +
                    "where caller = '" + caller+"' and substr(calltime,1,4) == '" + year
                    + "' group by substr(calltime,1,6)";      //注意sql字符串里面不能加分号
            ResultSet rs = st.executeQuery(sql);
            CallLog log = null;
            while (rs.next()) {
                CallLogStat logSt = new CallLogStat();
                logSt.setCount(rs.getInt(1));
                logSt.setYearMonth(rs.getString(2));
                list.add(logSt);
            }
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过spark的standalong模式来进行查询，没有运行成功！！（包的依赖关系比较多吧）
     * 查询指定人员指定年份中各个月份的通话次数
     */
//    public List<CallLogStat> statCallLogsCount(String caller, String year){
//        SparkSession sess = SparkSession.builder().appName("SparkHive").enableHiveSupport().master("spark://r201:7077").getOrCreate();
//        String sql = "select count(*) ,substr(calltime,1,6) from ext_calllogs_in_hbase " +
//                "where caller = '" + caller + "' and substr(calltime,1,4) == '" + year
//                + "' group by substr(calltime,1,6) order by substr(calltime,1,6) asc";
//        Dataset<Row> df = sess.sql(sql);
//        List<Row> rows = df.collectAsList();
//        List<CallLogStat> list = new ArrayList<CallLogStat>();
//        for(Row row : rows){
//            list.add(new CallLogStat(row.getString(2),row.getInt(1)));
//        }
//        return list;
//    }


    /**
     * 通过spark的thriftserver服务来进行查询，是通过spark进行的查询.这里是在spark里开的hive2服务
     * 查询指定人员指定年份中各个月份的通话次数
     */
//    public List<CallLogStat> statCallLogsCount(String caller, String year){
//        try{
//            Connection conn = DriverManager.getConnection(url);
//            String sql = "select count(*) ,substr(calltime,1,6) from ext_calllogs_in_hbase " +
//                    "where caller = '" + caller + "' and substr(calltime,1,4) == '" + year
//                    + "' group by substr(calltime,1,6) order by substr(calltime,1,6) desc";
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//
//            List<CallLogStat> list = new ArrayList<CallLogStat>();
//            while (rs.next()) {
//                long count = rs.getLong(1);
//                String ym = rs.getString(2);
//                list.add(new CallLogStat(ym, (int)count));
//            }
//            rs.close();
//            st.close();
//            conn.close();
//            return list ;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null ;
//    }
}
