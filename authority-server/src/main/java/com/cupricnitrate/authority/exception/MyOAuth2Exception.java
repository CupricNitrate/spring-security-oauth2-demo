package com.cupricnitrate.authority.exception;

import com.cupricnitrate.authority.exception.serializer.MyOAuth2ExceptionJackson2Deserializer;
import com.cupricnitrate.authority.exception.serializer.MyOauthExceptionJackson2Serializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author 硝酸铜
 * @date 2021/9/17
 */
@JsonSerialize(using = MyOauthExceptionJackson2Serializer.class)
@JsonDeserialize(using = MyOAuth2ExceptionJackson2Deserializer.class)
public class MyOAuth2Exception extends OAuth2Exception {
    public MyOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public MyOAuth2Exception(String msg) {
        super(msg);
    }
}
