package com.chengbinbbs.springbootshiro.common;

import java.io.Serializable;

/**
 * 封装返回数据格式
 *
 * @author zhangcb
 * @created 2017-08-17 17:53.
 */
public class BaseResult<T> implements Serializable {

    private Integer code;       //返回状态码

    private String message;     //返回状态说明

    private T data;             //返回数据

    public static BaseResult newInstance(){
        return new BaseResult();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
