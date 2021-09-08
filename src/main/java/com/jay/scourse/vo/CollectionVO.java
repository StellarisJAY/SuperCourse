package com.jay.scourse.vo;

import com.jay.scourse.entity.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 题目集vo
 * </p>
 *
 * @author Jay
 * @date 2021/8/28
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class CollectionVO extends Collection {

    /**
     * 题目总数
     */
    private Integer totalQuestion;

    public CollectionVO(Collection collection, Integer totalQuestion) {
        this.totalQuestion = totalQuestion;
        this.setId(collection.getId());
        this.setName(collection.getName());
        this.setTeacherId(collection.getTeacherId());
        this.setPrivateCollection(collection.getPrivateCollection());
    }
}
