package com.liuwei.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuwei.framework.domain.po.Animal;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * (Cat)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-02 14:12:33
 */
@Mapper
@Repository
public interface AnimalDao extends BaseMapper<Animal>{

    

}

