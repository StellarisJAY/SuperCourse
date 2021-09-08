package com.jay.scourse.vo;

import com.jay.scourse.entity.Practice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Jay
 * @date 2021/9/8
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPracticeVO extends Practice {
    /**
     * 新练习的题目id
     */
    private List<Long> questions;

    /**
     * 每道题目的分数
     */
    private List<Double> scores;
}
