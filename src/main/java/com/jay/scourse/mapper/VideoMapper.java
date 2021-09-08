package com.jay.scourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jay.scourse.entity.Video;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jay
 * @since 2021-08-24
 */
public interface VideoMapper extends BaseMapper<Video> {

    /**
     * 查询课程拥有的视频总数
     * @param courseId 课程id
     * @return int
     */
    @Select("SELECT COUNT(*) FROM t_video WHERE course_id=#{courseId}")
    Integer getCourseVideoCount(@Param("courseId") Long courseId);
}
