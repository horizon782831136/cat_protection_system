package com.liuwei.framework.enums;

import lombok.Getter;

@Getter
public enum Type {
    //消息类型
    SYSTEM_MESSAGE(0, "系统消息"),
    USER_MESSAGE(1, "用户消息"),

    //申请类型
    IS_ADOPTION(1,"是领养"),
    IS_FOSTER(2,"是寄养"),
    //话题
    IS_TOPIC(1,"是话题"),
    NOT_TOPIC(0,"不是话题"),

    //性别
    FEMALE(0, "女"),
    MALE(1, "男");



    private final int key;
    private final String msg;
    Type(int key, String msg){
        this.key = key;
        this.msg = msg;
    }
}
