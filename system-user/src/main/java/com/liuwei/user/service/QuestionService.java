package com.liuwei.user.service;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Question;
import com.liuwei.user.domain.dto.add.QuestionAddDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * (Question)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:35:46
 */
@Service
public interface QuestionService extends IService<Question>{


    Result getQuestionByUserId(Long userId);

    Result addQuestion(QuestionAddDTO questionAddDTO);
}
