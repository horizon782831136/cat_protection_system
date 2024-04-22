package com.liuwei.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuwei.framework.domain.po.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-02 14:36:21
 */
@Mapper
@Repository
public interface RoleMenuDao extends BaseMapper<RoleMenu>{

    

}

