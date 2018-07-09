package com.yiban.dto.nameResult;

/**
 * Created by Kuexun on 2018/7/9.
 */
public class MonitorNameResult {
    private int code;
    private String monitorName;

    public MonitorNameResult() {
    }

    public MonitorNameResult(int code, String monitorName) {
        this.code = code;
        this.monitorName = monitorName;
    }

    @Override
    public String toString() {
        return "DeanNameResult{" +
                "code=" + code +
                ", monitorName=" + monitorName +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }
}
