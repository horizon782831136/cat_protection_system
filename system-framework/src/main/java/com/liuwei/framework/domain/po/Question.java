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
 * (Question)实体类
 *
 * @author makejava
 * @since 2024-01-02 14:21:31
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class Question implements Serializable , StateLifecycle<Question> {
    private static final long serialVersionUID = 986830240618954314L;
    @TableId
    private Long questionId;
    
    private Long userId;
    
    private String description;
    
    private String answer;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;

    @TableLogic
    private Integer delFlag;

    @Override
    public Question init() {
        status = Status.NORMAL_STATUS.getKey();
        createTime = new Date();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public Question alter() {
        updateTime = new Date();
        return this;
    }

    @Override
    public Question halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public Question except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }

}

