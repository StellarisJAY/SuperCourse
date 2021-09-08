package com.jay.scourse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jay.scourse.entity.User;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.LoginVO;
import com.jay.scourse.vo.RegisterVO;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jay
 * @since 2021-08-08
 */
public interface IUserService extends IService<User> {

    /**
     * 通过前端凭证获取用户信息
     * @param ticket userTicket
     * @return User
     */
    User getUserByTicket(String ticket);

    /**
     * 登录
     * @param loginVO 请求实体
     * @param response response
     * @return CommonResult
     */
    CommonResult login(LoginVO loginVO, HttpServletResponse response);

    /**
     * 注册
     * @param registerVO 注册请求实体
     * @return CommonResult
     */
    CommonResult register(RegisterVO registerVO);
}
