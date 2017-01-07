package com.wmeup.util.exception;

import java.io.Serializable;

/**
 * @author zy
 */
public class HttpRequestUnknownException  extends Exception implements Serializable{

    public HttpRequestUnknownException() {
    }

    public HttpRequestUnknownException(String message) {
        super(message);
    }

    public HttpRequestUnknownException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestUnknownException(Throwable cause) {
        super(cause);
    }

    public HttpRequestUnknownException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
