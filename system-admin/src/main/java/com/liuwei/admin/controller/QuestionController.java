package com.liuwei.admin.controller;

import com.liuwei.admin.domain.dto.add.QuestionAddDTO;
import com.liuwei.admin.domain.dto.update.QuestionUpdateDTO;
import com.liuwei.admin.service.QuestionService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Question;
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
 * (Question)表控制层
 *
 * @author makejava
 * @since 2024-02-10 20:06:57
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("question")
public class QuestionController {
   private final QuestionService questionService;

   @ApiOperation("条件查询问题信息")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "current", value = "当前页"),
           @ApiImplicitParam(name = "size", value = "每页条数"),
           @ApiImplicitParam(name = "questionId", value = "问题id"),
           @ApiImplicitParam(name = "userId", value = "用户id"),
           @ApiImplicitParam(name = "status", value = "问题状态")
   })
   @GetMapping("/list")
   @PreAuthorize("hasAuthority('system:question:list')")
   public Object getQuestionByCondition(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                                        @RequestParam(value = "questionId", required = false) Long questionId,
                                        @RequestParam(value = "userId", required = false) Long userId,
                                        @RequestParam(value = "status", required = false) Integer status
                                        )
   {
     return questionService.getQuestionByCondition(current, size, questionId, userId, status);
   }

   @ApiOperation("添加问题信息")
   @PostMapping
   @PreAuthorize("hasAuthority('system:question:add')")
   public Result addQuestion(@RequestBody QuestionAddDTO questionAddDTO){
      Question question = BeanUtils.copyBean(questionAddDTO, Question.class).init();
      boolean save = questionService.save(question);
      return ResultUtils.add(save);
   }

   @ApiOperation("修改问题信息")
   @PutMapping
   @PreAuthorize("hasAuthority('system:question:update')")
   public Result updateQuestion(@RequestBody QuestionUpdateDTO questionUpdateDTO){
      Question question = BeanUtils.copyBean(questionUpdateDTO, Question.class).alter();
      boolean update = questionService.updateById(question);
      return ResultUtils.update(update);
   }

   @ApiOperation("删除问题信息")
   @PreAuthorize("hasAuthority('system:question:delete')")
   @ApiImplicitParam(name = "questionId", value = "问题id", required = true)
   @DeleteMapping("/{questionId}")
   public Result deleteQuestion(@PathVariable(value = "questionId") Long questionId){
      boolean delete = questionService.removeById(questionId);
      return ResultUtils.delete(delete);
   }

   @ApiOperation("批量删除")
   @PreAuthorize("hasAuthority('system:question:delete')")
   @ApiImplicitParam(name = "questionIds", value = "问题id", required = true)
   @DeleteMapping("/batchDelete")
   public Result deleteQuestions(@RequestParam("ids") List<Long> ids){
      boolean delete = questionService.removeByIds(ids);
      return ResultUtils.delete(delete);
   }
}

