package com.jay.scourse.vo;

import com.jay.scourse.entity.Course;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程列表，课程vo
 * </p>
 *
 * @author Jay
 * @date 2021/8/25
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseVO extends Course {
    /**
     * 关注人数
     */
    private Integer subscribeCount;

    public CourseVO(Course course){
        super(course.getId(), course.getName(), course.getImage(), course.getTeacherId(), course.getTeacherName(),
                course.getStatus(), course.getPrice(), course.getStartTime(),course.getEndTime());
    }

}
