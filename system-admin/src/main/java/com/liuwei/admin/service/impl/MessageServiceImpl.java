package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.MessageDao;
import com.liuwei.admin.domain.vo.MessageVO;
import com.liuwei.admin.service.MessageService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * (Message)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:35:31
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageDao,Message> implements MessageService {
    private final MessageDao messageDao;

    @Override
    public Result getMessageByCondition(Integer current, Integer size, Long messageId, Long senderId, Long receiverId, Integer messageType, Integer status) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(messageId), Message::getMessageId, messageId)
                .like(ObjectUtil.isNotEmpty(senderId), Message::getSenderId, senderId)
                .like(ObjectUtil.isNotEmpty(receiverId), Message::getReceiverId, receiverId)
                .eq(ObjectUtil.isNotEmpty(messageType), Message::getMessageType, messageType)
                .eq(ObjectUtil.isNotEmpty(status), Message::getStatus, status)
                .orderByDesc(Message::getCreateTime);
        Page<Message> page = new Page<>(current, size);
        messageDao.selectPage(page, queryWrapper);
        PageDTO<MessageVO> of = PageDTO.of(page, MessageVO.class);
        return new Result(of);
    }
}
