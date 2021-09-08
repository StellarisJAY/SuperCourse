package com.jay.scourse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <p>
 *
 * </p>
 *
 * @author Jay
 * @date 2021/9/8
 **/
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.jay");
        User user = applicationContext.getBean("user", User.class);

    }
}
