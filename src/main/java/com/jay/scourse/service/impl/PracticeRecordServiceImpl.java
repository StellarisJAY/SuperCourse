package com.jay.scourse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jay.scourse.common.CacheKey;
import com.jay.scourse.entity.PracticeRecord;
import com.jay.scourse.entity.User;
import com.jay.scourse.mapper.PracticeRecordMapper;
import com.jay.scourse.service.IPracticeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jay
 * @since 2021-09-08
 */
@Service
public class PracticeRecordServiceImpl extends ServiceImpl<PracticeRecordMapper, PracticeRecord> implements IPracticeRecordService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public PracticeRecordServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public PracticeRecord getPracticeRecord(User user, Long practiceId) {
        PracticeRecord practiceRecord = (PracticeRecord)redisTemplate.opsForValue().get(CacheKey.STUDENT_PRACTICE_RECORD + user.getId() + ":" + practiceId);
        if(practiceRecord == null){
            practiceRecord = baseMapper.selectByIds(user.getId(), practiceId);
            redisTemplate.opsForValue().set(CacheKey.STUDENT_PRACTICE_RECORD + user.getId() + ":" + practiceId, practiceRecord);
        }
        return practiceRecord;
    }
}
