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
 * (Role)实体类
 *
 * @author makejava
 * @since 2024-01-02 14:22:38
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class Role implements Serializable , StateLifecycle<Role> {
    private static final long serialVersionUID = -65188769850019494L;
    @TableId
    private Long roleId;
    
    private String roleName;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;

    @TableLogic
    private Integer delFlag;

    @Override
    public Role init() {
        status = Status.NORMAL_STATUS.getKey();
        createTime = new Date();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public Role alter() {
        updateTime = new Date();
        return this;
    }

    @Override
    public Role halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public Role except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }

}

