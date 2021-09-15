package com.jay.scourse.service;


import com.jay.scourse.entity.User;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.PracticeAnsweredVO;

/**
 * @author Jay
 */
public interface IJudgeService {
    /**
     * 判题
     * @param user user
     * @param practiceVO 提交答案
     * @return CommonResult
     */
    CommonResult judge(User user, PracticeAnsweredVO practiceVO);
}
