package life.nujiew.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublishController {
    // 发布页面
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
}
