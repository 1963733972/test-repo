package com.lagou.service.impl;/*
 * @ClassName MyAuthentication
 * @Desc TODO
 * @Author 19637
 * @Date 2021/11/22 9:14
 * @Version 1.0
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MyAuthentication implements AuthenticationSuccessHandler, AuthenticationFailureHandler
, LogoutSuccessHandler {

    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Autowired
    ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("登录失败。。。请求处理中");
        Map result = new HashMap();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
       result.put("code", HttpStatus.UNAUTHORIZED.value());
       result.put("message",e.getMessage());
       httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println("登陆成功。。。。请求处理中");
        Map result = new HashMap();
        result.put("code", HttpStatus.OK.value());
        result.put("message","登陆成功");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println("退出成功");
        redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/toLoginPage");
    }
}
