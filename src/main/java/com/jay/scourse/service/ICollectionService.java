package com.jay.scourse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jay.scourse.entity.Collection;
import com.jay.scourse.entity.User;
import com.jay.scourse.vo.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jay
 * @since 2021-08-28
 */
public interface ICollectionService extends IService<Collection> {

    /**
     * 获取题集列表
     * @param user 用户
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return CommonResult
     */
    CommonResult getCollectionList(User user, Integer pageNum, Integer pageSize);

    /**
     * 添加题目-题集关系
     * @param qid 题目id
     * @param cid 题集id
     * @param type 题目类型
     * @return int
     */
    Integer addCollectionQuestion(Long qid, Long cid, Integer type);
}
