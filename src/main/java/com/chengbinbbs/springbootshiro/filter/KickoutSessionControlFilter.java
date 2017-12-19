package com.chengbinbbs.springbootshiro.filter;

import com.chengbinbbs.springbootshiro.common.ResultUtil;
import com.chengbinbbs.springbootshiro.util.RequestUtil;
import com.chengbinbbs.springbootshiro.util.ResponseUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 1.读取当前登录用户名，获取在缓存中的sessionId队列
 * 2.判断队列的长度，大于最大登录限制的时候，按踢出规则
 * 将之前的sessionId中的session域中存入kickout：true，并更新队列缓存
 * 3.判断当前登录的session域中的kickout如果为true，
 * 想将其做退出登录处理，然后再重定向到踢出登录提示页面
 * @Author: zhangcb
 * @Description:
 * @Date: Created on 2017/12/9.
 */
public class KickoutSessionControlFilter  extends AccessControlFilter{

    private static final Logger logger = LoggerFactory.getLogger(KickoutSessionControlFilter.class);

    private String kickoutUrl = "/login"; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 //默认踢出之前登录的用户
    private int maxSession = 2; //同一个帐号最大会话数 默认1
    private SessionManager                     sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public KickoutSessionControlFilter setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
        return this;
    }

    public KickoutSessionControlFilter setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
        return this;
    }

    public KickoutSessionControlFilter setMaxSession(int maxSession) {
        this.maxSession = maxSession;
        return this;
    }

    public KickoutSessionControlFilter setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        return this;
    }

    public KickoutSessionControlFilter setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
        return this;
    }

    /**
     * 是否允许访问，返回true表示允许
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    /**
     * 表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，返回false表示自己已经处理了（比如重定向到另一个页面）。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }

        Session session = subject.getSession();
        String username = (String) subject.getPrincipal();
        Serializable sessionId = session.getId();

        // 初始化用户的队列放到缓存里
        Deque<Serializable> deque = cache.get(username);
        if (deque == null) {
            deque = new LinkedList<Serializable>();
            cache.put(username, deque);
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if (kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (SessionException e) {//ignore exception
                logger.error(e.getMessage());
            }
        }

        Boolean cickout = (Boolean) session.getAttribute("kickout");
        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (cickout != null && cickout == true) {
            //会话被踢出了
            try {
                //退出登录
                subject.logout();
            } catch (Exception e) { //ignore
                logger.error(e.getMessage());
            }
            saveRequest(request);
            //判断是不是Ajax请求
            if (RequestUtil.isAjax((HttpServletRequest) request)) {
                //输出json串
                ResponseUtil.out((HttpServletResponse) response, ResultUtil.genFailResult("您已经在其他地方登录，请刷新页面重新登录！"));
            } else {
                //重定向
                WebUtils.issueRedirect(request, response, kickoutUrl);
            }
            return false;
        }
        return true;
    }
}
