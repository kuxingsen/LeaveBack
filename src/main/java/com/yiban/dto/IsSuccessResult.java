package com.yiban.dto;

/**
 * 用于增加、修改、删除班级和导入或导出时的返回，表示成功或失败
 * Created by Kuexun on 2018/7/9.
 */
public class IsSuccessResult {
    //0表示查找成功，-1表示失败
    private int code;
    //成功或失败返回的信息（前端基本不用它）
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
