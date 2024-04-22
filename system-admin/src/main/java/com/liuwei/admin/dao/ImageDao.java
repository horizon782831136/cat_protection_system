package com.liuwei.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuwei.framework.domain.po.Image;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Image)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-02 14:29:44
 */
@Mapper
@Repository
public interface ImageDao extends BaseMapper<Image>{

    

}

