package com.liuwei.framework.domain.po;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.liuwei.framework.enums.Status;
import com.liuwei.framework.interfaces.StateLifecycle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (Breed)实体类
 *
 * @author makejava
 * @since 2024-01-02 13:32:46
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class Category implements Serializable , StateLifecycle<Category> {
    private static final long serialVersionUID = 597745827044085639L;
    @TableId
    private Long categoryId;

    private Long parentId;
    
    private String name;
    
    private String description;
    
    private Long imageId;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;

    @TableLogic
    private Integer delFlag;

    @Override
    public Category init() {
        status = Status.NORMAL_STATUS.getKey();
        createTime = new Date();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public Category alter() {
        updateTime = new Date();
        return this;
    }

    @Override
    public Category halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public Category except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }

}

