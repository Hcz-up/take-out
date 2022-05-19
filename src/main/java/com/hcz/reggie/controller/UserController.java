package com.hcz.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hcz.reggie.common.R;
import com.hcz.reggie.entity.User;
import com.hcz.reggie.service.UserService;
import com.hcz.reggie.utils.SMSUtils;
import com.hcz.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @title: UserController
 * @Author Tan
 * @Date: 2022/5/6 9:50
 * @Version 1.0
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 发送验证码
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        // 获取手机号
        String phone = user.getPhone();

        if(!StringUtils.isNotEmpty(phone))
            return R.error("手机号不能为空");

        // 生成4位的验证码
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        // 调用阿里云提供的短信服务API发送短信
        SMSUtils.sendMessage("阿里云短信测试", "SMS_154950909", phone, code);
        // 需要将生成的验证码保存到session
        session.setAttribute(phone, code);
        log.info("验证码"+code);
        return R.success("手机验证码短信发送成功");
    }


    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        // 获取手机号
        String phone = map.get("phone").toString();
        // 获取验证码
        String code = map.get("code").toString();

        // 从Session中获取保存的验证码
        Object sessionCode = session.getAttribute(phone);

        // 验证码比对
        if(sessionCode != null && sessionCode.equals(code)){
            // 判断当前手机号对应的用户是不是新用户，如果是则保存
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotEmpty(phone), User::getPhone, phone);
            User user = userService.getOne(queryWrapper);

            if(user == null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return R.success(user);
        }

        return R.error("登录失败");
    }
}
