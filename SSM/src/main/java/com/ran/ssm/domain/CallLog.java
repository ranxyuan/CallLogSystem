package com.ran.ssm.domain;

import com.ran.ssm.util.CallLogUtil;

/**
 * CallLog
 */
public class CallLog {
    private String caller ;//主叫
    private String callerName;//主叫名称
    private String callee ;//被叫
    private String calleeName;
    private String callTime ;//通话时间
    private String callDuration ;//通话时长
    //是否是主叫
    private boolean flag ;

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public String getCalleeName() {
        return calleeName;
    }

    public void setCalleeName(String calleeName) {
        this.calleeName = calleeName;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public String getCallTime() {
        if(callTime != null){
            return CallLogUtil.formatDate(callTime);
        }
        return null ;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    @Override
    public String toString() {
        return "CallLog{" +
                "caller='" + caller + '\'' +
                ", callee='" + callee + '\'' +
                ", callTime='" + callTime + '\'' +
                ", callDuration='" + callDuration + '\'' +
                ", flag=" + flag +
                '}';
    }
}