package com.ran.ssm.domain;

/**
 * 通话记录统计结果bean类
 */
public class CallLogStat {
    private String yearMonth;
    private int count;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CallLogStat() {
    }

    public CallLogStat(String yearMonth, int count) {
        this.yearMonth = yearMonth;
        this.count = count;
    }
}
