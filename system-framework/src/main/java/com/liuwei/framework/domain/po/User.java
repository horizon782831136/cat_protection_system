package com.liuwei.framework.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.liuwei.framework.enums.Cate;
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
 * (User)实体类
 *
 * @author makejava
 * @since 2024-01-02 14:24:20
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 配置accessor风格
public class User implements Serializable, StateLifecycle<User> {
    private static final long serialVersionUID = -55283501774541089L;
    @TableId
    private Long userId;
    
    private String username;

    private String nickname;
    
    private Date dateOfBirth;
    
    private Integer gender;
    
    private String password;
    
    private String phoneNumber;

    private String name;

    private String idNumber;
    
    private String email;
    
    private String occupation;
    
    private String address;
    
    private String signature;
    
    private Long role;
    
    private Long avatar;

    private String detail;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;

    @TableLogic
    private Integer delFlag;

    @Override
    public User init() {
        status = Status.NORMAL_STATUS.getKey();
        createTime = new Date();
        role = Cate.USER.getKey();
        delFlag = Status.NOT_DELETED.getKey();
        return this;
    }

    @Override
    public User alter() {
        updateTime = new Date();
        return this;
    }

    @Override
    public User halt() {
        status = Status.HALTED_STATUS.getKey();
        return this;
    }

    @Override
    public User except() {
        status = Status.EXCEPTION_STATUS.getKey();
        return this;
    }
}

