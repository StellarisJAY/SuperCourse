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
 * @since 2021-09-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_practice_record")
public class PracticeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 练习id
     */
    private Long pid;

    /**
     * 用时，单位s
     */
    private Integer timeUsed;

    /**
     * 提交时间
     */
    private LocalDateTime finishTime;

    /**
     * 得分
     */
    private Float score;


}
