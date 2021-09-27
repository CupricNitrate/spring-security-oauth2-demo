package com.cupricnitrate.resource.controller;

import com.cupricnitrate.resource.feign.OauthFeign;
import com.cupricnitrate.resource.http.req.LoginReq;
import com.cupricnitrate.resource.http.req.LogoutReq;
import com.cupricnitrate.resource.http.resp.LoginResp;
import com.cupricnitrate.resource.http.resp.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 硝酸铜
 * @date 2021/9/23
 */
@RestController
@RequestMapping("/auth")
public class AuthorityController {
    @Resource
    private OauthFeign oauthFeign;

    @PostMapping(value = "/login")
    public Result<LoginResp> login(@RequestBody LoginReq req){
        return oauthFeign.login(req);
    }

    @PostMapping(value = "logout")
    public Result<String> logOut(@RequestBody LogoutReq req) {
        return oauthFeign.logout(req);
    }
}
