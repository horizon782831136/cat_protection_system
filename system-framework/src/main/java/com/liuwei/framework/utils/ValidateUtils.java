package com.liuwei.framework.utils;

public class ValidateUtils {

    //校验用户名是否符合要求
    public static boolean validateUsername(String username)
    {

        if(!username.matches("^[a-zA-Z0-9_]{6,16}$")){
            return false;
        }
        return true;
    }

    //校验密码是否符合要求
    public static boolean validatePassword(String password)
    {
        //密码只能是长度为6~16的数字、字母、下划线、特殊符号
        if(!password.matches("^[a-zA-Z0-9_]{6,16}$")){
            return false;
        }
        return true;
    }
}
