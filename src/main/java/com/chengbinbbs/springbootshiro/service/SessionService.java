package com.chengbinbbs.springbootshiro.service;

import com.chengbinbbs.springbootshiro.dto.SessionOnlineDTO;
import org.apache.shiro.session.Session;

import java.util.List;

/**
 * @author zhangcb
 * @created on 2017/12/11.
 */
public interface SessionService {

    List<SessionOnlineDTO> getActiveSessions();

    Session readSession(String sessionId);

    void updateSession(Session session);
}
