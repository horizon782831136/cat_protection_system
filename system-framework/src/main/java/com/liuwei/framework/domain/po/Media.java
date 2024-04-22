package com.liuwei.framework.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.liuwei.framework.enums.Status;
import com.liuwei.framework.interfaces.StateLifecycle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (Media)实体类
 *
 * @author makejava
 * @since 2024-01-02 14:20:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class Media extends com.liuwei.framework.domain.Data implements Serializable , StateLifecycle<Media>{
    private static final long serialVersionUID = -97514870733514910L;
    @TableId
    private Long mediaId;
    
    private Long topicId;

    private Long userId;
    
    private String mediumType;

    @TableLogic
    private Integer delFlag;

    @Override
    public Media init() {
        status = Status.NORMAL_STATUS.getKey();
        createTime = new Date();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public Media alter() {
        return this;
    }

    @Override
    public Media halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public Media except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }
}

