package com.jay.scourse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jay.scourse.entity.PracticeRecord;
import com.jay.scourse.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jay
 * @since 2021-09-08
 */
public interface IPracticeRecordService extends IService<PracticeRecord> {
    /**
     * 获取用户练习记录
     * @param user user
     * @param practiceId 练习id
     * @return PracticeRecord
     */
    PracticeRecord getPracticeRecord(User user, Long practiceId);
}
