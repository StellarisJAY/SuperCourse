package com.jay.scourse.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jay
 */
public class ValidatorUtil {
    private static final Pattern MOBILE_PATTERN = Pattern.compile("[1]([3-9])[0-9]{9}$");

    @SuppressWarnings("deprecated")
    public static boolean isMobileValidate(String number){
        if(StringUtils.isEmpty(number)){
            return false;
        }
        Matcher matcher = MOBILE_PATTERN.matcher(number);
        return matcher.matches();
    }
}
