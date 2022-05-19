package com.hcz.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcz.reggie.entity.AddressBook;
import com.hcz.reggie.mapper.AddressBookMapper;
import com.hcz.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @title: AddressBookServiceImpl
 * @Author Tan
 * @Date: 2022/5/7 7:56
 * @Version 1.0
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
