package com.hcz.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcz.reggie.entity.User;
import com.hcz.reggie.mapper.UserMapper;
import com.hcz.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @title: UserServiceImpl
 * @Author Tan
 * @Date: 2022/5/6 9:48
 * @Version 1.0
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
