package com.lagou.handle;/*
 * @ClassName MyHandler
 * @Desc TODO
 * @Author 19637
 * @Date 2021/11/22 17:46
 * @Version 1.0
 */

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class MyHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        httpServletResponse.getWriter().write("权限不足，联系管理员");
    }
}
