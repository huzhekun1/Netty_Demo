package com.chat.common.core.model;

import com.chat.common.core.constants.ResultCodeEnum;
import com.chat.common.core.serializable.Serializable;

/**
 * 结果对象
 * @author hzk
 * @date 2018/10/23
 */
public class Result<T extends Serializable>{

    /**
     * 结果码
     */
    private int resultCode;

    /**
     * 结果内容
     */
    private T content;

    public static <T extends Serializable> Result<T> success(T content){
        Result<T> tResult = new Result<>();
        tResult.resultCode = ResultCodeEnum.SUCCESS.getResultCode();
        tResult.content = content;
        return tResult;
    }

    public static <T extends Serializable> Result<T> success(){
        Result<T> tResult = new Result<>();
        tResult.resultCode = ResultCodeEnum.SUCCESS.getResultCode();
        return tResult;
    }

    public static <T extends Serializable> Result<T> error(int resultCode){
        Result<T> tResult = new Result<>();
        tResult.setResultCode(resultCode);
        return tResult;
    }

    public static <T extends Serializable> Result<T> valueOf(int resultCode,T content){
        Result<T> tResult = new Result<>();
        tResult.resultCode = resultCode;
        tResult.content = content;
        return tResult;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    /**
     * 判断状态码是否成功
     * @return
     */
    public boolean isSuccess(){
        return this.resultCode == ResultCodeEnum.SUCCESS.getResultCode();
    }
}
