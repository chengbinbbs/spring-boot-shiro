package com.chengbinbbs.springbootshiro.enums;

/**
 * @author zhangcb
 * @created on 2017/8/17.
 */
public enum ErrorCode {
    SUCCESS(200,"请求成功"),//成功
    FAIL(400,"系统异常"),//失败
    UNAUTHORIZED(401,"未认证"),//未认证（签名错误）
    NOT_FOUND(404,"接口不存在"),//接口不存在
    INTERNAL_SERVER_ERROR(500,"服务器内部错误"),//服务器内部错误

    ILLEGAL_PARAM(1001,"参数异常!"),
    USER_UN_LOGIN(1002,"用户未登陆!");

    private Integer code;

    private String messgae;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessgae() {
        return messgae;
    }

    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }

    ErrorCode(Integer code, String messgae) {
        this.code = code;
        this.messgae = messgae;
    }
}
