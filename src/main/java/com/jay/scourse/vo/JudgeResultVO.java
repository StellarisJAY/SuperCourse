package com.jay.scourse.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 判题结果vo
 * </p>
 *
 * @author Jay
 * @date 2021/9/15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeResultVO {
    private PracticeVO practiceInfo;

    private List<List<String>> userAnswers;

    private List<Integer> answerStatus;

    private List<Double> scores;

    private Double totalScore;

}
