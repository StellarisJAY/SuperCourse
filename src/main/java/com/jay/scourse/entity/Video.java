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
 * @since 2021-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_video")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 视频id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 视频点播vid
     */
    private String vid;

    /**
     * 封面url
     */
    private String coverUrl;

    private String title;

    /**
     * 视频所属章节id
     */
    private Long chapterId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 时长，单位s
     */
    private Integer duration;


}
