package com.neeson.example.util.exception;


/**
 * @author MyPC
 */
public class MyException extends RuntimeException {
    private Integer error;

    public MyException(String message,int error){
        super(message);
        this.error = error;
    }

    public MyException(String message){
        super(message);
    }

}
