package com.neeson.example.base.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Create on 2019-09-23
 * 打印服务间调用日志
 *
 * @author DaiLe
 */
@Slf4j
@Order(100)
@Component
public class LogRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        log.info("\n【请求地址】: {}\n【请求头】：{}\n【请求参数】：{}", request.getURI(),request.getHeaders(),new String(body, "UTF-8"));
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        log.info("\n【Status code】: {}\n【Response body】：{}", response.getStatusCode(), StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
    }
}
