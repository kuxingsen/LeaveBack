package com.yiban.dto;

/**
 * 主要用于班级、辅导员、班主任、班长、转专业学生的回显
 * Created by Kuexun on 2018/7/14.
 */
public class NameResult {
    //0表示查找成功，-1表示失败
    private int code;
    //相应的名称
    private String uName;

    public NameResult(int code, String uName) {
        this.code = code;
        this.uName = uName;
    }

    public NameResult() {
    }

    @Override
    public String toString() {
        return "NameResult{" +
                "code='" + code + '\'' +
                ", name='" + uName + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String name) {
        this.uName = name;
    }
}
