package com.liuwei.user.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Message;
import com.liuwei.framework.enums.Code;
import com.liuwei.framework.enums.Status;
import com.liuwei.framework.enums.Type;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.domain.dto.add.MessageAddDTO;
import com.liuwei.user.service.MessageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * (Message)表控制层
 *
 * @author makejava
 * @since 2024-02-10 20:06:20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("message")
public class MessageController {
   private final MessageService messageService;

   //根据发送者ID查询消息基本信息
   @ApiOperation("根据发送者ID查询消息基本信息")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "senderId", value = "发送者ID", required = true),
           @ApiImplicitParam(name = "current", value = "当前页码", defaultValue = "1"),
           @ApiImplicitParam(name = "size", value = "每页条数", defaultValue = "10")
   })
   @PreAuthorize("hasAuthority('front:message:list')")
   @GetMapping("/senderId")
   public Result getMessageBySenderId(@RequestParam(value = "senderId") Long senderId,
                                      @RequestParam(value = "current", defaultValue = "1") Integer current,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size
                                      ){
     return messageService.getMessageBySenderId(senderId, current, size);
  }

  @ApiOperation("根据接收者ID查询消息基本信息")
  @PreAuthorize("hasAuthority('front:message:list')")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "receiverId", value = "接收者ID", required = true),
           @ApiImplicitParam(name = "current", value = "当前页码", defaultValue = "1"),
           @ApiImplicitParam(name = "size", value = "每页条数", defaultValue = "10")
   })
   @GetMapping("/receiverId")
   public Result getMessageByReceiverId(@RequestParam(value = "receiverId") Long receiverId,
                                        @RequestParam(value = "current", defaultValue = "1") Integer current,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size
                                        ){
     return messageService.getMessageByReceiverId(receiverId, current, size);
  }

  @ApiOperation("查询未读消息条数")
  @PreAuthorize("hasAuthority('front:message:list')")
  @GetMapping("/unread/{userId}")
   public Result getUnreadMessageCount(@PathVariable(value = "userId") Long userId){
      return messageService.getUnreadMessageCount(userId);
   }

  @ApiOperation("查询系统消息")
  @GetMapping("/system")
  public Result getSystemMessage(){
      return messageService.getSystemMessage();
   }
    @PreAuthorize("hasAuthority('front:message:update')")
   @ApiOperation("修改消息")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
           @ApiImplicitParam(name = "messageId", value = "消息ID", required = true)
   })
   @PutMapping
   public Result updateMessage(@RequestParam(value = "userId") Long userId,
                               @RequestParam(value = "messageId") Long messageId){
      LambdaQueryWrapper<Message> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(Message::getMessageId, messageId)
              .eq(Message::getReceiverId, userId)
              .select(Message::getMessageId, Message::getStatus);
       Message one = messageService.getOne(lambdaQueryWrapper);
       if(ObjectUtil.isNotEmpty(one) && one.getStatus().equals(Status.UNREAD_MESSAGE.getKey())){
            one.setStatus(Status.READ_MESSAGE.getKey());
           boolean update = messageService.updateById(one);
           return ResultUtils.update(update);
      }
       return null;
   }

   @ApiOperation("添加消息")
   @PreAuthorize("hasAuthority('front:message:add')")
   @PostMapping
   public Result addMessage(@RequestBody MessageAddDTO messageAddDTO){
      Message message = BeanUtils.copyBean(messageAddDTO, Message.class).init()
              .setMessageType(Type.USER_MESSAGE.getKey())
              .setStatus(Status.UNREAD_MESSAGE.getKey());
      boolean add = messageService.save(message);
      return ResultUtils.add(add);
   }

   @ApiOperation("删除消息")
   @PreAuthorize("hasAuthority('front:message:delete')")
   @ApiImplicitParam(name = "messageId", value = "消息id", required = true)
   @DeleteMapping("/{messageId}")
   public Result deleteMessage(@PathVariable(value = "messageId") Long messageId){
      LambdaQueryWrapper<Message> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(Message::getMessageId, messageId)
              .select(Message::getMessageType);
      Message one = messageService.getOne(lambdaQueryWrapper);
      if(one.getMessageType().equals(Type.SYSTEM_MESSAGE.getKey())){
         return ResultUtils.fail(Code.DELETE_FAILED.getKey(), "系统消息不能删除!");
      }
      boolean delete = messageService.removeById(messageId);
      return ResultUtils.delete(delete);
   }


}

