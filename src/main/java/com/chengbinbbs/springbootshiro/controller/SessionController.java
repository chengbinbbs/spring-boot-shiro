package com.chengbinbbs.springbootshiro.controller;

import com.alibaba.fastjson.JSON;
import com.chengbinbbs.springbootshiro.domain.PageModel;
import com.chengbinbbs.springbootshiro.dto.SessionOnlineDTO;
import com.chengbinbbs.springbootshiro.enums.Constants;
import com.chengbinbbs.springbootshiro.service.SessionService;
import com.chengbinbbs.springbootshiro.util.SessionUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @Author: zhangcb
 * @Description:
 * @Date: Created on 2017/12/7.
 */
@RequiresPermissions("session:*")
@RequestMapping("/session")
@Controller
public class SessionController {

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String listView(){
        return "session/list";
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public String list() {
        PageModel pageModel = new PageModel();
        List<SessionOnlineDTO> sessionList = sessionService.getActiveSessions();
        pageModel.setList(sessionList);
        pageModel.setCount(sessionList.size());
        pageModel.setMsg("ok");
        pageModel.setRel(true);
        return JSON.toJSONString(pageModel);
    }

    @RequestMapping("/forceOut/{sessionId}")
    @ResponseBody
    public String forceLogout(
            @PathVariable("sessionId") String sessionId) {
        int result = 1;
        try {
            Session session = sessionService.readSession(sessionId);
            if(session != null) {
                session.setAttribute(Constants.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);
                sessionService.updateSession(session);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        return result + "";
    }

}
