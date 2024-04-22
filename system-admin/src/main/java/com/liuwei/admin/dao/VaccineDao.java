package com.liuwei.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuwei.framework.domain.po.Vaccine;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Vaccine)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-02 14:37:07
 */
@Mapper
@Repository
public interface VaccineDao extends BaseMapper<Vaccine>{

    

}

