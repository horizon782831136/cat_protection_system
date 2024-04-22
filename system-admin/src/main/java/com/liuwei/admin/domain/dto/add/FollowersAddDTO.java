package com.liuwei.admin.domain.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("关注添加实体")
public class FollowersAddDTO {
    @ApiModelProperty(value = "跟随者id", required = true)
    private Long followerId;
    @ApiModelProperty(value = "被关注者id", required = true)
    private Long followeeId;

    @ApiModelProperty(value = "关注状态", required = true)
    private Integer status;
}
