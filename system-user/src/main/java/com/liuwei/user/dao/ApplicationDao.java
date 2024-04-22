package com.liuwei.user.dao;

import com.liuwei.framework.domain.po.Application;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * (Application)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-02 14:08:36
 */
@Mapper
@Repository
public interface ApplicationDao extends BaseMapper<Application>{

    

}

