package com.yiban.dto;

/**
 * Created by Kuexun on 2018/7/9.
 */
public class IsSuccessResult {
    private int code;
    private String msg;

    public IsSuccessResult() {
    }

    public IsSuccessResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "IsSuccessResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
