package com.ran.calllog.coprossor;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 协处理器,
 */
public class CallLogRegionObserver extends BaseRegionObserver {

    //被叫引用id
    private static final String REF_ROW_ID = "refrowid" ;
    //通话记录表名
    private static final String CALL_LOG_TABLE_NAME = "ns1:calllogs" ;

    /**
     * Put后处理：当在插入主叫数据之后，又把对应的被叫的记录插入一遍
     */
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        super.postPut(e, put, edit, durability);
        //
        String tableName0 = TableName.valueOf(CALL_LOG_TABLE_NAME).getNameAsString();

        //得到当前的TableName对象
        String tableName1 = e.getEnvironment().getRegion().getRegionInfo().getTable().getNameAsString();
        //判断是否是ns1:calllogs表
        if (!tableName0.equals(tableName1)) {
            //如果不是calllogs表直接返回
            return;
        }

        //得到主叫的rowkey,
        String rowkey = Bytes.toString(put.getRow());

        //如果被叫就放行
        String[] arr = rowkey.split(",");
        if (arr[3].equals("1")) {
            return;
        }
        //hashcode,caller,time,flag,callee,duration
        String caller = arr[1] ;        //主叫
        String callTime = arr[2] ;      //通话时间
        String callee = arr[4] ;        //被叫
        String callDuration = arr[5] ;  //通话时长

        //被叫hashcode
        String hashcode = CallLogUtil.getHashcode(callee,callTime,100);
        //被叫rowkey
        String calleeRowKey = hashcode + "," + callee + "," + callTime + ",1," + caller + "," + callDuration;
        Put newPut = new Put(Bytes.toBytes(calleeRowKey));
        /**
         * 被叫  再插入一个列族f2
         * -----------------------------
         * 	rowkey:						f2:refrowkey
         * 	98,5678,xxxx,1,1234,60   --> 12,1234,xxx,0,5678,60,.
         */
        newPut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes(REF_ROW_ID), Bytes.toBytes(rowkey));
        TableName tn = TableName.valueOf(CALL_LOG_TABLE_NAME);
        Table t = e.getEnvironment().getTable(tn);
        t.put(newPut);
    }

    /**
     * 重写方法，完成被叫查询，返回主叫结果。
     */
    public void postGetOp(ObserverContext<RegionCoprocessorEnvironment> e, Get get, List<Cell> results) throws IOException {
        //获得表名
        String tableName = e.getEnvironment().getRegion().getRegionInfo().getTable().getNameAsString();

        //判断表名是否是ns1:calllogs
        if(!tableName.equals(CALL_LOG_TABLE_NAME)){
            super.preGetOp(e, get, results);
        }
        else{
            //得到rowkey
            String rowkey = Bytes.toString(get.getRow());
            //
            String[] arr = rowkey.split(",");
            //0是主叫
            if(arr[3].equals("0")){
                super.postGetOp(e, get, results);
            }
            //被叫（1）
            else{
                //得到主叫方的rowkey
                String refrowid = Bytes.toString(CellUtil.cloneValue(results.get(0)));
                //
                Table tt = e.getEnvironment().getTable(TableName.valueOf(CALL_LOG_TABLE_NAME));
                Get g = new Get(Bytes.toBytes(refrowid));
                Result r = tt.get(g);
                List<Cell> newList = r.listCells();
                results.clear();
                results.addAll(newList);
            }
        }
    }

    /**
     *
     */
    public boolean postScannerNext(ObserverContext<RegionCoprocessorEnvironment> e, InternalScanner s, List<Result> results, int limit, boolean hasMore) throws IOException {
        boolean b = super.postScannerNext(e, s, results, limit, hasMore);

        //新集合
        List<Result> newList = new ArrayList<Result>();

        //获得表名
        String tableName = e.getEnvironment().getRegion().getRegionInfo().getTable().getNameAsString();

        //判断表名是否是ns1:calllogs
        if (tableName.equals(CALL_LOG_TABLE_NAME)) {
            Table tt = e.getEnvironment().getTable(TableName.valueOf(CALL_LOG_TABLE_NAME));
            for(Result r : results){
                //rowkey
                String rowkey = Bytes.toString(r.getRow());
                String flag = rowkey.split(",")[3] ;
                //主叫
                if(flag.equals("0")){
                    newList.add(r) ;
                }
                //被叫
                else{
                    //取出主叫号码
                    byte[] refrowkey = r.getValue(Bytes.toBytes("f2"),Bytes.toBytes(REF_ROW_ID)) ;
                    Get newGet = new Get(refrowkey);
                    newList.add(tt.get(newGet));
                }
            }
            results.clear();
            results.addAll(newList);
        }
        //否则返回超类的处理结果
        return b ;
    }
}
