package com.liuwei.admin.domain.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel(description = "用户添加实体")
public class UserAddDTO {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "用户昵称", required = true)
    private String nickname;
    @ApiModelProperty("出生日期")
    private Date dateOfBirth;
    @ApiModelProperty(value = "性别", required = true)
    private Integer gender;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty("电话号码")
    private String phoneNumber;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("身份证号")
    private String idNumber;
    @ApiModelProperty("电子邮件")
    private String email;
    @ApiModelProperty("职业")
    private String occupation;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("签名")
    private String signature;
    @ApiModelProperty(value = "角色", required = true)
    private Long role;
    @ApiModelProperty("头像")
    private Long avatar;
}
