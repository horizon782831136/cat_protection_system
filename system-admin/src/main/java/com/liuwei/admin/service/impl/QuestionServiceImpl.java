package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.QuestionDao;
import com.liuwei.admin.domain.vo.QuestionVO;
import com.liuwei.admin.service.QuestionService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * (Question)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:35:46
 */
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl extends ServiceImpl<QuestionDao,Question> implements QuestionService {
    private final QuestionDao questionDao;
    @Override
    public Object getQuestionByCondition(Integer current, Integer size, Long questionId, Long userId, Integer status) {
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(questionId), Question::getQuestionId, questionId)
                .like(ObjectUtil.isNotEmpty(userId), Question::getUserId, userId)
                .eq(ObjectUtil.isNotEmpty(status), Question::getStatus, status)
                .orderByDesc(Question::getCreateTime);
        Page<Question> page = new Page<>(current, size);
        questionDao.selectPage(page, queryWrapper);
        PageDTO<QuestionVO> of = PageDTO.of(page, QuestionVO.class);
        return new Result(of);
    }
}
