package com.jay.scourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jay.scourse.entity.Practice;
import com.jay.scourse.entity.Question;
import com.jay.scourse.vo.PracticeAnsweredVO;
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
 * @since 2021-08-28
 */
public interface PracticeMapper extends BaseMapper<Practice> {

    /**
     * 添加练习-题目关系
     * @param pid 练习id
     * @param qid 题目id
     * @param score 分数
     */
    @Insert("INSERT INTO t_practice_question VALUES(#{pid}, #{qid}, #{score})")
    void addPracticeQuestion(@Param("pid") Long pid, @Param("qid") Long qid, @Param("score") Double score);

    /**
     * 获取练习题目，不带答案
     * @param pid pid
     * @return List
     */
    @Select("SELECT q.* FROM t_practice_question pq INNER JOIN t_question q ON pq.qid=q.id WHERE pq.pid=#{pid}")
    List<Question> getQuestionsNoAnswer(@Param("pid") Long pid);

    /**
     * 获取练习题目分数
     * @param pid pid
     * @return List
     */
    @Select("SELECT pq.score FROM t_practice_question pq WHERE pq.pid=#{pid} ")
    List<Double> getQuestionScores(@Param("pid") Long pid);

    /**
     * 查询练习
     * @param pid 练习id
     * @return PracticeVO
     */
    @Select("SELECT p.* FROM t_practice p WHERE p.id=#{pid}")
    PracticeAnsweredVO getPracticeAnsweredVO(@Param("pid") Long pid);
}
