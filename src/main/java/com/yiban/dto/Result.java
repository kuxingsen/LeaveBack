package com.yiban.dto;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Kuexun on 2018/7/8.
 */
public class Result<T> {
    private int total; //1 --- 成功， 其他---失败
    private List<T> rows = new ArrayList<>();

    public Result() {
    }

    public Result(int total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "Result{" +
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


    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
