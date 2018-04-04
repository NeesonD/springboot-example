package com.neeson.example.util.response;

import lombok.Data;


/**
 * @author MyPC
 */
@Data
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;


    public static <T> ResponseResult<T> newInstance(){
        return new ResponseResult<>();
    }


    public void setErrorInfo(ResponseErrorEnum responseErrorEnum) {
        this.message = responseErrorEnum.getMessage();
    }

}
