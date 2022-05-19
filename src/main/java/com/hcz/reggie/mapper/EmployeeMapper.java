package com.hcz.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcz.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @title: EmployeeMapper
 * @Author Tan
 * @Date: 2022/4/23 14:43
 * @Version 1.0
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
