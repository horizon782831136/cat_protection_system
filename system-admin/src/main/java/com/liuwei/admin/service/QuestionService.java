package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.po.Question;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * (Question)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:35:46
 */
@Service
public interface QuestionService extends IService<Question>{

    public Object getQuestionByCondition(Integer current, Integer size, Long questionId, Long userId, Integer status);

}
