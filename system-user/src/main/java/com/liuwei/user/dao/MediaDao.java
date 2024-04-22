package com.liuwei.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuwei.framework.domain.po.Media;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * (Media)表数据库访问层
 *
 * @author makejava
 * @since 2024-02-10 20:02:55
 */
@Mapper
@Repository
public interface MediaDao extends BaseMapper<Media>{

    

}

