package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Message;
import org.springframework.stereotype.Service;

/**
 * (Message)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:35:31
 */
@Service
public interface MessageService extends IService<Message>{
    Result getMessageByCondition(Integer current, Integer size, Long messageId, Long senderId, Long receiverId,
                                        Integer messageType, Integer status);
    

}
