package com.ran.ssm.domain;


/**
 */
public class CallLogRange {
    private String startPoint ;
    private String endPoint ;

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String toString() {
        return startPoint + " - " + endPoint ;
    }
}
