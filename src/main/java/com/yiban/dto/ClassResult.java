package com.yiban.dto;

import com.yiban.entity.ClassTable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Kuexun on 2018/7/8.
 */
public class ClassResult {
    private int total; //1 --- 成功， 其他---失败
    private List<ClassTable> rows = new ArrayList<>();

    public ClassResult() {
    }

    public ClassResult(int total, List<ClassTable> rows) {
        this.total = total;
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "ClassResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    public List<ClassTable> getRows() {
        return rows;
    }

    public void setRows(List<ClassTable> rows) {
        this.rows = rows;
    }
}
