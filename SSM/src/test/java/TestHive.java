import org.junit.Test;

import com.ran.ssm.domain.CallLog;
import com.ran.ssm.hive.HiveCallLogService;

/**
 *
 */
public class TestHive {
    @Test
    public void test1(){
        HiveCallLogService s = new HiveCallLogService();
        CallLog log = s.findLatestCallLog("13269361119");

    }

}