package com.neeson.example.base.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Create on 2019-09-23
 *
 * @author DaiLe
 */
@Configuration
public class RestTemplateConfiguration {

    @Autowired
    private List<ClientHttpRequestInterceptor> clientHttpRequestInterceptors;

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // 可以复制流，防止 LogRequestInterceptor 中获取 body 之后，后续获取不到数据
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(getClientHttpRequestFactory());
        // Interceptor 的顺序可以由 @order 控制
        restTemplate.setInterceptors(clientHttpRequestInterceptors);
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }

    @Bean
    public SimpleClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory
                = new SimpleClientHttpRequestFactory();
        //Connect timeout
        clientHttpRequestFactory.setConnectTimeout(10_000);

        //Read timeout
        clientHttpRequestFactory.setReadTimeout(10_000);
        return clientHttpRequestFactory;
    }

}
