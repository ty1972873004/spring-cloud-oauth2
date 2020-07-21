package com.hxmec.auth.config;

import com.hxmec.auth.converter.MyUserAuthenticationConverter;
import com.hxmec.auth.properties.AuthClientProperties;
import com.hxmec.auth.service.MyAuthenticationEntryPoint;
import com.hxmec.auth.service.MyTokenServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;

/**
 * 功能描述: 资源服务器配置
 * @author  Trazen
 * @date  2020/7/14 21:37
 */
@Slf4j
@Configuration
@EnableResourceServer
@AllArgsConstructor
@ComponentScan("com.hxmec.auth")
@EnableConfigurationProperties(AuthClientProperties.class)
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final MyAuthenticationEntryPoint baseAuthenticationEntryPoint;

    private final AuthClientProperties authClientProperties;

    private final RestTemplate lbRestTemplate;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // 放行 swagger ui (有整合swagger就放行这些请求吧)
        http.authorizeRequests().antMatchers(
                "/v2/api-docs",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/api/**/v2/api-docs")
                .permitAll();

        // 根据自定义配置url放行
        if (authClientProperties.getIgnoreUrls() != null){
            for(String url: authClientProperties.getIgnoreUrls()){
                http.authorizeRequests().antMatchers(url).permitAll();
            }
        }
        // 其他请求均需要token才能访问
        http.authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        if (authClientProperties.getResourceId() != null) {
            resources.resourceId(authClientProperties.getResourceId());
        }

        // 这里的签名key 保持和认证中心一致
        if (authClientProperties.getSigningKey() == null) {
            log.info("SigningKey is null cant not decode token.......");
        }

        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new MyUserAuthenticationConverter());

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //设置解析jwt的密钥
        converter.setSigningKey(authClientProperties.getSigningKey());
        converter.setVerifier(new MacSigner(authClientProperties.getSigningKey()));

        MyTokenServices tokenServices = new MyTokenServices();

        // 在CustomTokenServices注入三个依赖对象
        //设置token存储策略
        tokenServices.setTokenStore(new JwtTokenStore(converter));
        tokenServices.setJwtAccessTokenConverter(converter);
        tokenServices.setDefaultAccessTokenConverter(accessTokenConverter);
        tokenServices.setRestTemplate(lbRestTemplate);
        resources.tokenServices(tokenServices)
                .authenticationEntryPoint(baseAuthenticationEntryPoint);
    }


}
