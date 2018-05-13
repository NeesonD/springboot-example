package com.neeson.example.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author daile
 * @date 2017/8/15
 * desc： 解决跨域
 */
public class SpringFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String originHeader=httpServletRequest.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type,token");
        response.setHeader("Access-Control-Max-Age", "1800");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(httpServletRequest, response);
    }

}