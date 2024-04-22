package com.liuwei.user.domain.dto.add;

import com.liuwei.framework.domain.bo.Content;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("评论添加实体")
public class CommentAddDTO {

    @ApiModelProperty(value = "父id", required = true)
    private Long parentId;

    @ApiModelProperty(value = "内容", required = true)
    private List<Content> contents;

    @ApiModelProperty(value = "上传列表", required = true)
    private List<Long> uploadList;
}
