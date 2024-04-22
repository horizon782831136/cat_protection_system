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
 * (Image)实体类
 *
 * @author makejava
 * @since 2024-01-02 14:20:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class Image extends com.liuwei.framework.domain.Data implements Serializable , StateLifecycle<Image> {
    private static final long serialVersionUID = -91389984584345484L;
    @TableId
    private Long imageId;

    private Long userId;

    @TableLogic
    private Integer delFlag;

    @Override
    public Image init() {
        status = Status.NORMAL_STATUS.getKey();
        createTime = new Date();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public Image alter() {
        return this;
    }

    @Override
    public Image halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public Image except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }

}

