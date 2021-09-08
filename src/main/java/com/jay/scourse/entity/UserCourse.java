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
 * @since 2021-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user_course")
public class UserCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生id
     */
    private String userId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 学习时长
     */
    private Long studyTime;

    /**
     * 购买时间
     */
    private LocalDateTime purchaseTime;

    /**
     * 已学习章节数
     */
    private Integer watchedChapterCount;

    /**
     * 上次学习章节id
     */
    private Long lastWatchedChapter;


}
