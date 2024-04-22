package com.liuwei.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuwei.admin.domain.dto.add.AnimalAddDTO;
import com.liuwei.admin.domain.dto.update.AnimalUpdateDTO;
import com.liuwei.admin.service.AnimalService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Animal;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animal")
public class AnimalController {
    private final AnimalService animalService;
    @ApiOperation("动物条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页条数"),
            @ApiImplicitParam(name = "animalId", value = "动物id"),
            @ApiImplicitParam(name = "name", value = "动物名称"),
            @ApiImplicitParam(name = "status", value = "动物状态")
    })
    @PreAuthorize("hasAuthority('system:animal:list')")
    @GetMapping("/list")
    public Result getAnimalByCondition(@RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "animalId", required = false) Long animalId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status
            ) {
        return animalService.getAnimalByCondition(current, size, animalId, name, status);
    }



    @ApiOperation("添加动物")
    @PreAuthorize("hasAuthority('system:animal:add')")
    @PostMapping
    public Result addAnimal(@RequestBody AnimalAddDTO animalAddDTO) {
        Animal animal = BeanUtils.copyBean(animalAddDTO, Animal.class).init();
        boolean save = animalService.save(animal);
        return ResultUtils.add(save);
    }

    @ApiOperation("修改动物")
    @PutMapping
    @PreAuthorize("hasAuthority('system:animal:update')")
    public Result updateAnimal(@RequestBody AnimalUpdateDTO animalUpdateDTO) {
        Animal animal = BeanUtils.copyBean(animalUpdateDTO, Animal.class).alter();
        boolean update = animalService.updateById(animal);
        return ResultUtils.update(update);
    }

    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "animalId", value = "动物id", required = true)
    @DeleteMapping("/{animalId}")
    @PreAuthorize("hasAuthority('system:animal:delete')")
    public Result deleteAnimal(@PathVariable(value = "animalId") Long animalId) {
        boolean delete = animalService.removeById(animalId);
        return ResultUtils.delete(delete);
    }

    @ApiOperation("批量删除")
    @ApiImplicitParam(name = "animalIds", value = "动物id", required = true)
    @DeleteMapping("/batchDelete")
    @Transactional
    @PreAuthorize("hasAuthority('system:animal:delete')")
    public Result deleteAnimals(@RequestParam("ids") List<Long> ids) {
        LambdaQueryWrapper<Animal> animalQueryWrapper = new LambdaQueryWrapper<>();
        animalQueryWrapper.in(Animal::getAnimalId, ids)
                .select(Animal::getImageId);
        boolean delete = animalService.removeByIds(ids);
        return ResultUtils.delete(delete);
    }
}
