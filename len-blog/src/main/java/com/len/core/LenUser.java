package com.len.core;

import com.len.util.JWTUtil;
import com.len.util.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;

/**
 *
 */
public class LenUser {
    public static Principal getPrincipal() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Object principal = subject.getPrincipal();
            if (principal != null) {
                return JWTUtil.getPrincipal(principal.toString());
            } else {
                throw new UnknownAccountException("current user null");
            }
        } else {
            throw new UnknownAccountException("current user null");
        }
    }
}
