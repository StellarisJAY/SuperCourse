package com.jay.scourse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@TableName("t_course_chapter")
public class CourseChapter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 章节id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 章节名称
     */
    private String name;


}
