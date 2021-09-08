package com.jay.scourse.vo;

import lombok.*;

import java.util.List;

/**
 * <p>
 * 题目文件vo
 * </p>
 *
 * @author Jay
 * @date 2021/8/31
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuestionFileVO {
    /**
     * 第一行作为field
     */
    private Boolean firstRowHeader;
    /**
     * 上传题目目标题集
     */
    private List<Long> collections;

}
