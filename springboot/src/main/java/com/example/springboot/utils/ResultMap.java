package com.example.springboot.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("resultMap")
public class ResultMap<T> {
    private String msg;
    private Boolean Status;
    private int totalCount;
    private List<T> list = new ArrayList<T>();

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
