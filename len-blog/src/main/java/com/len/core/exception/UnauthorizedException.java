package com.len.core.exception;

/**
 * 未授权异常
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }
}
