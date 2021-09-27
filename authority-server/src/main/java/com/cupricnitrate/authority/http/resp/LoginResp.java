package com.cupricnitrate.authority.http.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 登陆返回体
 * @author 硝酸铜
 * @date 2021/9/23
 */
@Data
@Accessors(chain = true)
public class LoginResp {

    private String accessToken;

    private String tokenType;

    private String refreshToken;

    private Integer expiresIn;

    private Set<String> scope;

    private String jti;

}
