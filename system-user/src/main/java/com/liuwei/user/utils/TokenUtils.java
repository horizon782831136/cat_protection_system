package com.liuwei.user.utils;

import javax.servlet.http.HttpServletRequest;


public class TokenUtils {
    public static String getToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return token.substring(7);
    }
}
