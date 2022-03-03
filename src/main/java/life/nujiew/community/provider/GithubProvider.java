package life.nujiew.community.provider;

import com.alibaba.fastjson.JSON;
import life.nujiew.community.dto.AccessTokenDTO;
import life.nujiew.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class GithubProvider {
    /**
     * 获取accesstoken
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = getOKHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            // System.out.println(string);
            // access_token=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&scope=user&token_type=bearer
            // 拆分，返回token
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 通过accesstoken获取返回的user信息
     * @param accessToken
     * @return
     */
    public GithubUser getUser(String accessToken){
        OkHttpClient client = getOKHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //System.out.println(string);
            // json --> object
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 解决okhttp总是socketTimeout问题
    * https://www.cnblogs.com/oldboyooxx/p/13024390.html
    * */
    public OkHttpClient getOKHttpClient(){
        return new OkHttpClient()
                .newBuilder()
                .retryOnConnectionFailure(false)
                .connectionPool(new ConnectionPool(5, 10, TimeUnit.SECONDS))
                .build();
    }
}
