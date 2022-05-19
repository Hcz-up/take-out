package com.hcz.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcz.reggie.entity.ShoppingCart;
import com.hcz.reggie.mapper.ShoppingCartMapper;
import com.hcz.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Controller;

/**
 * @title: ShoppingCartServiceImpl
 * @Author Tan
 * @Date: 2022/5/7 9:30
 * @Version 1.0
 */
@Controller
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
