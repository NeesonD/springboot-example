package com.neeson.example.util.exception;


import com.neeson.example.util.response.ResponseErrorEnum;
import com.neeson.example.util.response.ResponseResult;
import com.neeson.example.util.response.RestResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.UnexpectedTypeException;
import java.util.List;

/**
 *
 * @author daile
 * @date 2017/8/24
 * controllerAdvice用来处理全局异常
 * 局部异常和全局异常同时存在时，局部异常将被使用
 */
@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler
    private <T>ResponseResult<T> globalExceptionHandler(HttpServletRequest request,Exception e){
        log.error(request.getRequestURI()+"?"+request.getQueryString(), e);
        return RestResultGenerator.genErrorResult(ResponseErrorEnum.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(UnexpectedTypeException.class)
    private <T> ResponseResult<T> illegalParamsExceptionHandler(HttpServletRequest request,UnexpectedTypeException e) {
        log.error(request.getRequestURI()+"?"+request.getQueryString(), e);
        return RestResultGenerator.genErrorResult(ResponseErrorEnum.ILLEGAL_PARAMS);
    }

    @ExceptionHandler(MyException.class)
    private <T> ResponseResult<T> myException(HttpServletRequest request,MyException e){
        log.error(request.getRequestURI()+"?"+request.getQueryString(), e);
        return RestResultGenerator.genErrorResult(e.getMessage());
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult validException(HttpServletRequest request, MethodArgumentNotValidException e){
        log.error(request.getRequestURI()+"?"+request.getQueryString(), e);
        List<ObjectError> errors =e.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        for (ObjectError error : errors) {
            errorMsg.append(error.getDefaultMessage()).append(";");
        }
        return RestResultGenerator.genErrorResult(errorMsg.toString());
    }



}
