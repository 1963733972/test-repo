package com.lagou.exception;/*
 * @ClassName ValidateCodeException
 * @Desc TODO
 * @Author 19637
 * @Date 2021/11/22 11:06
 * @Version 1.0
 */


import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg){
        super(msg);
    }
}
