package com.stc.tracnghiemonline.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 10:39 PM
 * Filename  : UserNotFoundAuthenticationException
 */
public class UserNotFoundAuthenticationException extends AuthenticationException {
    public UserNotFoundAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserNotFoundAuthenticationException(String msg) {
        super(msg);
    }
}
