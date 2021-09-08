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
@TableName("t_watch_record")
public class WatchRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 观看记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 观看到时间
     */
    private Integer time;

    /**
     * 是否完成
     */
    private Integer finished;


}
