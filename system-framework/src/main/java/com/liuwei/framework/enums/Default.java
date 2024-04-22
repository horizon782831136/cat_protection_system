package com.liuwei.framework.enums;


import lombok.Getter;

@Getter
public enum Default {

    BUCKET_NAME("admin", "bucket name"),
    DURATION("7", "duration"),

    CAT_PARENT("1753709564299079682", "cat"),
    GENERATE_SHARE_CODE("1", "generate share code"),
    K("1024", "1k"),
    MAX_QUESTIONS_NO("10", "max questions no"),
    LIKE_WEIGHT("0.1", "like weight"),
    COMMENT_WEIGHT("0.02", "comment weight"),
    CLICK_WEIGHT("0.0004", "click weight"),
    RECOMMEND_SIZE("50", "recommend size"),

    VIDEO("video", "video"),
    IMAGE("image", "image"),
    TEXT("text", "text"),
    AUDIO("audio", "audio");

    private final String msg;
    private final String key;
    Default(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }
}
