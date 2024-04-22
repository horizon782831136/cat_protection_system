package com.liuwei.user.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "消息实体")
public class MessageVO {
    @ApiModelProperty("消息ID")
    private Long messageId;

    @ApiModelProperty("发送者ID")
    private Long senderId;

    @ApiModelProperty("发送者用户名")
    private String username;

    @ApiModelProperty("消息内容")
    private String messageContent;

    @ApiModelProperty("消息类型")
    private Integer messageType;

    @ApiModelProperty("消息状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
