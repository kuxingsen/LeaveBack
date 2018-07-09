package com.yiban.dto.nameResult;

/**
 * Created by Kuexun on 2018/7/9.
 */
public class DeanNameResult {
    private int code;
    private String deanName;

    public DeanNameResult() {
    }

    public DeanNameResult(int code, String deanName) {
        this.code = code;
        this.deanName = deanName;
    }

    @Override
    public String toString() {
        return "DeanNameResult{" +
                "code=" + code +
                ", deanName=" + deanName +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDeanName() {
        return deanName;
    }

    public void setDeanName(String deanName) {
        this.deanName = deanName;
    }
}
