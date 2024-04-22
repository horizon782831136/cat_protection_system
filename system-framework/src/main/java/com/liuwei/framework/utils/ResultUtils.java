package com.liuwei.framework.utils;

import com.liuwei.framework.enums.Code;
import com.liuwei.framework.domain.Result;

public class ResultUtils {
    //添加
    public static Result add(boolean flag){
        return flag ? new Result(Code.ADD_SUCCESS.getKey(), Code.ADD_SUCCESS.getMsg()) :
                new Result(Code.ADD_FAILED.getKey(), Code.ADD_FAILED.getMsg());
    }

    //修改
    public static Result update(boolean flag){
        return flag ? new Result(Code.UPDATE_SUCCESS.getKey(), Code.UPDATE_SUCCESS.getMsg()) :
                new Result(Code.UPDATE_FAILED.getKey(), Code.UPDATE_FAILED.getMsg());
    }

    //删除
    public static Result delete(boolean flag){
        return flag ? new Result(Code.DELETE_SUCCESS.getKey(), Code.DELETE_SUCCESS.getMsg()) :
                new Result(Code.DELETE_FAILED.getKey(), Code.DELETE_FAILED.getMsg());
    }

    public static Result fail(Integer code, String msg){
        return new Result(code, msg);
    }

    public static Result warning(Integer code, String msg){
        return new Result(code, msg);
    }

    public static Result error(Integer code, String msg){
        return new Result(code, msg);
    }
}
