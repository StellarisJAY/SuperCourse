package com.jay.scourse.config;

import com.aliyuncs.utils.StringUtils;
import com.jay.scourse.entity.User;
import com.jay.scourse.exception.GlobalException;
import com.jay.scourse.service.IUserService;
import com.jay.scourse.util.CookieUtil;
import com.jay.scourse.vo.CommonResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户信息解析器
 * </p>
 *
 * @author Jay
 * @date 2021/8/8
 **/
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final IUserService userService;

    @Autowired
    public UserArgumentResolver(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)  {
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        String userTicket = CookieUtil.getCookie(request, "userTicket");
        if(!StringUtils.isEmpty(userTicket)){
            User user =  userService.getUserByTicket(userTicket);
            if(user != null){
                return user;
            }
        }
        throw new GlobalException(CommonResultEnum.INVALID_LOGIN_ERROR);
    }
}
