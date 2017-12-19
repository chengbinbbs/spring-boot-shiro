package com.chengbinbbs.springbootshiro.exception;

import com.chengbinbbs.springbootshiro.common.BaseResult;
import com.chengbinbbs.springbootshiro.common.ResultUtil;
import com.chengbinbbs.springbootshiro.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.UnexpectedTypeException;
import javax.validation.ValidationException;

/**
 * 全局异常处理器
 *
 * @author zhangcb
 * @created 2017-08-17 18:21.
 */
@ControllerAdvice
public class GlobarExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobarExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResult globarExceptionHandler(Exception e){
        return ResultUtil.genFailResult("服务器内部异常!");
    }


}
