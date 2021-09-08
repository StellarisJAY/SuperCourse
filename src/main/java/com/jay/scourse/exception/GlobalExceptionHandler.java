package com.jay.scourse.exception;

import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.CommonResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * <p>
 * 全局异常捕捉器
 * </p>
 *
 * @author Jay
 * @date 2021/8/7
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public CommonResult handle(Exception e){
        if(e instanceof GlobalException){
            return CommonResult.fail(((GlobalException) e).getCommonResultEnum(), null);
        }
        else if(e instanceof BindException){
            BindException ex = (BindException)e;
            return CommonResult.fail(CommonResultEnum.BIND_ERROR, ex.getAllErrors().get(0).getDefaultMessage(), null);
        }
        else{
            e.printStackTrace();
            return CommonResult.fail(CommonResultEnum.INTERNAL_ERROR, null);
        }
    }
}
