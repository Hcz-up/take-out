package com.hcz.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcz.reggie.common.CustomException;
import com.hcz.reggie.entity.Category;
import com.hcz.reggie.entity.Dish;
import com.hcz.reggie.entity.Setmeal;
import com.hcz.reggie.mapper.CategoryMapper;
import com.hcz.reggie.service.CategoryService;
import com.hcz.reggie.service.DishService;
import com.hcz.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @title: CategoryServiceImpl
 * @Author Tan
 * @Date: 2022/4/26 11:51
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;



    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        // 设置查询条件
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        // 查询当前分类是否关联了菜品，如果有，则抛出业务异常
        if(count > 0 ){
            // 抛出异常
            throw new CustomException("当前分类关联了菜品，不能删除");
        }

        // 设置查询条件
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        // 查询当前分类是否关联了套餐，如果有，则抛出业务异常
        if(count1 > 0){
            // 抛出异常
            throw new CustomException("当前分类关联了套餐，不能删除");
        }

        // 如果都没有，执行删除
        super.removeById(id);
    }
}
