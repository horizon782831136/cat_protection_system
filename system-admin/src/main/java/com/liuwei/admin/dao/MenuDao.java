package com.liuwei.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuwei.framework.domain.po.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Menu)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-02 14:35:18
 */
@Mapper
@Repository
public interface MenuDao extends BaseMapper<Menu>{

    

}

