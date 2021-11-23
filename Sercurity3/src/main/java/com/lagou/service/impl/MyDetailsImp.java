package com.lagou.service.impl;/*
 * @ClassName MyDetailsImp
 * @Desc TODO
 * @Author 19637
 * @Date 2021/11/21 21:43
 * @Version 1.0
 */

import com.lagou.domain.Permission;
import com.lagou.domain.User;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MyDetailsImp implements UserDetailsService {
    @Autowired
    UserService userService;
    @Autowired
    PermissionServiceImpl permissionService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if(user ==null){
            throw new UsernameNotFoundException("用户找不到"+username);
        }
        Collection<GrantedAuthority> list = new ArrayList<>();
        List<Permission> permissions = permissionService.findByUserId(user.getId());
        for(Permission p : permissions){
            list.add(new SimpleGrantedAuthority(p.getPermissionTag()));
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username,"{bcrypt}"+user.getPassword(),list);
        return userDetails;
    }

}
