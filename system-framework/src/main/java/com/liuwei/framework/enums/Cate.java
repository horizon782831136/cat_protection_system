package com.liuwei.framework.enums;

import lombok.Getter;

@Getter
public enum Cate {
    //顶层种类
    TOP_CATEGORY(0, "顶层"),

    //访客通用id
    VISITOR(0, "访客"),

    //系统专用id
    SYSTEM(8, "系统"),

    //普通用户
    USER(1742095126664146945l, "普通用户"),
    //管理员
    ADMIN(1, "管理员"),

    //测试
    TEST(1752757742369554433L, "测试用户");
    private final String msg;
    private final long key;
    Cate(long key, String msg) {
        this.key = key;
        this.msg = msg;
    }
}
