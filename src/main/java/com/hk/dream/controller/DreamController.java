package com.hk.dream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 坤
 * 2018/7/19 22:25
 */
@Controller
public class DreamController {
    @RequestMapping("/index")
    public String demo(){
        System.out.println(111111);
        return "6";
    }
}
