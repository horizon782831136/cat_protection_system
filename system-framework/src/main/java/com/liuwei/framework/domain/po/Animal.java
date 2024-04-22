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
 * (Cat)实体类
 *
 * @author makejava
 * @since 2024-01-02 13:34:06
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class Animal implements Serializable , StateLifecycle<Animal> {
    private static final long serialVersionUID = 903151980296935559L;
    @TableId
    private Long animalId;
    
    private String name;
    
    private Long imageId;
    
    private Integer gender;
    
    private Integer age;
    
    private Long categoryId;
    
    private Integer neutering;
    
    private Date registrationTime;
    
    private String description;

    private Integer health;

    private String detail;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;

    @TableLogic
    private Integer delFlag;


    @Override
    public Animal init() {
        status = Status.NON_ADOPTION.getKey();
        createTime = new Date();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public Animal alter() {
        updateTime = new Date();
        return this;
    }

    @Override
    public Animal halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public Animal except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }
}

