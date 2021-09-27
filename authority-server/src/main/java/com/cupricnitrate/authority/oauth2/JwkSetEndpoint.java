package com.cupricnitrate.authority.oauth2;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * 为了和 Spring Security 5.1 以上版本的 Resource Server 兼容
 * 我们需要让 Spring Security OAuth2 支持 JWK
 * 这个类是为了暴露 JWK 的接入点
 * `@FrameworkEndpoint` 注解表示其为框架级的接入点，这里可以理解成一个`@RestController`
 *
 * @author 硝酸铜
 * @date 2021/9/23
 */
@FrameworkEndpoint
class JwkSetEndpoint {

    @Resource
    private KeyPair keyPair;

    @GetMapping("/.well-known/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
