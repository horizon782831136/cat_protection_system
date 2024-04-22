package com.liuwei.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuwei.framework.domain.po.Recommended;

/**
 * (Recommended)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-02 14:35:57
 */
@Mapper
@Repository
public interface RecommendedDao extends BaseMapper<Recommended>{

    

}

