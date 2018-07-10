package com.yiban.entity;

import java.io.Serializable;

/**
 * Created by Kuexun on 2018/7/10.
 */
public class Info implements Serializable {
    //{total:2,
    // rows:[
    // {studentId:201624133135,studentName:'少年',department:'软件学院',className:'16软件1班',phone:123456,
    // beginTime:'2017年5月1日',endTime:'2018年6月5日',status:'批准'}
    // ]};
    private static final long serialVersionUID = 9193842223814904244L;
    //编号
    private long id;
    //学号id
    private String studentId;
    //学生姓名
    private String studentName;
    //学生院系信息
    private String department;
    //学生班级信息
    private String className;
    //请假开始时间
    private String beginTime;
    //请假结束时间
    private String endTime;
    //联系方式
    private String phone;
    //请假状态
    private String status;
    //请假节数
    private int num;
    //请假原因
    private String reason;

    public Info() {
    }

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", department='" + department + '\'' +
                ", className='" + className + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", num=" + num +
                ", reason='" + reason + '\'' +
                '}';
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
