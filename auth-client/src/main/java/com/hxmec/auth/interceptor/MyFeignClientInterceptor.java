package com.hxmec.auth.interceptor;

import feign.RequestTemplate;
import org.springframework.cloud.security.oauth2.client.AccessTokenContextRelay;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 * 功能描述: 扩展OAuth2FeignRequestInterceptor
 *  如果特殊场景比如调度任务调用Feign接口等
 *  可以通过过滤指定header头的方式，防止accessTokenContextRelay.copyToken()报错
 * @author  Trazen
 * @date  2020/7/17 21:17
 */
public class MyFeignClientInterceptor extends OAuth2FeignRequestInterceptor {

    private final OAuth2ClientContext oAuth2ClientContext;
    private final AccessTokenContextRelay accessTokenContextRelay;

    /**
     * Default constructor which uses the provided OAuth2ClientContext and Bearer tokens
     * within Authorization header
     *
     * @param oAuth2ClientContext     provided context
     * @param resource                type of resource to be accessed
     * @param accessTokenContextRelay
     */
    public MyFeignClientInterceptor(OAuth2ClientContext oAuth2ClientContext
            , OAuth2ProtectedResourceDetails resource, AccessTokenContextRelay accessTokenContextRelay) {
        super(oAuth2ClientContext, resource);
        this.oAuth2ClientContext = oAuth2ClientContext;
        this.accessTokenContextRelay = accessTokenContextRelay;
    }


    /**
     * Create a template with the header of provided name and extracted extract
     * 1. 如果使用 非web 请求，header 区别
     * 2. 根据authentication 还原请求token
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        accessTokenContextRelay.copyToken();
        if (oAuth2ClientContext != null
                && oAuth2ClientContext.getAccessToken() != null) {
            super.apply(template);
        }
    }
}
