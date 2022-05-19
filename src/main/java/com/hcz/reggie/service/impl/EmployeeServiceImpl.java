package com.hcz.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcz.reggie.entity.Employee;
import com.hcz.reggie.mapper.EmployeeMapper;
import com.hcz.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @title: EmployeeServiceImpl
 * @Author Tan
 * @Date: 2022/4/23 14:44
 * @Version 1.0
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
