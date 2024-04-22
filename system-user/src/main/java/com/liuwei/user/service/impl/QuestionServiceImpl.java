package com.liuwei.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Question;
import com.liuwei.framework.enums.Code;
import com.liuwei.framework.enums.Default;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.dao.QuestionDao;
import com.liuwei.user.domain.dto.add.QuestionAddDTO;
import com.liuwei.user.domain.vo.QuestionVO;
import com.liuwei.user.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


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
    public Result getQuestionByUserId(Long userId) {
        LambdaQueryWrapper<Question> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Question::getUserId, userId)
                .orderByDesc(Question::getCreateTime);
        List<Question> questions = questionDao.selectList(lambdaQueryWrapper);
        List<QuestionVO> questionVOS = BeanUtils.copyList(questions, QuestionVO.class);
        return new Result(questionVOS);
    }

    @Override
    public Result addQuestion(QuestionAddDTO questionAddDTO) {
        Question question = BeanUtils.copyBean(questionAddDTO, Question.class).init();
        //看当前问题数目是否小于最大问题数目
        LambdaQueryWrapper<Question> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Question::getUserId, question.getUserId());
        if (questionDao.selectCount(lambdaQueryWrapper) >= Integer.valueOf(Default.MAX_QUESTIONS_NO.getKey())) {
            return ResultUtils.fail(Code.ADD_FAILED.getKey(), "当前用户问题数目已达到最大问题数目");
        }
        boolean flag = questionDao.insert(question) > 0;
        return ResultUtils.add(flag);
    }
}
