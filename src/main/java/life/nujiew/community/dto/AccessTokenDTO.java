package life.nujiew.community.dto;

import lombok.Data;

/**
 * 封装Github授权登录第二步的请求所需的参数
 * https://github.com/login/oauth/access_token
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
