package com.liuwei.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuwei.framework.domain.po.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Question)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-02 14:35:46
 */
@Mapper
@Repository
public interface QuestionDao extends BaseMapper<Question>{

    

}

