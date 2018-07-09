package com.yiban.entity;

import java.io.Serializable;

/**
 * 班级表实体对象
 */
public class ClassTable implements Serializable {

    private static final long serialVersionUID = -2890008160294451071L;
    //班级ID
    private String classId;
    private String className;
    //班级对应辅导员的易班ID
    private String teacherYibanId;
    private String teacherName;
    //班级班长的ID
    private String monitorId;
    private String monitorName;
    //班主任的ID
    private  String deanYiban_id;
    private  String deanName;


    public ClassTable() {
    }

    @Override
    public String toString() {
        return "ClassTable{" +
                "classId='" + classId + '\'' +
                ", className='" + className + '\'' +
                ", teacherYibanId='" + teacherYibanId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", monitorId='" + monitorId + '\'' +
                ", monitorName='" + monitorName + '\'' +
                ", deanYiban_id='" + deanYiban_id + '\'' +
                ", deanName='" + deanName + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getTeacherYibanId() {
        return teacherYibanId;
    }

    public void setTeacherYibanId(String teacherYibanId) {
        this.teacherYibanId = teacherYibanId;
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

    public String getDeanYiban_id() {
        return deanYiban_id;
    }

    public void setDeanYiban_id(String deanYiban_id) {
        this.deanYiban_id = deanYiban_id;
    }

    public String getDeanName() {
        return deanName;
    }

    public void setDeanName(String deanName) {
        this.deanName = deanName;
    }
}
