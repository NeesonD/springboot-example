package com.neeson.example.auth;

import com.neeson.example.auth.api.QQApi;
import com.neeson.example.auth.api.WeChatApi;
import com.neeson.example.auth.api.WeiboApi;
import com.neeson.example.auth.service.CustomOAuthService;
import com.neeson.example.properties.QQLoginProperties;
import com.neeson.example.properties.WeChatLoginProperties;
import com.neeson.example.properties.WeiboLoginProperties;
import org.scribe.builder.ServiceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/31
 * Time: 11:15
 * Description:注册第三方服务bean
 */
@Configuration
public class OAuthConfig {

    private static final String CALLBACK_URL = "http://www.qisir.cn/example/oauth/%s/callback";


    @Autowired
    private WeChatLoginProperties weChatLoginProperties;
    @Autowired
    private QQLoginProperties qqLoginProperties;
    @Autowired
    private WeiboLoginProperties weiboLoginProperties;


    @Bean
    public CustomOAuthService getWechatOAuthService() {
        return (CustomOAuthService) new ServiceBuilder()
                .provider(WeChatApi.class)
                .apiKey(weChatLoginProperties.getAppKey())
                .apiSecret(weChatLoginProperties.getAppSecret())
                .callback(String.format(CALLBACK_URL, OAuthType.WECHAT))
                .build();
    }

    @Bean
    public CustomOAuthService getSinaOAuthService(){
        return (CustomOAuthService) new ServiceBuilder()
                .provider(WeiboApi.class)
                .apiKey(weiboLoginProperties.getAppKey())
                .apiSecret(weiboLoginProperties.getAppSecret())
                .callback(String.format(CALLBACK_URL, OAuthType.WEIBO))
                .build();
    }


    @Bean
    public CustomOAuthService getQQOAuthService(){
        return (CustomOAuthService) new ServiceBuilder()
                .provider(QQApi.class)
                .apiKey(qqLoginProperties.getAppKey())
                .apiSecret(qqLoginProperties.getAppSecret())
                .callback(String.format(CALLBACK_URL, OAuthType.QQ))
                .build();
    }



}
