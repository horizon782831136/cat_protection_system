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
 * (Vaccine)实体类
 *
 * @author makejava
 * @since 2024-01-02 14:24:35
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class Vaccine implements Serializable , StateLifecycle<Vaccine> {
    private static final long serialVersionUID = -47310658776244047L;
    @TableId
    private Long vaccineId;
    
    private Long animalId;
    
    private Long userId;
    
    private String type;
    
    private Date inoculationTime;
    
    private String address;
    
    private Integer periodOfValidity;

    private String name;
    
    private Long imageId;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;

    @TableLogic
    private Integer delFlag;

    @Override
    public Vaccine init() {
        createTime = new Date();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public Vaccine alter() {
        updateTime = new Date();
        return this;
    }

    @Override
    public Vaccine halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public Vaccine except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }
}

