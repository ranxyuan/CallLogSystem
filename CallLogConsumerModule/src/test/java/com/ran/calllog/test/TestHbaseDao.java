package com.ran.calllog.test;

import com.ran.calllog.consumer.HbaseDao;
import org.junit.Test;

/**
 *
 */
public class TestHbaseDao {
    @Test
    public void test1(){
        HbaseDao dao =new HbaseDao();
        dao.put("18641241020,15032293356,2017/01/05 07:39:27,507");
    }
}
