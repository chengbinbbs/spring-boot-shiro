package com.chengbinbbs.springbootshiro.config;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 配置Druid过滤器
 *
 * @author zhangcb
 * @created 2017-06-29 11:10.
 */
@WebFilter(filterName = "druidWebStatFilter",urlPatterns = "/",initParams = {
        @WebInitParam(name="exclusions",value=".js,.gif,.jpg,.bmp,.png,.css,.ico,/druid/*")
// 忽略资源
})
public class DruidStatFilter extends WebStatFilter {
}
