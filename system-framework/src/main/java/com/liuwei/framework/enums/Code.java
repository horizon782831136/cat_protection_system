package com.liuwei.framework.enums;

import lombok.Getter;

@Getter
public enum Code {
    LOGIN_SUCCESS(1001, "登录成功"),
    LOGIN_FAILED(1000, "认证失败，请重新登录"),
    LOGOUT_SUCCESS(200, "退出成功"),
    NO_PERMISSION(10010, "无访问权限"),
    PERMISSION(10011, "正常访问"),
    CAPTCHA_ERROR(100, "验证码错误"),
    CAPTCHA_CORRECT(101, "验证码正确"),
    CAPTCHA_EXPIRED(102, "验证码过期"),
    USERNAME_EXIST(105, "用户名已存在"),
    USERNAME_NOT_EXIST(106, "用户名不存在"),
    EMAIL_EXIST(109, "邮箱已存在"),
    EMAIL_NOT_EXIST(110, "邮箱不存在"),
    PHONE_EXIST(111, "手机号已存在"),
    PHONE_NOT_EXIST(112, "手机号不存在"),
    PASSWORD_ERROR(113, "密码错误"),
    //用户名或密码错误
    LOGIN_ERROR(115, "用户名或密码错误"),
    //注册成功
    REGISTER_SUCCESS(10001, "注册成功"),
    REGISTER_FAILED(10000, "注册失败"),
    ADD_SUCCESS(11001, "添加成功"),
    ADD_FAILED(11000, "添加失败"),
    UPDATE_SUCCESS(11011, "修改成功"),
    UPDATE_FAILED(11010,  "修改失败"),
    DELETE_SUCCESS(11101, "删除成功"),
    DELETE_FAILED(11100, "删除失败"),
    QUERY_SUCCESS(11111, "查询成功"),
    QUERY_FAILED(11110, "查询失败"),
    BUSINESS_ERROR(1010, "业务繁忙，请稍后再试！"),
    SYSTEM_ERROR(1100, "服务器正在维护，请稍后再试！");




    private final int key;
    private final String msg;
    Code(int key, String msg){
        this.key = key;
        this.msg = msg;
    }
}
