package com.lagou.service.impl;/*
 * @ClassName MyAuthorized
 * @Desc TODO
 * @Author 19637
 * @Date 2021/11/22 18:22
 * @Version 1.0
 */

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
@Service
public class MyAuthorized {
    public boolean check(Authentication authentication, HttpServletRequest request){
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        if("admin".equals(principal.getUsername())){
            return true;
        }else{
            String uri = request.getRequestURI();
            if(uri.contains("/user")){
                for(GrantedAuthority authority:authorities)
                    if("ROLE_ADMIN".equals(authority.getAuthority()))
                        return true;
            }
        }
        return false;
    }
    public boolean check(Authentication authentication,HttpServletRequest request,Integer id){
        if(id>10)
            return false;
        return true;
    }
}
