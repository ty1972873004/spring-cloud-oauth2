package com.hxmec.auth.service;

import lombok.Data;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;
/**
 * 功能描述: 
 * @author  Trazen
 * @date  2020/7/14 9:24
 */
@Data
public class MyTokenServices extends RemoteTokenServices {

    private TokenStore tokenStore;

    private DefaultAccessTokenConverter defaultAccessTokenConverter;

    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        //从token中解析得到authentication
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);

        Map<String, ?> map = jwtAccessTokenConverter.convertAccessToken(readAccessToken(accessToken), oAuth2Authentication);
        //根据重新设置默认转换器再去获取authentication (稍后在资源服务器配置类注入CustomUserAuthenticationConverter)
        return defaultAccessTokenConverter.extractAuthentication(map);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        return tokenStore.readAccessToken(accessToken);
    }
}
