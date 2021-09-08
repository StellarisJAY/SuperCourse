package com.jay.scourse.vo;

import com.jay.scourse.entity.Practice;
import com.jay.scourse.entity.Question;
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
@NoArgsConstructor
@AllArgsConstructor
public class PracticeVO extends Practice {
    private List<Question> questions;
    private List<Double> scores;
}
