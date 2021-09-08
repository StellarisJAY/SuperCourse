package com.jay.scourse.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jay.scourse.entity.User;
import com.jay.scourse.exception.GlobalException;
import com.jay.scourse.mapper.UserMapper;
import com.jay.scourse.service.IUserService;
import com.jay.scourse.util.CookieUtil;
import com.jay.scourse.util.Md5Util;
import com.jay.scourse.util.ValidatorUtil;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.CommonResultEnum;
import com.jay.scourse.vo.LoginVO;
import com.jay.scourse.vo.RegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jay
 * @since 2021-08-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public UserServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public User getUserByTicket(String ticket) {
        String key = "user-"+ticket;
        return (User)redisTemplate.opsForValue().get(key);
    }

    @Override
    public CommonResult login(LoginVO loginVO, HttpServletResponse response) {
        if(!ValidatorUtil.isMobileValidate(loginVO.getId())){
            throw new GlobalException(CommonResultEnum.LOGIN_ERROR);
        }
        User user = baseMapper.selectById(loginVO.getId());
        // 用户不存在
        if(user == null){
            throw new GlobalException(CommonResultEnum.USER_NOT_EXIST_ERROR);
        }
        // 验证密码
        String password = Md5Util.encodetodbpass(loginVO.getPassword(), user.getSalt());
        if(!password.equals(user.getPassword())){
            throw new GlobalException(CommonResultEnum.LOGIN_ERROR);
        }

        // 生成token
        String uuid = UUID.randomUUID().toString();
        // 存入cookie
        CookieUtil.setCookie(response, "userTicket", uuid);

        // redis记录用户信息，设置超时为2小时
        redisTemplate.opsForValue().set("user-"+uuid, user);
        redisTemplate.expire("user-" + uuid, 2, TimeUnit.HOURS);

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        return CommonResult.success(CommonResultEnum.SUCCESS, user.getUserType());
    }

    @Override
    public CommonResult register(RegisterVO registerVO) {
        // 验证输入格式
        if(!ValidatorUtil.isMobileValidate(registerVO.getId())
                || StringUtils.isEmpty(registerVO.getNickname()) || StringUtils.isEmpty(registerVO.getPassword())){
            throw new GlobalException(CommonResultEnum.BIND_ERROR);
        }
        User user = baseMapper.selectById(registerVO.getId());
        // 用户已存在
        if(user != null){
            throw new GlobalException(CommonResultEnum.USER_ALREADY_EXIST_ERROR);
        }

        user = new User();
        // 生成salt
        String salt = Md5Util.genSalt();
        // MD5加密密码
        String password = Md5Util.encodetodbpass(registerVO.getPassword(), salt);
        user.setId(registerVO.getId());
        user.setPassword(password);
        user.setCourseCount(0);
        user.setSalt(salt);
        user.setNickname(registerVO.getNickname());

        baseMapper.insert(user);

        return CommonResult.success(CommonResultEnum.SUCCESS, "注册成功", null);
    }
}
