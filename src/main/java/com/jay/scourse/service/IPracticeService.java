package com.jay.scourse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jay.scourse.entity.Practice;
import com.jay.scourse.entity.User;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.NewPracticeVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jay
 * @since 2021-08-28
 */
public interface IPracticeService extends IService<Practice> {

    /**
     * 添加练习
     * @param user 用户
     * @param practiceVO 练习实体
     * @return CommonResult
     */
    CommonResult addPractice(User user, NewPracticeVO practiceVO);

    /**
     * 获取学生用户的练习实体
     * @param user 用户
     * @param practiceId 练习id
     * @return CommonResult
     */
    CommonResult getPracticeForStudent(User user, Long practiceId);
}
