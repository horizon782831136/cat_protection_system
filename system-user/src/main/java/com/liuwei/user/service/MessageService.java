package com.liuwei.user.service;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Message;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * (Message)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:35:31
 */
@Service
public interface MessageService extends IService<Message>{


    Result getMessageBySenderId(Long senderId, Integer current, Integer size);

    Result getMessageByReceiverId(Long receiverId, Integer current, Integer size);

    Result getSystemMessage();

    Result getUnreadMessageCount(Long userId);
}
