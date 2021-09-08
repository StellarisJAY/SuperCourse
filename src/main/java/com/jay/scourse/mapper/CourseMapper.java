package com.jay.scourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jay.scourse.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jay
 * @since 2021-08-24
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 更新课程状态
     * @param courseId 课程id
     * @param status 状态值
     */
    @Update("UPDATE t_course SET status=#{status} WHERE id=#{courseId}")
    void updateCourseStatus(@Param("courseId") Long courseId, @Param("status") Integer status);
}
