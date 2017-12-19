package com.chengbinbbs.springbootshiro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 分布式session管理
 * 开启spring session支持  实现session共享  
 * maxInactiveIntervalInSeconds: 设置Session失效时间
 * 使用Redis Session之后，原Boot的server.session.timeout属性不再生效
 * @author zhangcb
 * @created 2017-07-19 10:22.
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig {
}
