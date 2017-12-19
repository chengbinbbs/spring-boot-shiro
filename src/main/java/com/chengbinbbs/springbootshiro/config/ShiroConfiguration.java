package com.chengbinbbs.springbootshiro.config;

import com.chengbinbbs.springbootshiro.filter.ForceLogoutFilter;
import com.chengbinbbs.springbootshiro.filter.KickoutSessionControlFilter;
import com.chengbinbbs.springbootshiro.redis.RedisCacheManager;
import com.chengbinbbs.springbootshiro.redis.RedisSessionDAO;
import com.chengbinbbs.springbootshiro.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import com.chengbinbbs.springbootshiro.shiro.realm.UserRealm;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangcb
 * @created 2017-12-06 15:48.
 */
@Configuration
public class ShiroConfiguration {

   /**
     * /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
         Filter Chain定义说明
         1、一个URL可以配置多个Filter，使用逗号分隔
         2、当设置多个过滤器时，全部验证通过，才视为通过
         3、部分过滤器可指定参数，如perms，roles
     */
    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager")SecurityManager securityManager,
                                              @Autowired KickoutSessionControlFilter kickoutSessionControlFilter,@Autowired ForceLogoutFilter forceLogoutFilter){
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        //shiroFilterFactoryBean.setSuccessUrl("/index");

        //过滤器
        Map<String, Filter> filters = new HashMap<String, Filter>();
        //filters.put("authc", this.getLoginFilter());
        filters.put("logout", this.getLogoutFilter());
        filters.put("kickout", kickoutSessionControlFilter);
        filters.put("forceLogout", forceLogoutFilter);

        shiroFilterFactoryBean.setFilters(filters);
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        // 从数据库获取
//        List<Permission> list = permissionService.selectAll();
//        for (Permission permission : list) {
//            filterChainDefinitionMap.put(permission.getUrl(),
//                    permission.getPermission());
//        }
        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/datas/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        //配置记住我或认证通过可以访问的地址
        filterChainDefinitionMap.put("/", "authc");
        filterChainDefinitionMap.put("/index", "authc");
        filterChainDefinitionMap.put("/**", "forceLogout,authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    //配置自定义的权限登录器
    @Bean(name="userRealm")
    public UserRealm authRealm(@Qualifier("credentialsMatcher")CredentialsMatcher credentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        userRealm.setCachingEnabled(false);
        userRealm.setAuthenticationCachingEnabled(false);//禁用认证缓存
        userRealm.setAuthenticationCacheName("authenticationCache");
        userRealm.setAuthorizationCachingEnabled(false);
        userRealm.setAuthorizationCacheName("authorizationCache");
        return userRealm;
    }

    //配置自定义的密码比较器
    @Bean(name="credentialsMatcher")
    public CredentialsMatcher credentialsMatcher(@Autowired RedisCacheManager cacheManager) {
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
        credentialsMatcher.setHashAlgorithmName("md5");     //散列算法:这里使用MD5算法
        credentialsMatcher.setHashIterations(2);            //散列的次数，比如散列两次，相当于 md5(md5(""))
        //storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean
    public QuartzSessionValidationScheduler getQuartzSessionValidationScheduler() {
        QuartzSessionValidationScheduler sessionValidationScheduler = new QuartzSessionValidationScheduler();
        sessionValidationScheduler.setSessionValidationInterval(100000);
        return sessionValidationScheduler;
    }

    /**
     * 配置EhCacheManager，shiro会缓存用户权限和角色信息
     * @return
     */
//    @Bean
//    public EhCacheManager cacheManger(){
//        EhCacheManager cacheManger = new EhCacheManager();
//        cacheManger.setCacheManagerConfigFile("classpath:ehcache.xml");
//        return cacheManger;
//    }

    @Bean
    public SessionIdGenerator getSessionIdGenerator() { // 3
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public SessionDAO getSessionDAO(@Autowired SessionIdGenerator sessionIdGenerator, @Autowired RedisCacheManager cacheManager) { // 4
        RedisSessionDAO sessionDAO = new RedisSessionDAO();	// 使用Redis进行Session管理
        //sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(sessionIdGenerator);
        sessionDAO.setCacheManager(cacheManager);
        return sessionDAO;
    }

    @Bean
    public RememberMeManager getRememberManager() throws Base64DecodingException { // 5
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        SimpleCookie cookie = new SimpleCookie("MLDNJAVA-RememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        rememberMeManager.setCookie(cookie);
        //cookie加密的密钥
        rememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return rememberMeManager;
    }

    @Bean
    public DefaultWebSessionManager getSessionManager(@Autowired SessionDAO sessionDAO,
                                                      @Autowired QuartzSessionValidationScheduler sessionValidationScheduler) { // 6
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1000000);
        sessionManager.setDeleteInvalidSessions(true);
//        sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
//        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionDAO(sessionDAO);
        SimpleCookie sessionIdCookie = new SimpleCookie("mldn-session-id");
        sessionIdCookie.setHttpOnly(true);
        sessionIdCookie.setMaxAge(-1);
        sessionManager.setSessionIdCookie(sessionIdCookie);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    //配置核心安全事务管理器
    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm, @Autowired RedisCacheManager cacheManager,
                                           @Autowired DefaultWebSessionManager sessionManager, @Autowired RememberMeManager rememberMeManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setCacheManager(cacheManager);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    /**
     * 配置LifecycleBeanPostProcessor生命周期管理器，会自动调用配置在IOC容器中的shiro bean的生命周期
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启动IOC容器中使用shiro的注解，但是必须配置在LifecycleBeanPostProcessor中才能使用
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

    public FormAuthenticationFilter getLoginFilter() { // 在ShiroFilterFactoryBean中使用
        FormAuthenticationFilter filter = new FormAuthenticationFilter();
        filter.setUsernameParam("mid");
        filter.setPasswordParam("password");
        filter.setRememberMeParam("rememberMe");
        filter.setLoginUrl("/loginPage");	// 登录提交页面
        filter.setFailureKeyAttribute("error");
        return filter;
    }

    public LogoutFilter getLogoutFilter() { // 在ShiroFilterFactoryBean中使用
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/");	// 首页路径，登录注销后回到的页面
        return logoutFilter;
    }

    /**
     * 限制同一账号登录同时登录人数控制
     * @return
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter(@Autowired DefaultWebSessionManager sessionManager, @Autowired RedisCacheManager cacheManager){
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        //这里我们还是用之前shiro使用的redisManager()实现的cacheManager()缓存管理
        //也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
        kickoutSessionControlFilter.setCacheManager(cacheManager);
        //用于根据会话ID，获取会话进行踢出操作的；
        kickoutSessionControlFilter.setSessionManager(sessionManager);
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
        kickoutSessionControlFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        kickoutSessionControlFilter.setMaxSession(2);
        //被踢出后重定向到的地址；
        kickoutSessionControlFilter.setKickoutUrl("/login");
        return kickoutSessionControlFilter;
    }

    @Bean
    public ForceLogoutFilter forceLogoutFilter(@Autowired RedisCacheManager cacheManager){
        ForceLogoutFilter forceLogoutFilter = new ForceLogoutFilter();
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        //这里我们还是用之前shiro使用的redisManager()实现的cacheManager()缓存管理
        //也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
        forceLogoutFilter.setCacheManager(cacheManager);
        return forceLogoutFilter;
    }

}
