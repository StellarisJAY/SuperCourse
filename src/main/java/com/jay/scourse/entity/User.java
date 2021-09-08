package com.jay.scourse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jay
 * @since 2021-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 11位手机号id
     */
    private String id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 两次md5加密后的密码
     */
    private String password;

    /**
     * md5 加密 salt
     */
    private String salt;

    /**
     * 头像链接
     */
    private String head;

    /**
     * 个人简介
     */
    private String description;

    /**
     * 学习总时长，单位min
     */
    private Long studyTime;

    /**
     * 注册时间
     */
    private LocalDateTime registerTime;

    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 学生 0，老师 1
     */
    private Integer userType;

    /**
     * 学生：关注课程数量，老师：发布课程数量
     */
    private Integer courseCount;


}
