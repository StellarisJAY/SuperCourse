package com.jay.scourse.controller;

import com.jay.scourse.service.IUserService;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.RegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 注册控制器
 * </p>
 *
 * @author Jay
 * @date 2021/8/8
 **/
@RestController
@RequestMapping("/register")
public class RegisterController {

    private final IUserService userService;
    @Autowired
    public RegisterController(IUserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public CommonResult register(@RequestBody @Valid RegisterVO registerVO){
        return userService.register(registerVO);
    }
}
