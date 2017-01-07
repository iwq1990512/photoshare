package com.wmeup.util.exception;

/**
 * @author zy
 */
public class BusinessException extends BaseException{
    public BusinessException(String code, String msg) {
        super(code, msg);
    }

    public BusinessException(String message, String code, String msg) {
        super(message, code, msg);
    }

    public BusinessException(String message, Throwable cause, String code, String msg) {
        super(message, cause, code, msg);
    }

    public BusinessException(Throwable cause, String code, String msg) {
        super(cause, code, msg);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace, code, msg);
    }
}
