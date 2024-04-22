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
 * (Theme)实体类
 *
 * @author makejava
 * @since 2024-01-02 14:23:41
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class Theme implements Serializable , StateLifecycle<Theme> {
    private static final long serialVersionUID = -32280526565274362L;
    @TableId
    private Long themeId;
    
    private Long userId;
    
    private String shareCode;
    
    private String themeName;
    
    private String themeCss;
    
    private Long imageId;
    
    private Integer checked;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;

    @TableLogic
    private Integer delFlag;

    @Override
    public Theme init() {
        status = Status.NORMAL_STATUS.getKey();
        createTime = new Date();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public Theme alter() {
        updateTime = new Date();
        return this;
    }

    @Override
    public Theme halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public Theme except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }

}

