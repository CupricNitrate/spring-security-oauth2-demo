package com.cupricnitrate.resource.http.req;

import lombok.Data;

/**
 * 登出请求体
 * @author 硝酸铜
 * @date 2021/9/23
 */
@Data
public class LogoutReq {
    private String accessToken;
}
