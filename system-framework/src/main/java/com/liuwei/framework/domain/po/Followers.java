package com.liuwei.framework.domain.po;

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
import java.util.Date;

/**
 * (Followers)实体类
 *
 * @author makejava
 * @since 2024-01-02 14:26:23
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class Followers implements Serializable , StateLifecycle<Followers> {
    private static final long serialVersionUID = 347235117349413379L;
    @TableId
    private Long followersId;
    
    private Long followerId;
    
    private Long followeeId;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;

    @TableLogic
    private Integer delFlag;

    @Override
    public Followers init() {
        createTime = new Date();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public Followers alter() {
        updateTime = new Date();
        return this;
    }

    @Override
    public Followers halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public Followers except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }

}

