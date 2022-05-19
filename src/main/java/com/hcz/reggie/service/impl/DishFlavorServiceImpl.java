package com.hcz.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcz.reggie.entity.DishFlavor;
import com.hcz.reggie.mapper.DishFlavorMapper;
import com.hcz.reggie.service.DishFlavorService;
import org.springframework.stereotype.Controller;

/**
 * @title: DishFlavorServiceImpl
 * @Author Tan
 * @Date: 2022/4/27 15:10
 * @Version 1.0
 */

@Controller
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
