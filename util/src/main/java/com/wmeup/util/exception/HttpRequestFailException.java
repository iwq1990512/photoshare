package com.wmeup.util.exception;

import java.io.Serializable;

/**
 * @author zy
 */
public class HttpRequestFailException extends Exception implements Serializable{

    public HttpRequestFailException() {
    }

    public HttpRequestFailException(String message) {
        super(message);
    }

    public HttpRequestFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestFailException(Throwable cause) {
        super(cause);
    }

    public HttpRequestFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
