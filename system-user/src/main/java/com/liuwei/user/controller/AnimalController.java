package com.liuwei.user.controller;

import com.liuwei.framework.domain.Result;
import com.liuwei.user.service.AnimalService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animal")
public class AnimalController {
    private final AnimalService animalService;

    @GetMapping("/page")
    @ApiOperation("查询所有猫咪")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示条数", defaultValue = "10")
    })
    public Result getAllAnimal(@RequestParam(value = "current", defaultValue = "1") Integer current,
                               @RequestParam(value = "size", defaultValue = "10") Integer size

    ) {
        return animalService.getAllAnimal(current, size);
    }

    //根据猫咪名模糊查询猫咪信息
    @GetMapping("/name")
    @ApiOperation("根据猫咪名模糊查询猫咪信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "猫咪名", required = true),
            @ApiImplicitParam(name = "current", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示条数", defaultValue = "10")
    })
    public Result getAnimalByName(@RequestParam(value = "name") String name,
                                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return animalService.getAnimalByName(name, current, size);
    }

    //根据猫咪id查询猫咪信息
    @GetMapping("/id")
    @ApiOperation("根据猫咪id查询猫咪信息")
    @ApiImplicitParam(name = "animalId", value = "猫咪id", required = true)
    public Result getAnimalById(@PathVariable(name = "animalId") Long animalId) {
        return animalService.getAnimalById(animalId);
    }

    //根据种类查询猫咪信息
    @GetMapping("/category")
    @ApiOperation("根据种类查询猫咪信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "种类id", required = true),
            @ApiImplicitParam(name = "current", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示条数", defaultValue = "10")
    })
    public Result getAnimalByCategoryId(@RequestParam(value = "current", defaultValue = "1") Integer current,
    @RequestParam(value = "size", defaultValue = "10") Integer size,
    @RequestParam(value = "categoryId") Long categoryId
    ) {
        return animalService.getAnimalByCategoryId(current, size, categoryId);
    }


}
