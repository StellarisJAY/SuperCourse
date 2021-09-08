package com.jay.scourse.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

/**
 * MD5 工具类
 * @author Jay
 */
public class Md5Util {

    private static final int SALT_LENGTH = 10;

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    public static String encodetodbpass(String pass, String salt){
        String str = salt.charAt(0) + salt.charAt(3) + pass + salt.charAt(1) + salt.charAt(2);
        return DigestUtils.md5Hex(str);
    }

    /**
     * 随机生成10位字符的salt
     * @return string
     */
    public static String genSalt(){
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder salt = new StringBuilder();
        for(int i = 0; i < SALT_LENGTH; i++){
            salt.append((char)('z' - rand.nextInt(26)));
        }
        return salt.toString();
    }

}
