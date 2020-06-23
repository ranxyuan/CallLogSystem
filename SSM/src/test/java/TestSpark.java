//import com.ran.ssm.domain.CallLogStat;
//import com.ran.ssm.util.CharUtil;
//import org.apache.spark.SparkConf;
//import org.apache.spark.SparkContext;
//import org.apache.spark.sql.Dataset;
//import org.apache.spark.sql.Row;
//import org.apache.spark.sql.SparkSession;
//import org.junit.Test;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// */
//public class TestSpark {
//    @Test
//    public void test1(){
//        String caller = "13269361119" ;
//        String year = "2017" ;
//        SparkSession sess = SparkSession.builder().appName("SparkHive").enableHiveSupport().master("spark://r201:7077").getOrCreate();
//        String sql = "select count(*) ,substr(calltime,1,6) from ext_calllogs_in_hbase " +
//                "where caller = '" + caller + "' and substr(calltime,1,4) == '" + year
//                + "' group by substr(calltime,1,6) order by substr(calltime,1,6)";
//        Dataset<Row> df = sess.sql(sql);
//        List<Row> rows = df.collectAsList();
//        List<CallLogStat> list = new ArrayList<CallLogStat>();
//        for (Row row : rows) {
//            System.out.println(row.getString(1));
//            list.add(new CallLogStat(row.getString(1), (int)row.getLong(0)));
//        }
//    }
//
////    使用spark的thriftserver服务器来进行spark操作hive查询hbase数据
//    @Test
//    public void testSparkSQLByThriftServer() throws Exception {
//        Class.forName("org.apache.hive.jdbc.HiveDriver");
//        Connection conn = DriverManager.getConnection("jdbc:hive2://r201:10000");
//
//        String caller = "13269361119" ;
//        String year = "2017" ;
//        String sql = "select count(*) ,substr(calltime,1,6) from ext_calllogs_in_hbase " +
//                "where caller = '" + caller + "' and substr(calltime,1,4) == '" + year
//                + "' group by substr(calltime,1,6) order by substr(calltime,1,6) desc";
//        Statement st = conn.createStatement();
//        ResultSet rs = st.executeQuery(sql);
//        while(rs.next()){
//            long count  = rs.getLong(1);
//            String ym  = rs.getString(2);
//            System.out.println(ym + " : " + count);
//        }
//        rs.close();
//        st.close();
//        conn.close();
//    }
//}
