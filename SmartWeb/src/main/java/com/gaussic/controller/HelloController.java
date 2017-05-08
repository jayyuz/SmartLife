package com.gaussic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2016/9/24.
 */
@Controller
public class HelloController {
    @RequestMapping(value = "/hello.jsp", method = RequestMethod.GET)
    public String hello(){
        return "index";
    }
}

