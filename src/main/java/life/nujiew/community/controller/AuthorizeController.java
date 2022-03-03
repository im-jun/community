package life.nujiew.community.controller;

import life.nujiew.community.dto.AccessTokenDTO;
import life.nujiew.community.dto.GithubUser;
import life.nujiew.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

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
        accessTokenDTO.setClient_id("7165422e9053eee9f701");
        accessTokenDTO.setClient_secret("81d18dce91e870c3370e7bf40f533e26d86f79da");
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        // 得到access_token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //System.out.println(accessToken);
        // 通过access_token得到用户信息
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
