package com.chengbinbbs.springbootshiro.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangcb
 * @created 2017-12-11 10:07.
 */
public class SessionOnlineDTO implements Serializable {

    private String sessionId;
    private String username;
    private String host;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;     //Session创建时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastAccessTime;    //Session最后交互时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
    private long timeout;
    private boolean sessionStatus = Boolean.TRUE;   //session 是否踢出


    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public boolean isSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(boolean sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
}
