package com.ashu.caller.exception;

public class CallerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CallerException(String code) {
        super(code);
    }

}
