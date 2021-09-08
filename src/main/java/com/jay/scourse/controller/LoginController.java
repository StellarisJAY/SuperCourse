package com.jay.scourse.controller;

import com.jay.scourse.service.IUserService;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * <p>
 * 登录控制器
 * </p>
 *
 * @author Jay
 * @date 2021/8/8
 **/
@RestController
@RequestMapping("/login")
public class LoginController {

    private final IUserService userService;

    @Autowired
    public LoginController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public CommonResult login(@RequestBody @Valid LoginVO loginVO, HttpServletResponse response){
        return userService.login(loginVO, response);
    }
}
