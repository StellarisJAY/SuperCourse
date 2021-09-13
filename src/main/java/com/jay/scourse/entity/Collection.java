package com.jay.scourse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jay
 * @since 2021-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_collection")
public class Collection implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    @TableField(jdbcType = JdbcType.VARCHAR)
    private String name;

    /**
     * 教师id
     */
    private String teacherId;

    /**
     * 私人题集 1 true  0 false
     */
    private Integer privateCollection;


}
