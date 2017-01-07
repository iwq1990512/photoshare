package com.wmeup.util.exception;

/**
 * @author zy
 */
public class ValidationException extends BaseException{
    public ValidationException(String code, String msg) {
        super(code, msg);
    }

    public ValidationException(String message, String code, String msg) {
        super(message, code, msg);
    }

    public ValidationException(String message, Throwable cause, String code, String msg) {
        super(message, cause, code, msg);
    }

    public ValidationException(Throwable cause, String code, String msg) {
        super(cause, code, msg);
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace, code, msg);
    }
}
