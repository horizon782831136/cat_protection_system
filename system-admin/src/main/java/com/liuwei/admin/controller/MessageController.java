package com.liuwei.admin.controller;

import cn.hutool.core.util.ObjectUtil;
import com.liuwei.admin.domain.dto.add.MessageAddDTO;
import com.liuwei.admin.domain.dto.update.MessageUpdateDTO;
import com.liuwei.admin.service.MessageService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Message;
import com.liuwei.framework.enums.Cate;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

   @ApiOperation("条件查询消息")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "current", value = "当前页"),
           @ApiImplicitParam(name = "size", value = "每页条数"),
           @ApiImplicitParam(name = "messageId", value = "消息Id"),
           @ApiImplicitParam(name = "senderId", value = "发送者id"),
           @ApiImplicitParam(name = "receiverId", value = "接收者id"),
           @ApiImplicitParam(name = "messageType", value = "消息类型"),
           @ApiImplicitParam(name = "status", value = "消息状态")
   })
   @PreAuthorize("hasAuthority('system:message:list')")
   @GetMapping("/list")
   public Result getMessageByCondition(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     @RequestParam(value = "senderId", required = false) Long senderId,
                                     @RequestParam(value = "messageId", required = false) Long messageId,
                                     @RequestParam(value = "receiverId", required = false) Long receiverId,
                                     @RequestParam(value = "messageType", required = false) Integer messageType,
                                     @RequestParam(value = "status", required = false) Integer status){
      return messageService.getMessageByCondition(current, size, messageId, senderId, receiverId, messageType, status);
   }

   @ApiOperation("修改消息")
   @PreAuthorize("hasAuthority('system:message:update')")
   @PutMapping
   public Result updateMessage(@RequestBody MessageUpdateDTO messageUpdateDTO){
      Message message = BeanUtils.copyBean(messageUpdateDTO, Message.class).alter();
      boolean update = messageService.updateById(message);
      return ResultUtils.update(update);
   }

   @ApiOperation("添加消息")
   @PreAuthorize("hasAuthority('system:message:add')")
   @PostMapping
   public Result addMessage(@RequestBody MessageAddDTO messageAddDTO){
      Message message = BeanUtils.copyBean(messageAddDTO, Message.class).init();
      if(ObjectUtil.isEmpty(message.getSenderId())){
         message = message.setSenderId(Cate.SYSTEM.getKey()).setReceiverId(Cate.VISITOR.getKey());
      }
      boolean add = messageService.save(message);
      return ResultUtils.add(add);
   }

   @ApiOperation("删除消息")
   @PreAuthorize("hasAuthority('system:message:delete')")
   @ApiImplicitParam(name = "messageId", value = "消息id", required = true)
   @DeleteMapping("/{messageId}")
   public Result deleteMessage(@PathVariable(value = "messageId") Long messageId){
      boolean delete = messageService.removeById(messageId);
      return ResultUtils.delete(delete);
   }

   @ApiOperation("批量删除")
   @PreAuthorize("hasAuthority('system:message:delete')")
   @ApiImplicitParam(name = "messageIds", value = "消息id", required = true)
   @DeleteMapping("/batchDelete")
   public Result deleteMessages(@RequestParam("ids") List<Long> ids){
      boolean delete = messageService.removeByIds(ids);
      return ResultUtils.delete(delete);
   }

}

