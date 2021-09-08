package com.jay.scourse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jay
 * @since 2021-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_question")
@ToString
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题干
     */
    private String content;

    /**
     * 题目类型： 1单选 2多选 3填空
     */
    private Integer type;

    /**
     * A
     */
    private String selectionA;

    /**
     * B
     */
    private String selectionB;

    /**
     * C
     */
    private String selectionC;

    /**
     * D
     */
    private String selectionD;


}
