package com.cupricnitrate.authority.exception.handler;

import com.cupricnitrate.authority.exception.translator.AuthWebResponseExceptionTranslator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 硝酸铜
 * @date 2021/9/18
 */
@RestControllerAdvice
public class OauthExceptionHandler {

    private WebResponseExceptionTranslator<OAuth2Exception> providerExceptionHandler = new AuthWebResponseExceptionTranslator();

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<OAuth2Exception> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) throws Exception {
        return providerExceptionHandler.translate(e);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<OAuth2Exception> handleException(Exception e) throws Exception {
        return providerExceptionHandler.translate(e);
    }

    @ExceptionHandler({ClientRegistrationException.class})
    public ResponseEntity<OAuth2Exception> handleClientRegistrationException(Exception e) throws Exception {
        return providerExceptionHandler.translate(new BadClientCredentialsException());
    }

    @ExceptionHandler({OAuth2Exception.class})
    public ResponseEntity<OAuth2Exception> handleException(OAuth2Exception e) throws Exception {
        return providerExceptionHandler.translate(e);
    }
}
