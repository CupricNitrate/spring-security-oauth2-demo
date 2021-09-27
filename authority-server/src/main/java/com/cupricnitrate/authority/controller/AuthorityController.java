package com.cupricnitrate.authority.controller;

import com.cupricnitrate.authority.http.req.LoginReq;
import com.cupricnitrate.authority.http.req.LogoutReq;
import com.cupricnitrate.authority.http.resp.LoginResp;
import com.cupricnitrate.authority.http.resp.Result;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 硝酸铜
 * @date 2021/9/23
 */
@RestController
@RequestMapping("/oauth")
public class AuthorityController {

    @Resource
    private TokenEndpoint tokenEndpoint;

    @Resource
    @Lazy
    private TokenStore tokenStore;

    @PostMapping(value = "/login")
    public Result<LoginResp> login(@RequestBody LoginReq req) throws HttpRequestMethodNotSupportedException {
        //创建客户端信息,客户端信息可以写死进行处理，因为Oauth2密码模式，客户端双信息必须存在，所以伪装一个
        //如果不想这么用，需要重写比较多的代码
        //这里设定，调用这个接口的都是资源服务
        User clientUser = new User("resource-server", "12345678", new ArrayList<>());
        //生成已经认证的client
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(clientUser, null, new ArrayList<>());
        //封装成一个UserPassword方式的参数体
        Map<String, String> map = new HashMap<>();
        map.put("username", req.getUsername());
        map.put("password", req.getPassword());
        //授权模式为：密码模式
        map.put("grant_type", "password");

        //调用自带的获取token方法。
        OAuth2AccessToken resultToken = tokenEndpoint.postAccessToken(token, map).getBody();
        LoginResp resp = new LoginResp();
        resp.setAccessToken(resultToken.getValue())
                .setTokenType(resultToken.getTokenType())
                .setRefreshToken(resultToken.getRefreshToken().getValue())
                .setExpiresIn(resultToken.getExpiresIn())
                .setScope(resultToken.getScope())
                .setJti((String) resultToken.getAdditionalInformation().get("jti"));
        return Result.success(resp);
    }

    @PostMapping(value = "/logout")
    public Result<String> logOut(@RequestBody LogoutReq req) {
        Assert.notNull(req.getAccessToken(), "传参错误，入参accessToken为空");
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(req.getAccessToken());
        if (oAuth2AccessToken != null) {
            OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
            //从tokenStore中移除token
            tokenStore.removeAccessToken(oAuth2AccessToken);
            tokenStore.removeRefreshToken(oAuth2RefreshToken);
            tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
            return Result.success("登出成功");
        } else {
            return Result.error("token已失效，请勿重复登出", 500);
        }
    }
}
