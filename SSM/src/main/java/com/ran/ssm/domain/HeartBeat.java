package com.ran.ssm.domain;

/**
 * Created by Administrator on 2017/4/16.
 */
public class HeartBeat {
    private  String ip ;
    private int flag ;
    private long timestamp ;

    public HeartBeat(String ip, int flag, long timestamp) {
        this.ip = ip;
        this.flag = flag;
        this.timestamp = timestamp;
    }
    public HeartBeat() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
