package com.liuwei.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuwei.framework.domain.po.Application;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


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

