package com.liuwei.user.domain.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("消息添加实体")
public class MessageAddDTO {
    @ApiModelProperty(value = "发送者id")
    private Long senderId;
    @ApiModelProperty(value = "接收者id")
    private Long receiverId;
    @ApiModelProperty(value = "消息内容", required = true)
    private String messageContent;
}
