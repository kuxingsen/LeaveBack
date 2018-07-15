package com.yiban.entity;

import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = 3210359254553565346L;
    //�װ�ID
    private String yibanId;
    //ѧ��ѧ��
    private String studentId;
    //ѧ������
    private String name;
    //ѧ��Ժϵ��Ϣ
    private String department;
    //ѧ���༶��Ϣ
    private String className;
    //�ж��°༶
    private String judgeIsNewClassId;

    public Student()
    {
    	super();
    }

    public Student(String yibanId, String studentId, String name, String department, String className, String judgeIsNewClassId) {
        this.yibanId = yibanId;
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.className = className;
        this.judgeIsNewClassId = judgeIsNewClassId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "yibanId='" + yibanId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", className='" + className + '\'' +
                ", judgeIsNewClassId='" + judgeIsNewClassId + '\'' +
                '}';
    }

    public String getJudgeIsNewClassId() {
        return judgeIsNewClassId;
    }

    public void setJudgeIsNewClassId(String judgeIsNewClassId) {
        this.judgeIsNewClassId = judgeIsNewClassId;
    }
    public String getYibanId() {
        return yibanId;
    }

    public void setYibanId(String yibanId) {
        this.yibanId = yibanId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
