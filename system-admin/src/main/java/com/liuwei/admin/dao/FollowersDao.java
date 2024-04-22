package com.liuwei.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuwei.framework.domain.po.Followers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Followers)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-02 14:15:37
 */
@Mapper
@Repository
public interface FollowersDao extends BaseMapper<Followers>{

    

}

