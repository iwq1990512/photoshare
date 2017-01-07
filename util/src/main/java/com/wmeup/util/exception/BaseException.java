package com.wmeup.util.exception;

/**
 * 处理异常信息
 */
public class BaseException extends RuntimeException{
    private String code;
    private String msg;

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BaseException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public BaseException(String message, String code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(String message, Throwable cause, String code, String msg) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(Throwable cause, String code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
