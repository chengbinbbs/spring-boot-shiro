package com.chengbinbbs.springbootshiro.util;

import com.chengbinbbs.springbootshiro.enums.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

/**
 * @Author: zhangcb
 * @Description:
 * @Date: Created on 2017/12/9.
 */
public class SessionUtil {

    public static String principal(Session session) {

        PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

        return (String)principalCollection.getPrimaryPrincipal();
    }

    public static boolean isForceLogout(Session session) {
        return session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY) != null;
    }
}
