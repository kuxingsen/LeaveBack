package com.yiban.dto;

/**
 * Created by Kuexun on 2018/7/14.
 */
public class NameResult {
    private int code;
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
