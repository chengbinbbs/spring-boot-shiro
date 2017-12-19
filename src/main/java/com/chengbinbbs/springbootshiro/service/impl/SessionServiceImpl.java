package com.chengbinbbs.springbootshiro.service.impl;

import com.chengbinbbs.springbootshiro.dto.SessionOnlineDTO;
import com.chengbinbbs.springbootshiro.enums.Constants;
import com.chengbinbbs.springbootshiro.service.SessionService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author zhangcb
 * @created 2017-12-11 10:14.
 */
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionDAO sessionDAO;

    @Override
    public List<SessionOnlineDTO> getActiveSessions() {
        Collection<Session> sessions =  sessionDAO.getActiveSessions();
        List<SessionOnlineDTO> sessionList = Lists.newArrayList();
        for (Session session : sessions) {
            //获取session登录信息。
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(null == obj){
                continue;
            }
            SessionOnlineDTO dto = new SessionOnlineDTO();
            //确保是 SimplePrincipalCollection对象。
            if(obj instanceof SimplePrincipalCollection) {
                SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
                /**
                 * 获取用户登录的，@link SampleRealm.doGetAuthenticationInfo(...)方法中
                 * return new SimpleAuthenticationInfo(user,user.getPswd(), getName());的user 对象。
                 */
                obj = spc.getPrimaryPrincipal();
                if (null != obj) {
                    dto.setUsername((String) obj);
                }
                //最后一次和系统交互的时间
                dto.setLastAccessTime(session.getLastAccessTime());
                //主机的ip地址
                dto.setHost(session.getHost());
                //session ID
                dto.setSessionId(session.getId().toString());
                //session最后一次与系统交互的时间
                dto.setLastLoginTime(session.getLastAccessTime());
                //回话到期 ttl(ms)
                dto.setTimeout(session.getTimeout());
                //session创建时间
                dto.setStartTime(session.getStartTimestamp());
                //是否踢出
                dto.setSessionStatus(session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY) != null);
                sessionList.add(dto);
            }
        }
        return sessionList;
    }

    public Session readSession(String sessionId){
        return sessionDAO.readSession(sessionId);
    }

    public void updateSession(Session session){
        sessionDAO.update(session);
    }
}
