package com.liuwei.framework.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
@lombok.Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    public String name;
    public String originalName;
    public String path;
    public String detail;
    public Date createTime;
    public Date updateTime;
    public Integer status;
}
