package com.jay.scourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jay.scourse.entity.PracticeRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jay
 * @since 2021-09-08
 */
public interface PracticeRecordMapper extends BaseMapper<PracticeRecord> {
    /**
     * 通过uid、pid 获取练习记录
     * @param uid uid
     * @param pid pid
     * @return PracticeRecord
     */
    @Select("SELECT * FROM t_practice_record WHERE uid=#{uid} AND pid=#{pid}")
    PracticeRecord selectByIds(@Param("uid") String uid, @Param("pid") Long pid);
}
