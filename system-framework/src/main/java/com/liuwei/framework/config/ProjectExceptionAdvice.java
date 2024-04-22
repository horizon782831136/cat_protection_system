package com.liuwei.framework.config;


import com.liuwei.framework.domain.Result;
import com.liuwei.framework.enums.Code;
import com.liuwei.framework.exception.BusinessException;
import com.liuwei.framework.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {
    //业务逻辑层
    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException ex){
        return new Result(ex.getCode(),  ex.getMessage());
    }

    //系统错误
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex){
        return new Result(ex.getCode(),  ex.getMessage());
    }

    //服务器错误
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex){
        return new Result(Code.SYSTEM_ERROR.getKey(), "系统繁忙请稍后再试！");
    }


}
