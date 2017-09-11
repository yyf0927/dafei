package com.mjs.xiaomi.exception;

/**
 * Created by dafei on 2017/9/8.
 */
public class ApiException extends RuntimeException {
    public ApiException(Throwable t) {
        super(t);
    }
}
