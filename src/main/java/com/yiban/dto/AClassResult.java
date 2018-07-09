package com.yiban.dto;

/**
 * Created by Kuexun on 2018/7/9.
 */
public class AClassResult {
    //{code:0,
    private int code;
    // classId: classId,className:"班级名称",
    // deanId:'123456',deanName:'美少女',
    // teacherId:'123321',teacherName:'最漂亮美少女',
    // monitorId:'201624133135',monitorName:'Af'}

    private String classId;
    private String className;
    //班级对应辅导员的易班ID
    private String teacherId;
    private String teacherName;
    //班级班长的ID
    private String monitorId;
    private String monitorName;
    //班主任的ID
    private  String deanId;
    private  String deanName;

    public AClassResult() {
    }

    public AClassResult(int code, String classId, String className, String teacherId, String teacherName, String monitorId, String monitorName, String deanId, String deanName) {
        this.code = code;
        this.classId = classId;
        this.className = className;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.monitorId = monitorId;
        this.monitorName = monitorName;
        this.deanId = deanId;
        this.deanName = deanName;
    }

    @Override
    public String toString() {
        return "AClassResult{" +
                "code=" + code +
                ", classId='" + classId + '\'' +
                ", className='" + className + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", monitorId='" + monitorId + '\'' +
                ", monitorName='" + monitorName + '\'' +
                ", deanId='" + deanId + '\'' +
                ", deanName='" + deanName + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
}
