package com.hcz.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcz.reggie.common.R;
import com.hcz.reggie.dto.DishDto;
import com.hcz.reggie.entity.Category;
import com.hcz.reggie.entity.Dish;
import com.hcz.reggie.entity.DishFlavor;
import com.hcz.reggie.service.CategoryService;
import com.hcz.reggie.service.DishFlavorService;
import com.hcz.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @title: DishController
 * @Author Tan
 * @Date: 2022/4/27 9:27
 * @Version 1.0
 */

/**
 * 菜品管理
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;


    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }


    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page<DishDto>> getPage(int page, int pageSize, String name){
        // 构造分页查询构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>(page, pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Strings.isNotEmpty(name), Dish::getName, name);
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);

        // 执行查询
        dishService.page(pageInfo, lambdaQueryWrapper);

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());

        BeanUtils.copyProperties(pageInfo, dishDtoPage, "flavors");
        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }


    /**
     * 修改菜品信息
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品信息成功");
    }


    /**
     * 根据categoryId或者菜品名name查询菜品
     * @param categoryId
     * @return
     */
//    @GetMapping("/list")
//    public R<List<Dish>> getByCategoryId(Long categoryId, String name){
////        log.info("" + categoryId);
////        log.info(name);
//
//        // 构造条件查询器
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        // 设置查询条件
//        queryWrapper.eq(categoryId!=null ,Dish::getCategoryId, categoryId);
//        queryWrapper.like(Strings.isNotEmpty(name), Dish::getName, name);
//        // 执行查询操作
//        List<Dish> dishList = dishService.list(queryWrapper);
//        return R.success(dishList);
//    }
    @GetMapping("/list")
    public R<List<DishDto>> getByCategoryId(Long categoryId, String name, Integer status){
//        log.info("" + categoryId);
//        log.info(name);

        // 构造条件查询器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        // 设置查询条件
        queryWrapper.eq(categoryId!=null ,Dish::getCategoryId, categoryId);
        queryWrapper.like(Strings.isNotEmpty(name), Dish::getName, name);
        queryWrapper.eq(status != null, Dish::getStatus, status);
        // 执行查询操作
        List<Dish> dishList = dishService.list(queryWrapper);

        List<DishDto> dishDtoList = dishList.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            // 获得dish的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);

            dishDto.setFlavors(dishFlavorList);

            return dishDto;
        }).collect(Collectors.toList());


        return R.success(dishDtoList);
    }

}
