package com.chengbinbbs.springbootshiro.filter;

import com.chengbinbbs.springbootshiro.enums.Constants;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Author: zhangcb
 * @Description:
 * @Date: Created on 2017/12/9.
 */
public class ForceLogoutFilter extends AccessControlFilter {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private Cache<Object, Object> cache;

    public ForceLogoutFilter setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
        return this;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Session session = getSubject(request, response).getSession(false);
        if(session == null) {
            return true;
        }
        Object obj = cache.get(session.getId());
        if(obj == null) {
            return true;
        }
        session = (Session)obj;
        return session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY) == null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        try {
            Subject subject = getSubject(request, response);
            Session session = subject.getSession(false);
            if(null != session){
                cache.remove(session.getId().toString());
            }
            subject.logout();//强制退出
        } catch (Exception e) {/*ignore exception*/}

        String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";
        WebUtils.issueRedirect(request, response, loginUrl);
        return false;
    }
}
