package com.hcz.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hcz.reggie.dto.DishDto;
import com.hcz.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    // 新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish  dish_flavor
    void saveWithFlavor(DishDto dishDto);

    // 根据id查询菜品信息和对应的口味信息
    DishDto getByIdWithFlavor(Long id);

    // 更新菜品，同时更新菜品对应的口味数据，需要操作两张表：dish  dish_flavor
    void updateWithFlavor(DishDto dishDto);
}
