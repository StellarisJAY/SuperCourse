package com.jay.scourse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jay.scourse.entity.Question;
import com.jay.scourse.entity.User;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.QuestionVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jay
 * @since 2021-08-29
 */
public interface IQuestionService extends IService<Question> {

    /**
     * 添加题目
     * @param user 用户
     * @param questionVO 题目实体
     * @return CommonResult
     */
    CommonResult addQuestion(User user, QuestionVO questionVO);

    /**
     * 批量上传题目
     * @param user 用户
     * @param questions 题目实体列表
     * @param collections 目标题目集id列表
     * @return CommonResult
     */
    CommonResult addQuestionBatch(User user, List<QuestionVO> questions, List<Long> collections);

    /**
     * 获取题目集题目列表
     * @param user 用户
     * @param collectionId cid
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return CommonResult
     */
    CommonResult getQuestionList(User user, Long collectionId, Integer pageNum, Integer pageSize);
}
