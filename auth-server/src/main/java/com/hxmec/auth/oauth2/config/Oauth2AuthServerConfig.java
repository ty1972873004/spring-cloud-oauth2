package com.hxmec.auth.oauth2.config;

import com.hxmec.auth.oauth2.service.MyClientDetailsService;
import com.hxmec.auth.oauth2.service.MyUserDetailsService;
import com.hxmec.auth.oauth2.token.MyTokenEnhancer;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * 功能描述: oauth2 认证服务器配置
 * @author  Trazen
 * @date  2020/7/14 16:11
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class Oauth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final DataSource dataSource;

    private final MyUserDetailsService myUserDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        MyClientDetailsService clientDetailsService = new MyClientDetailsService(dataSource);
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 将增强的token设置到增强链中
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer(), jwtAccessTokenConverter()));

        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                // //刷新token的请求会用用到
                .userDetailsService(myUserDetailsService)
                .tokenEnhancer(enhancerChain);
    }


    /**
     * 更改存储token的策略，默认是内存策略,修改为jwt
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        //基于token认证
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jat = new JwtAccessTokenConverter();
        // jwt使用这个key来签名，验证token的服务也使用这个key来验签
        jat.setSigningKey("hxmec");
        return jat;
    }


    /**
     * 添加自定义token增强器实现颁发额外信息的token,因为默认颁发token的字段只有username和ROLE
     * @return
     */
    @Bean
    public TokenEnhancer customTokenEnhancer() {
        //自定义实现
        return new MyTokenEnhancer();
    }


}
