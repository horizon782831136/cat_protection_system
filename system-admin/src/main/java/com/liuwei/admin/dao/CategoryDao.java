package com.liuwei.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuwei.framework.domain.po.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * (Breed)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-02 14:11:31
 */
@Mapper
@Repository
public interface CategoryDao extends BaseMapper<Category>{

    

}

