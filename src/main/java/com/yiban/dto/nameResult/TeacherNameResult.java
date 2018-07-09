package com.yiban.dto.nameResult;

/**
 * Created by Kuexun on 2018/7/9.
 */
public class TeacherNameResult {
    private int code;
    private String teacherName;

    public TeacherNameResult() {
    }

    public TeacherNameResult(int code, String teacherName) {
        this.code = code;
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "DeanNameResult{" +
                "code=" + code +
                ", teacherName=" + teacherName +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
