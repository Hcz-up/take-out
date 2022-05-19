package com.hcz.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hcz.reggie.entity.Category;
import com.hcz.reggie.entity.Employee;

public interface CategoryService  extends IService<Category> {
    public void remove(Long id);
}
