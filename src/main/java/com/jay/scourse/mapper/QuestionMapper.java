package com.jay.scourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jay.scourse.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jay
 * @since 2021-08-29
 */
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 保存题目-题集关系
     * @param qid qid
     * @param cid cid
     * @return int
     */
    @Insert("INSERT INTO t_collection_question VALUES(#{qid}, #{cid})")
    Integer saveCollectionQuestion(@Param("qid") Long qid, @Param("cid") Long cid);

    /**
     * 添加题目答案
     * @param qid qid
     * @param answer 答案 A1 B2 C3 D4 填空题为字符串
     * @return int
     */
    @Insert("INSERT INTO t_answer VALUES(#{qid}, #{answer})")
    Integer saveAnswer(@Param("qid")Long qid, @Param("answer") String answer);

    /**
     * 查询cid题目集的所有题目id
     * @param cid 题目集id
     * @return List
     */
    @Select("SELECT qid FROM t_collection_question WHERE cid=#{cid}")
    List<Long> getCollectionQuestions(@Param("cid") Long cid);

    /**
     * 查询题目答案
     * @param qid 题目id
     * @return List
     */
    @Select("SELECT answer FROM t_answer WHERE qid=#{qid}")
    List<String> getAnswer(@Param("qid") Long qid);
}
