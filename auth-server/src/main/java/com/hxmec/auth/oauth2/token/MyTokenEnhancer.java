package com.hxmec.auth.oauth2.token;

import com.hxmec.auth.oauth2.model.AuthUserDetail;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: 增强颁发的token的携带信息
 * @author  Trazen
 * @date  2020/7/14 16:07
 */
public class MyTokenEnhancer implements TokenEnhancer {


    /**
     * 客户端模式
     */
    private final static String CLIENT_CREDENTIALS = "client_credentials";

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        //客户端模式不进行增强
        if (CLIENT_CREDENTIALS.equals(authentication.getOAuth2Request().getGrantType())) {
            return accessToken;
        }
        //获取要增强携带的字段
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal();


        final Map<String, Object> additionalInfo = new HashMap<>(3);

        //添加token携带的字段
        additionalInfo.put("id", authUserDetail.getSysUser().getId());
        additionalInfo.put("username", authUserDetail.getSysUser().getUsername());
        additionalInfo.put("email", authUserDetail.getSysUser().getEmail());
        additionalInfo.put("mobile", authUserDetail.getSysUser().getMobile());

        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        token.setAdditionalInformation(additionalInfo);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;

    }
}
