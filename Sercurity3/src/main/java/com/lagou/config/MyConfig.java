package com.lagou.config;/*
 * @ClassName MyConfig
 * @Desc TODO
 * @Author 19637
 * @Date 2021/11/21 20:57
 * @Version 1.0
 */

import com.lagou.domain.Permission;
import com.lagou.filter.ValidateCodeFilter;
import com.lagou.handle.MyHandler;
import com.lagou.service.impl.MyAuthentication;
import com.lagou.service.impl.MyDetailsImp;
import com.lagou.service.impl.PermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    MyDetailsImp myDetails;
    @Autowired
    DataSource dataSource;
    @Autowired
    MyAuthentication myAuthentication;
    @Autowired
    ValidateCodeFilter validateCodeFilter;
    @Autowired
    MyHandler myHandler;
    @Autowired
    PermissionServiceImpl permissionService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .formLogin().loginPage("/toLoginPage")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(myAuthentication)
                .failureHandler(myAuthentication)
                .and().logout().logoutUrl("/log").logoutSuccessHandler(myAuthentication)
                .and().rememberMe().tokenValiditySeconds(60).rememberMeParameter("remember-me").tokenRepository(getPersistentTokenRepository())
                .and().authorizeRequests().antMatchers("/toLoginPage").permitAll();
        http.exceptionHandling().accessDeniedHandler(myHandler);
        http.headers().frameOptions().sameOrigin();
//
        http.csrf().disable();
        http.sessionManagement()
                .invalidSessionUrl("/toLoginPage")
                .maximumSessions(1)
                .expiredUrl("/toLoginPage")
                .maxSessionsPreventsLogin(true);
        for(Permission p : permissionService.list()){
            http.authorizeRequests().antMatchers(p.getPermissionUrl()).hasAuthority(p.getPermissionTag());
        }
        http.authorizeRequests().anyRequest().authenticated();
    }
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//       jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/images/**", "/js/**","/code/**");
    }
    /*
            * @description
            *                   身份安全管理
            * @author
            * @date 21:48 2021/11/21
            * @return {@link void}
            **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(myDetails);
    }
}
