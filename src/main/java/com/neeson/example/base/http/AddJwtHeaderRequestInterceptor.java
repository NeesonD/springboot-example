package com.neeson.example.base.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 设置请求头，保证服务调用上下文的传输
 * @author DaiLe
 */
@Slf4j
@Order(1)
@Component
public class AddJwtHeaderRequestInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    private JwtContextService jwtContextService;

    // 这里待定
    private static final String JWT = "JWT";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        JwtContext jwtContext = jwtContextService.getJwtContext();
        return execution.execute(request, body);
    }
}
