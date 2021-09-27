package com.cupricnitrate.authority.http.req;

import lombok.Data;


/**
 * 登陆请求体
 * @author 硝酸铜
 * @date 2021/9/23
 */
@Data
public class LoginReq {
    private String username;

    private String password;
}
