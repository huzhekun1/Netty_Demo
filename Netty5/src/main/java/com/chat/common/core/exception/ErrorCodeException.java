package com.chat.common.core.exception;

/**
 * @author hzk
 * @date 2018/10/22
 */
public class ErrorCodeException extends RuntimeException{

    private final int errorCode;

    public int getErrorCode(){
        return errorCode;
    }

    public ErrorCodeException(int errorCode){
        this.errorCode = errorCode;
    }
}
