package life.nujiew.community.controller;

import life.nujiew.community.dto.PaginationDTO;
import life.nujiew.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    // 主页
    @GetMapping("/")
    public String Index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size){
        // 获取帖子列表
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination", pagination);

        return "index";
    }


}
