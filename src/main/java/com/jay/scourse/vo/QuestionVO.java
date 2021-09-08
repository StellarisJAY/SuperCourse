package com.jay.scourse.vo;

import com.jay.scourse.entity.Question;
import lombok.*;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Jay
 * @date 2021/8/29
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVO extends Question {

    /**
     * 答案列表
     */
    private List<String> answers;

    /**
     * 所属题目集id
     */
    private Long collectionId;

    @Override
    public String toString() {
        return super.toString() +
                "answers=" + answers +
                ", collectionId=" + collectionId +
                '}';
    }
}
