package com.chengbinbbs.springbootshiro.controller;

import com.chengbinbbs.springbootshiro.common.BaseResult;
import com.chengbinbbs.springbootshiro.common.ResultUtil;
import com.chengbinbbs.springbootshiro.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author zhangcb
 * @created 2017-12-07 15:43.
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String indexPage() {
        return "index";
    }

    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public String mainPage() {
        return "main";
    }


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult login(@RequestParam("username") String username,@RequestParam("password") String password,
                            @RequestParam("rememberMe") Boolean rememberMe) {
        try {
            //1.得到subject
            Subject currentUser = SecurityUtils.getSubject();
            //2.创建用户名、密码的token
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            if(null != rememberMe){
                token.setRememberMe(rememberMe);
            }
            //3.验证登录
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");

            //4.验证是否登录成功
            if (currentUser.isAuthenticated()) {
                logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
                String principal = (String) currentUser.getPrincipal();
                Session session = currentUser.getSession();
                session.setAttribute("username",principal);
                return ResultUtil.genSuccessResult();
            } else {
                token.clear();
            }

        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            return ResultUtil.genFailResult("未知账户!");
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            return ResultUtil.genFailResult("密码不正确!");
        } catch (LockedAccountException lae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            return ResultUtil.genFailResult("账户已锁定!");
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            return ResultUtil.genFailResult("用户名或密码错误次数过多!");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            return ResultUtil.genFailResult("用户名或密码错误!");
        }
        return ResultUtil.genFailResult("用户名或密码错误!");
    }

    @RequestMapping("/logout")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
//        session.removeAttribute("user");
        return "login";
    }
}
