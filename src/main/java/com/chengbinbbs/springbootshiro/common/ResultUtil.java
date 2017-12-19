package com.chengbinbbs.springbootshiro.common;


import com.chengbinbbs.springbootshiro.enums.ErrorCode;

/**
 * 生成返回数据格式
 *
 * @author zhangcb
 * @created 2017-08-17 17:58.
 */
public class ResultUtil {
    /**
     * 返回响应成功的结果
     * @return
     */
    public static BaseResult genSuccessResult(){
        BaseResult result = BaseResult.newInstance();
        result.setCode(200);
        result.setMessage("SUCCESS");
        return result;
    }

    /**
     * 返回响应成功的带数据的结果
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> genSuccessResult(T data){
        BaseResult result = BaseResult.newInstance();
        result.setCode(200);
        result.setMessage("SUCCESS");
        result.setData(data);
        return result;
    }

    /**
     * 返回响应失败的结果
     * @param message
     * @return
     */
    public static BaseResult genFailResult(String message){
        BaseResult result = BaseResult.newInstance();
        result.setCode(201);
        result.setMessage(message);
        return result;
    }

    /**
     * 返回响应失败的带数据的结果
     * @param errorCode
     * @return
     */
    public static BaseResult genFailResult(ErrorCode errorCode){
        BaseResult result = BaseResult.newInstance();
        result.setCode(errorCode.getCode());
        result.setMessage(errorCode.getMessgae());
        return result;
    }
}
