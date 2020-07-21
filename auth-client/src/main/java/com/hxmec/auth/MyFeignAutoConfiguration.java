package com.hxmec.auth;

import com.hxmec.auth.interceptor.MyFeignClientInterceptor;
import feign.Feign;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.security.oauth2.client.AccessTokenContextRelay;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 * 功能描述: fegin配置增强
 * @author  Trazen
 * @date  2020/7/17 21:44
 */
@Configuration
@ConditionalOnClass(Feign.class)
public class MyFeignAutoConfiguration {

	@Bean
	@ConditionalOnProperty("security.oauth2.client.client-id")
	public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext,
                                                            OAuth2ProtectedResourceDetails resource,
                                                            AccessTokenContextRelay accessTokenContextRelay) {
		return new MyFeignClientInterceptor(oAuth2ClientContext, resource, accessTokenContextRelay);
	}

}
