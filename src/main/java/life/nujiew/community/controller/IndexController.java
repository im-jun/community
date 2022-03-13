package life.nujiew.community.controller;

import life.nujiew.community.dto.PaginationDTO;
import life.nujiew.community.dto.QuestionDTO;
import life.nujiew.community.mapper.QuestionMapper;
import life.nujiew.community.mapper.UserMapper;
import life.nujiew.community.model.Question;
import life.nujiew.community.model.User;
import life.nujiew.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    // 主页
    @GetMapping("/")
    public String Index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size){
        // 通过cookie判断登录状态
        // 访问首页时，循环查看所有cookie，找到对应name的cookie，获取该cookie的value
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    // 通过token查找数据库中是否存在对应记录的用户
                    User user = userMapper.findByToken(token);
                    // 有的话将其放入session，前端就能判断是否对user信息进行展示
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        // 获取帖子列表
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination", pagination);

        return "index";
    }


}
