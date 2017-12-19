package com.chengbinbbs.springbootshiro.exception;

import com.chengbinbbs.springbootshiro.enums.ErrorCode;

/**
 * @author zhangcb
 * @created 2017-12-11 13:35.
 */
public class BusinessException extends Exception{

    protected int state;

    public BusinessException() {
        super();
        this.state = ErrorCode.FAIL.getCode();
    }

    public BusinessException(String message) {
        super(message);
        this.state = ErrorCode.FAIL.getCode();
    }

    public BusinessException(int state, String message) {
        super(message);
        this.state = state;
    }

    /**
     * Getter method for property <tt>state</tt>.
     *
     * @return property value of state
     */
    public int getState() {
        return state;
    }

    /**
     * @see java.lang.Throwable#fillInStackTrace()
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
