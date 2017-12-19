package com.chengbinbbs.springbootshiro.domain;

import java.util.List;

/**
 * @Author: zhangcb
 * @Description:
 * @Date: Created on 2017/12/7.
 */
public class PageModel<T> {

    public List<T> list;
    public Integer count = 0;
    public String msg;
    public Boolean rel;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getRel() {
        return rel;
    }

    public void setRel(Boolean rel) {
        this.rel = rel;
    }
}