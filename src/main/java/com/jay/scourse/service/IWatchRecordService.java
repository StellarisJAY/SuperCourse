package com.jay.scourse.service;

import com.jay.scourse.entity.User;
import com.jay.scourse.entity.WatchRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jay.scourse.vo.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jay
 * @since 2021-08-27
 */
public interface IWatchRecordService extends IService<WatchRecord> {

    /**
     * 保存观看记录
     * @param user 用户id
     * @param watchRecord 观看记录实体
     * @return CommonResult
     */
    CommonResult addWatchRecord(User user, WatchRecord watchRecord);
}
