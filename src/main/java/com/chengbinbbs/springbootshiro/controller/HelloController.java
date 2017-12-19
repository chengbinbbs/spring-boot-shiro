package com.chengbinbbs.springbootshiro.controller;

import com.chengbinbbs.springbootshiro.common.BaseResult;
import com.chengbinbbs.springbootshiro.common.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangcb
 * @created 2017-11-29 14:05.
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public BaseResult hello(){
        return ResultUtil.genSuccessResult();
    }
}
