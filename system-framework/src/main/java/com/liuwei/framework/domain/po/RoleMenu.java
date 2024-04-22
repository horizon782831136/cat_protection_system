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
 * (RoleMenu)实体类
 *
 * @author makejava
 * @since 2024-01-02 14:22:58
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class RoleMenu implements Serializable , StateLifecycle<RoleMenu> {
    private static final long serialVersionUID = 397244115162232851L;
    @TableId
    private Long id;
    
    private Long roleId;
    
    private Long menuId;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;

    @TableLogic
    private Integer delFlag;

    @Override
    public RoleMenu init() {
        status = Status.NORMAL_STATUS.getKey();
        createTime = new Date();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public RoleMenu alter() {
        updateTime = new Date();
        return this;
    }

    @Override
    public RoleMenu halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public RoleMenu except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }
}

