package com.liuwei.framework.enums;

import lombok.Getter;

@Getter
public enum Status {
    //状态之间是可以相互转换的

    //可见
    PUBLICLY_VISIBLE(1, "公开可见"),
    PRIVATE_VISIBLE(2, "私密可见"),

    //未发布
    UNPUBLISHED(3, "未发布"),

    //状态
    NORMAL_STATUS(0, "正常状态"),
    HALTED_STATUS(-1, "停用状态"),
    EXCEPTION_STATUS(-2, "异常状态"),


    //逻辑删除
    DELETED(1, "已删除"),
    NOT_DELETED(0, "未删除"),

    //消息
    UNREAD_MESSAGE(4, "未读消息"),
    READ_MESSAGE(5, "已读消息"),

    //置顶
    IS_TOP(1,"置顶"),
    NOT_TOP(0,"不是置顶"),

    //绝育
    UNSTERILIZED(0, "未绝育"),
    STERILIZED(1, "已绝育"),
    UNKNOWN(2, "未知"),

    //健康状况
    FINE(1, "良好"),
    COMMON(2, "一般"),
    ILLNESS(3, "疾病"),
    DISABILITY(4, "残疾"),

    //猫咪状态
    NON_ADOPTION(6, "未领养"),
    ADOPTED(7, "已领养"),
    DEAD(8, "已死亡"),

    //提交申请
    NOT_SUBMITTED(9, "未提交"), //保存未提交的申请
    APPROVAL_PENDING(10, "待审核"),
    ALREADY_PASSED(11, "已通过"),
    ALREADY_REFUSED(12, "未通过"),
    CALL_BACK(13, "打回"),
    ACCOMPLISH(14, "已完成"),
    CANCELED(15, "已取消"),

    //选中
    UNCHECKED(0, "未选中"),
    CHECKED(1, "已选中");


    private final int key;
    private final String msg;
    Status(int key, String msg){
        this.key = key;
        this.msg = msg;
    }
}
