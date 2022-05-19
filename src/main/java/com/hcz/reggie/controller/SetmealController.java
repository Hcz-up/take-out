package com.hcz.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcz.reggie.common.R;
import com.hcz.reggie.dto.SetmealDto;
import com.hcz.reggie.entity.Category;
import com.hcz.reggie.entity.Setmeal;
import com.hcz.reggie.service.CategoryService;
import com.hcz.reggie.service.SetmealDishService;
import com.hcz.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @title: SetmealController
 * @Author Tan
 * @Date: 2022/4/28 10:50
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;


    /**
     * 添加套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
//        log.info(setmealDto.toString());
        setmealService.saveWithDish(setmealDto);
        return R.success("套餐添加成功");
    }

    /**
     * 分页查询套餐信息
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> getPage(int page, int pageSize, String name){
        // 构造分页查询构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>(page, pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(name), Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        // 执行查询
        setmealService.page(pageInfo, queryWrapper);

        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            // 获取当前套餐的类别id
            Long categoryId = item.getCategoryId();
            // 在category中查询对应的名字
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            setmealDto.setCategoryName(categoryName);
            return setmealDto;
        }).collect(Collectors.toList());

        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "setmealDishes");
        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }


    /**
     * 根据套餐ids删除套餐信息
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        setmealService.deleteWithDish(ids);
        return R.success("删除套餐成功");
    }


    /**
     * 根据条件查询套餐数据
     * @param categoryId
     * @param status
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(String categoryId, Integer status){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Strings.isNotEmpty(categoryId), Setmeal::getCategoryId, categoryId);
        queryWrapper.eq(status != null, Setmeal::getStatus, status);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> setmealList = setmealService.list(queryWrapper);

        return R.success(setmealList);
    }
}
