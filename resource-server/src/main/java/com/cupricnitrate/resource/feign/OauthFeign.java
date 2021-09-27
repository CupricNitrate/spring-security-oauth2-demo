package com.cupricnitrate.resource.feign;

import com.cupricnitrate.resource.config.FeignConfig;
import com.cupricnitrate.resource.http.req.LoginReq;
import com.cupricnitrate.resource.http.req.LogoutReq;
import com.cupricnitrate.resource.http.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author 硝酸铜
 * @date 2021/9/23
 */
@FeignClient(
        value = "authorization-server",
        path = "/authorization-server",
        configuration = FeignConfig.class
)
public interface OauthFeign {

    @PostMapping(value = "/oauth/login")
    <T> Result<T> login(@RequestBody LoginReq req);

    @PostMapping(value = "/oauth/logout")
    <T> Result<T> logout(@RequestBody LogoutReq req);
}
