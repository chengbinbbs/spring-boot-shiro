package com.chengbinbbs.springbootshiro.vo;

import java.io.Serializable;

/**
 * @Author: zhangcb
 * @Description:
 * @Date: Created on 2017/12/7.
 */
public class PageVO implements Serializable{

    private Integer pageIndex = 1;

    private Integer pageSize = 10;


    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
