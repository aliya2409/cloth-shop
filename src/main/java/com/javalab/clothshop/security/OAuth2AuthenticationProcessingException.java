package com.javalab.clothshop.security;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcessingException extends AuthenticationException {

    public OAuth2AuthenticationProcessingException(String detail) {
        super(detail);
    }

    public OAuth2AuthenticationProcessingException(String detail, Throwable ex) {
        super(detail, ex);
    }
}
