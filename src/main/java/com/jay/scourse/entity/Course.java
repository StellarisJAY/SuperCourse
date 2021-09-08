package com.jay.scourse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程名
     */
    @NotNull
    @Length(min = 4, max = 255)
    private String name;

    /**
     * 课程图片
     */
    private String image;

    /**
     * 老师id
     */
    private String teacherId;

    /**
     * 冗余字段：老师姓名
     */
    private String teacherName;

    /**
     * 状态，0未开始、1进行中、2已结束
     */
    private Integer status;

    /**
     * 价格，0.00为免费课程
     */
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    /**
     * 开始时间
     */
    @FutureOrPresent
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间，null表示永不结束
     */
    @FutureOrPresent
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

}
