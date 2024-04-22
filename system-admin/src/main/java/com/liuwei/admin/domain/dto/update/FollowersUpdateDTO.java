package com.liuwei.admin.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("关注修改实体")
public class FollowersUpdateDTO {
    @ApiModelProperty(value = "关注id", required = true)
    private Long followersId;
    @ApiModelProperty(value = "跟随者id")
    private Long followerId;
    @ApiModelProperty(value = "被关注者id")
    private Long followeeId;

    @ApiModelProperty(value = "关注状态")
    private Integer status;

}
