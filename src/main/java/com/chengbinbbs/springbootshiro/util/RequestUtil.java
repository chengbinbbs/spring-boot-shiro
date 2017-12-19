package com.chengbinbbs.springbootshiro.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zhangcb
 * @Description:
 * @Date: Created on 2017/12/9.
 */
public class RequestUtil {

    private RequestUtil() {
    }

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
}
