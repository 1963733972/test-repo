package com.lagou.filter;/*
 * @ClassName ValidateCodeFilter
 * @Desc TODO
 * @Author 19637
 * @Date 2021/11/22 10:59
 * @Version 1.0
 */

import com.lagou.exception.ValidateCodeException;
import com.lagou.controller.ValidateCodeController;
import com.lagou.service.impl.MyAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {
   @Autowired
    MyAuthentication myAuthentication;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/login") && request.getMethod().equalsIgnoreCase("post")) {
            String imageCode = request.getParameter("imageCode");
            try {
                Validate(request, imageCode);
            } catch (ValidateCodeException e) {
                myAuthentication.onAuthenticationFailure(request,response,e);
                return ;
            }
        }


        doFilter(request, response, filterChain);
    }

    @Autowired
    StringRedisTemplate redisTemplate;

    private void Validate(HttpServletRequest request, String imageCode) throws ValidateCodeException {
        String redisKey = ValidateCodeController.REDIS_KEY_IMAGE_CODE + "-" + request.getRemoteAddr();
        System.out.println(redisKey);
        String code =  redisTemplate.boundValueOps(redisKey).get();
        //为空
        if (!StringUtils.hasText(imageCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        //过期
        if (!StringUtils.hasText(code)) {
            throw new ValidateCodeException("验证码已经过期");
        }
        //错误
        if (!code.equalsIgnoreCase(imageCode)) {
            throw new ValidateCodeException("验证码错误");
        }
    }
}
