package com.jay.scourse.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * cookie工具类
 * </p>
 *
 * @author Jay
 * @date 2021/8/8
 **/
public class CookieUtil {

    public static String getCookie(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0){
            return null;
        }
        for(Cookie cookie : cookies){
            if(name.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void setCookie(HttpServletResponse response, String name, String value){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 24 * 60);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
