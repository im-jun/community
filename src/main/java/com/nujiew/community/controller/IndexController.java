package com.nujiew.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

    // 主页
    @GetMapping("/")
    public String Index(){
        return "index";
    }
}
