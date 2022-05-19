package com.hcz.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcz.reggie.dto.SetmealDto;
import com.hcz.reggie.entity.Setmeal;
import com.hcz.reggie.entity.SetmealDish;
import com.hcz.reggie.mapper.SetmealMapper;
import com.hcz.reggie.service.SetmealDishService;
import com.hcz.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @title: SetmealServiceImpl
 * @Author Tan
 * @Date: 2022/4/27 9:04
 * @Version 1.0
 */

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    // 新增套餐，同时将套餐对应的菜品加入到setmeal_dish表中
    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        // 首先将套餐信息加入setmeal表中
        this.save(setmealDto);

        // 保存套餐对应的菜品到set_dish表中
        // 获取套餐id
        Long setMealId = setmealDto.getId();
        // 获取套餐对应的菜品信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item)->{
            item.setSetmealId(setMealId);
            return item;
        }).collect(Collectors.toList());

        // 保存菜品信息到set_meal表中
        setmealDishService.saveBatch(setmealDishes);
    }

    // 删除套餐信息，同时需要删除套餐对应的setmeal_dish里面的内容
    @Override
    public void deleteWithDish(Long setmealId) {
        // 构造条件构造器
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealId);
        // 删除setmeal_dish中对应的内容
        setmealDishService.remove(queryWrapper);

        // 删除setmeal的内容
        this.removeById(setmealId);
    }
}
