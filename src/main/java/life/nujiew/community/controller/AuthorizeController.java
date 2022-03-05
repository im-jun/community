package life.nujiew.community.controller;

import life.nujiew.community.dto.AccessTokenDTO;
import life.nujiew.community.dto.GithubUser;
import life.nujiew.community.mapper.UserMapper;
import life.nujiew.community.model.User;
import life.nujiew.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Github授权登录相关的处理逻辑
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    // 注入Github授权登录所需的配置
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    /**
     * Github授权登录
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public String Callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        // 得到access_token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //System.out.println(accessToken);
        // 通过access_token得到用户信息
        GithubUser githubUser = githubProvider.getUser(accessToken);

        // 判断登录状态
        if (githubUser != null) {
            // 构造user，将用户信息持久化到数据库
            User user = new User();
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);

            // 登录成功，写cookie和session
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } else {
            // 登录失败
            return "redirect:/";
        }
    }
}
