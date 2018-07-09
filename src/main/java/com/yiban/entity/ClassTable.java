package com.yiban.entity;

import java.io.Serializable;

/**
 * �༶��ʵ�����
 */
public class ClassTable implements Serializable {

    private static final long serialVersionUID = -2890008160294451071L;
    //�༶ID
    private String classId;
    private String className;
    //�༶��Ӧ����Ա���װ�ID
    private String teacherYibanId;
    private String teacherName;
    //�༶�೤��ID
    private String monitor;
    private String monitorName;
    //�����ε�ID
    private  String deanYiban_id;
    private  String deanName;

    public ClassTable(String classId, String teacherYibanId, String monitor, String deanYiban_id) {
		super();
		this.classId = classId;
		this.teacherYibanId = teacherYibanId;
		this.monitor = monitor;
		this.deanYiban_id=deanYiban_id;
	}

    public ClassTable() {
    }

    @Override
    public String toString() {
        return "ClassTable{" +
                "classId='" + classId + '\'' +
                ", className='" + className + '\'' +
                ", teacherYibanId='" + teacherYibanId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", monitor='" + monitor + '\'' +
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

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
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
