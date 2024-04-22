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
 * (Topic)实体类
 *
 * @author makejava
 * @since 2024-01-02 14:24:01
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class Topic implements Serializable, StateLifecycle<Topic> {
    private static final long serialVersionUID = -15388636152278368L;
    @TableId
    private Long topicId;
    
    private Long categoryId;
    
    private String title;
    
    private String content;
    
    private Long userId;
    
    private Long parentId;
    
    private Integer isTopic;

    private Integer clickCount;

    private float weight;

    private Integer commentCount;

    private double score;

    private Integer isTop;
    
    private Integer likeCount;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;

    @TableLogic
    private Integer delFlag;

    @Override
    public Topic init() {
        createTime = new Date();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public Topic alter() {
        updateTime = new Date();
        return this;
    }

    @Override
    public Topic halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public Topic except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }

}

