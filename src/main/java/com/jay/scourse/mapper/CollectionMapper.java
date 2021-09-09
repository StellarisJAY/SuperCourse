package com.jay.scourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jay.scourse.entity.Collection;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jay
 * @since 2021-08-28
 */
public interface CollectionMapper extends BaseMapper<Collection> {

    /**
     * 获取题目集题目数量
     * @param collectionId 题集id
     * @return Integer
     */
    @Select("SELECT COUNT(*) FROM t_collection_question WHERE cid=#{collectionId}")
    Integer getQuestionCount(@Param("collectionId") Long collectionId);

    /**
     * 添加题集-题目关系
     * @param collectionId 题目集id
     * @param questionId 题目id
     * @param type 题目类型 1 选择 2填空
     * @return int
     */
    @Insert("INSERT INTO t_collection_question VALUES(#{qid}, #{cid}, #{type})")
    Integer addCollectionQuestion(@Param("cid") Long collectionId, @Param("qid") Long questionId, @Param("type") Integer type);
}
