package com.liuwei.framework.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class EncodeUtils {
    public static String encode(String str) {
        String salt = "Mr.Liu";
        String result = DigestUtils.md5DigestAsHex((str + salt).getBytes(StandardCharsets.UTF_8));
        return result;
    }

    public static void main(String[] args) {
        System.out.println(encode("123456"));
    }
}
