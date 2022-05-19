package com.hcz.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcz.reggie.entity.Category;
import com.hcz.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
