package com.chengbinbbs.springbootshiro.redis;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 此时的类将实现SessionDAO的改写
 * @author zhangcb
 * @created 2017-12-07 17:44.
 */
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {

    private Log log = LogFactory.getLog(RedisSessionDAO.class);

    @Autowired
    private RedisUtils redisUtils;

    private RedisCacheManager cacheManager;

    @Override
    public RedisCacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(RedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    protected Serializable doCreate(Session session) { // 创建Session，返回session id
        log.info("*** doCreate : " + session);
        Serializable sessionId = super.doCreate(session); // 创建sessionid
        // 将当前创建好的Session的数据保存在Redis数据库里面
        redisUtils.set(this.getKey(sessionId.toString()), session);
        return sessionId;
    }
    @Override
    protected Session doReadSession(Serializable sessionId) { // 根据session
        // id读取session数据
        log.info("*** doReadSession : " + sessionId);
        Object obj = redisUtils.get(this.getKey(sessionId.toString())); // 读取Session数据
        if (obj == null) { // 现在没有读取到session数据，通过Redis读取
            return null;
        }
        return (Session)obj;
    }
    @Override
    protected void doUpdate(Session session) { // 实现Session更新，每次操作都要更新
        log.info("*** doUpdate : " + session);
        super.doUpdate(session);
        if (session != null) {
            redisUtils.set(this.getKey(session.getId().toString()),session);
        }
    }
    @Override
    protected void doDelete(Session session) { // session的删除处理
        log.info("*** doDelete : " + session);
        super.doDelete(session);
        redisUtils.remove(this.getKey(session.getId().toString()));
    }

    /**
     * EnterpriseCacheSessionDAO没有getActiveSessions方法
     * 这里重写getActiveSessions方法自己实现
     * @return
     */
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet();
        Set<String> keys = redisUtils.keys(this.getKey("*"));
        if (keys != null && keys.size() > 0) {
            Iterator i$ = keys.iterator();

            while(i$.hasNext()) {
                String sessionKey = (String)i$.next();
                Session s = (Session) redisUtils.get(sessionKey);
                sessions.add(s);
            }
        }

        return sessions;
    }

    public Session readSession(Serializable sessionId) { // 根据session
        return this.doReadSession(sessionId);
    }

    public void update(Session session) { // 根据session
        this.doUpdate(session);
    }

    private String getKey(String key) {
        return key.toString();
    }
}
