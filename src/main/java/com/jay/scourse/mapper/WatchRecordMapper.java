package com.jay.scourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jay.scourse.entity.WatchRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jay
 * @since 2021-08-27
 */
public interface WatchRecordMapper extends BaseMapper<WatchRecord> {

    /**
     * 更新观看记录
     * @param watchRecord 观看记录实体
     */
    @Update("UPDATE t_watch_record SET time=#{record.time}, finished=#{record.finished} WHERE user_id=#{record.userId} AND video_id=#{record.videoId}")
    void updateWatchRecord(@Param("record") WatchRecord watchRecord);

}
