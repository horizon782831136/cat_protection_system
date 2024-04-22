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
 * (Message)实体类
 *
 * @author makejava
 * @since 2024-01-02 14:21:11
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class Message implements Serializable , StateLifecycle<Message> {
    private static final long serialVersionUID = 124938310096050672L;
    @TableId
    private Long messageId;
    
    private Long senderId;
    
    private Long receiverId;
    
    private String messageContent;
    
    private Integer messageType;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;

    @TableLogic
    private Integer delFlag;

    @Override
    public Message init() {
        createTime = new Date();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public Message alter() {
        updateTime = new Date();
        return this;
    }

    @Override
    public Message halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public Message except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }
}

