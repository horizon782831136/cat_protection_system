package com.liuwei.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuwei.framework.domain.po.Theme;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Theme)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-02 14:36:33
 */
@Mapper
@Repository
public interface ThemeDao extends BaseMapper<Theme>{

    

}

