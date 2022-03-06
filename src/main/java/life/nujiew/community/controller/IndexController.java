package life.nujiew.community.controller;

import life.nujiew.community.mapper.UserMapper;
import life.nujiew.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    // 主页
    @GetMapping("/")
    public String Index(HttpServletRequest request){
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
        return "index";
    }


}
