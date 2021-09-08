package com.jay.scourse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jay.scourse.entity.User;
import com.jay.scourse.entity.WatchRecord;
import com.jay.scourse.mapper.WatchRecordMapper;
import com.jay.scourse.service.IWatchRecordService;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.CommonResultEnum;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jay
 * @since 2021-08-27
 */
@Service
public class WatchRecordServiceImpl extends ServiceImpl<WatchRecordMapper, WatchRecord> implements IWatchRecordService {

    @Override
    public CommonResult addWatchRecord(User user, WatchRecord watchRecord) {
        // 设置观看记录的用户id
        watchRecord.setUserId(user.getId());


        Integer count = query().eq("user_id", user.getId()).eq("video_id", watchRecord.getVideoId()).count();
        if(count == null || count == 0){
            save(watchRecord);
        }
        else{
            baseMapper.updateWatchRecord(watchRecord);
        }
        return CommonResult.success(CommonResultEnum.INSERT_SUCCESS, watchRecord);
    }
}
