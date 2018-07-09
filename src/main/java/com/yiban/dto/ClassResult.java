package com.yiban.dto;

/**
 *
 * Created by Kuexun on 2018/7/8.
 */
public class ClassResult {
    private int code; //1 --- 成功， 其他---失败
    private String classId;
    private String className;

    private String deanId;
    private String deanName;

    private String monitorId;
    private String monitorName;

    private String teacherId;
    private String teacherName;

    @Override
    public String toString() {
        return "ClassResult{" +
                "code=" + code +
                ", deanId='" + deanId + '\'' +
                ", deanName='" + deanName + '\'' +
                ", monitorId='" + monitorId + '\'' +
                ", monitorName='" + monitorName + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }

    public ClassResult() {
    }

    public ClassResult(int code, String deanId, String deanName, String monitorId, String monitorName, String teacherId, String teacherName) {
        this.code = code;
        this.deanId = deanId;
        this.deanName = deanName;
        this.monitorId = monitorId;
        this.monitorName = monitorName;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDeanId() {
        return deanId;
    }

    public void setDeanId(String deanId) {
        this.deanId = deanId;
    }

    public String getDeanName() {
        return deanName;
    }

    public void setDeanName(String deanName) {
        this.deanName = deanName;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
