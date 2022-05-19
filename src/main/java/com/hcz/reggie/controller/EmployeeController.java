package com.hcz.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcz.reggie.common.BaseContext;
import com.hcz.reggie.common.R;
import com.hcz.reggie.entity.Employee;
import com.hcz.reggie.service.EmployeeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @title: EmployeeController
 * @Author Tan
 * @Date: 2022/4/23 14:49
 * @Version 1.0
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 用户登录操作
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R login(HttpServletRequest request, @RequestBody Employee employee){
        // 1、将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2、根据页面提交的username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        // 3、如果没有查询到则返回 用户名不存在结果
        if(emp == null){
            return R.error("用户名不存在");
        }

        // 4、密码比对，密码错误则返回 密码错误结果
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }

        // 5、查看员工状态，如果员工状态为禁用，则返回 账号已禁用结果
        if(emp.getStatus() == 0){
            return R.error("该账户已被禁用");
        }

        // 6、登录成功，将员工id存入Session并返回 登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 用户退出操作
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 保存用户操作
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){

        // Employee(id=null, username=zhangsan, name=张三, password=null, phone=18222222222, sex=1,
        // idNumber=531124142902013229, status=null, createTime=null, updateTime=null, createUser=null, updateUser=null)

        // 设置默认密码，使用md5加密
        String password = "123456";
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        employee.setPassword(password);
//        // 设置其他字段信息
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        // 获取当前登录用户的id
//        Long empId = (Long)request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    /**
     * 分页查询
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> getPage(int page, int pageSize, String name){
        // 构造分页构造器
        Page<Employee> pageInfo = new Page(page, pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // 执行查询
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));

        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    /**
     * 根据id查员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable long id){
        Employee employee = employeeService.getById(id);

        if(employee != null)
            return R.success(employee);

        return R.error("没有查询到对应员工信息");
    }
}
