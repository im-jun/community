package life.nujiew.community.controller;

import life.nujiew.community.dto.AccessTokenDTO;
import life.nujiew.community.dto.GithubUser;
import life.nujiew.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * Github授权登录
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public String Callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){

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
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        // 返回首页
        return "index";
    }
}
