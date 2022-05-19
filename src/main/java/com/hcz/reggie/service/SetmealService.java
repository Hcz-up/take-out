package com.hcz.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hcz.reggie.dto.SetmealDto;
import com.hcz.reggie.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {

    // 新增套餐，同时将套餐对应的菜品加入到setmeal_dish表中
    void saveWithDish(SetmealDto setmealDto);

    // 删除套餐信息，同时需要删除套餐对应的setmeal_dish里面的内容
    void deleteWithDish(Long setmealId);
}
