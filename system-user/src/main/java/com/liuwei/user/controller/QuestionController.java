package com.liuwei.user.controller;


import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Question;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.domain.dto.add.QuestionAddDTO;
import com.liuwei.user.domain.dto.update.QuestionUpdateDTO;
import com.liuwei.user.service.QuestionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

   //根据用户ID查询问题
   @ApiOperation("根据用户ID查询问题")
   @ApiImplicitParam(name = "userId", value = "用户id", required = true)
   @GetMapping("/userId/{userId}")
   @PreAuthorize("hasAuthority('front:question:list')")
   public Result getQuestionByUserId(@PathVariable(value = "userId") Long userId){
      return questionService.getQuestionByUserId(userId);
   }

   @ApiOperation("添加问题信息")
   @PreAuthorize("hasAuthority('front:question:add')")
   @PostMapping
   public Result addQuestion(@RequestBody QuestionAddDTO questionAddDTO){
      //todo 后续用户id要要从后台注入
      return questionService.addQuestion(questionAddDTO);
   }

   @ApiOperation("修改问题信息")
   @PreAuthorize("hasAuthority('front:question:update')")
   @PutMapping
   public Result updateQuestion(@RequestBody QuestionUpdateDTO questionUpdateDTO){
      Question question = BeanUtils.copyBean(questionUpdateDTO, Question.class).alter();
      boolean update = questionService.updateById(question);
      return ResultUtils.update(update);
   }

   @ApiOperation("删除问题信息")
   @PreAuthorize("hasAuthority('front:question:delete')")
   @ApiImplicitParam(name = "questionId", value = "问题id", required = true)
   @DeleteMapping("/{questionId}")
   public Result deleteQuestion(@PathVariable(value = "questionId") Long questionId){
      boolean delete = questionService.removeById(questionId);
      return ResultUtils.delete(delete);
   }
}

