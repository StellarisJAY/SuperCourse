package com.jay.scourse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jay
 */
@SpringBootApplication
@MapperScan(basePackages = "com.jay.scourse.mapper")
public class SuperCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperCourseApplication.class, args);
    }

}
