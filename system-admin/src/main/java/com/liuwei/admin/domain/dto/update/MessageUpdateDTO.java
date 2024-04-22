package com.liuwei.admin.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("消息添加实体")
public class MessageUpdateDTO {
    @ApiModelProperty(value = "消息id", required = true)
    private Long messageId;
    @ApiModelProperty(value = "发送者id")
    private Long senderId;
    @ApiModelProperty(value = "接收者id")
    private Long receiverId;
    @ApiModelProperty(value = "消息内容")
    private String messageContent;
    @ApiModelProperty(value = "消息状态")
    private Integer status;
}
