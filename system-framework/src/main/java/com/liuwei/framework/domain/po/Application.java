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
 * (Application)实体类
 *
 * @author makejava
 * @since 2024-01-02 13:13:06
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class Application implements Serializable , StateLifecycle<Application> {
    private static final long serialVersionUID = 339991381048137487L;
    @TableId
    private Long applicationId;
    
    private Long userId;
    
    private Integer type;
    //将来存json类型的数据
    private String detail;
    
    private String reasonForApplication;
    
    private Long referrer;
    
    private String reasonForApproval;
    
    private Long administratorId;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;

    @TableLogic
    private Integer delFlag;

    @Override
    public Application init() {
        createTime = new Date();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public Application alter() {
        updateTime = new Date();
        return this;
    }

    @Override
    public Application halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public Application except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }

}

