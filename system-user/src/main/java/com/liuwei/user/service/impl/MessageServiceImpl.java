package com.liuwei.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Message;
import com.liuwei.framework.domain.po.User;
import com.liuwei.framework.enums.Status;
import com.liuwei.framework.enums.Type;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.user.dao.MessageDao;
import com.liuwei.user.domain.vo.MessageVO;
import com.liuwei.user.service.MessageService;
import com.liuwei.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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
    private final UserService userService;
    @Override
    public Result getMessageBySenderId(Long senderId, Integer current, Integer size) {
        //todo 后期要改成从token获取
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getSenderId, senderId)
                    .orderByDesc(Message::getCreateTime);
        Page<Message> page = new Page<>(current, size);
        page = messageDao.selectPage(page, queryWrapper);
        List<MessageVO> collect = page.getRecords().stream().map(message -> {
            MessageVO messageVO = BeanUtils.copyBean(message, MessageVO.class);
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getUserId, senderId)
                    .select(User::getUsername);
            User one = userService.getOne(userLambdaQueryWrapper);
            messageVO.setUsername(one.getUsername());
            return messageVO;
        }).collect(Collectors.toList());
        Page<MessageVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal());
        PageDTO<MessageVO> of = PageDTO.of(newPage, MessageVO.class);
        return new Result(of);
    }

    @Override
    public Result getMessageByReceiverId(Long receiverId, Integer current, Integer size) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getReceiverId, receiverId)
                .orderByDesc(Message::getCreateTime);
        Page<Message> page = new Page<>(current, size);
        page = messageDao.selectPage(page, queryWrapper);
        List<MessageVO> collect = page.getRecords().stream().map(message -> {
            MessageVO messageVO = BeanUtils.copyBean(message, MessageVO.class);
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getUserId, receiverId)
                    .select(User::getUsername);
            User one = userService.getOne(userLambdaQueryWrapper);
            messageVO.setUsername(one.getUsername());
            return messageVO;
        }).collect(Collectors.toList());
        Page<MessageVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal());
        PageDTO<MessageVO> of = PageDTO.of(newPage, MessageVO.class);
        return new Result(of);
    }

    @Override
    public Result getSystemMessage() {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getMessageType, Type.SYSTEM_MESSAGE.getKey())
                .orderByDesc(Message::getCreateTime);
        List<Message> messages = messageDao.selectList(queryWrapper);
        List<MessageVO> messageVOS = BeanUtils.copyList(messages, MessageVO.class);
        return new Result(messageVOS);
    }

    @Override
    public Result getUnreadMessageCount(Long userId) {
        LambdaQueryWrapper<Message>  queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getReceiverId, userId)
                .eq(Message::getStatus, Status.UNREAD_MESSAGE.getKey());
        LambdaQueryWrapper<Message> messageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        messageLambdaQueryWrapper.eq(Message::getMessageType, Type.SYSTEM_MESSAGE.getKey())
                .eq(Message::getStatus, Status.UNREAD_MESSAGE.getKey());
        int cnt = messageDao.selectCount(queryWrapper) + messageDao.selectCount(messageLambdaQueryWrapper);
        return new Result(cnt);
    }
}
