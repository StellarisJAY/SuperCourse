package com.jay.scourse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jay.scourse.entity.Course;
import com.jay.scourse.entity.User;
import com.jay.scourse.vo.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jay
 * @since 2021-08-24
 */
public interface ICourseService extends IService<Course> {
    /**
     * 获取教师个人的课程列表
     * @param teacherId 教师id
     * @return CommonResult
     */
    CommonResult teacherCourseList(String teacherId);

    /**
     * 获取课程信息
     * @param id id
     * @return Course
     */
    Course getCourseInfo(Long id);

    /**
     * 添加课程
     * @param course 课程实体
     * @param user 用户
     * @return CommonResult
     */
    CommonResult addCourse(Course course, User user);

    /**
     * 更新课程信息
     * @param course 课程实体
     * @param user 用户
     * @return CommonResult
     */
    CommonResult updateCourse(Course course, User user);

    /**
     * 获取学生学习进度
     * @param user 用户
     * @param courseId 课程id
     * @return CommonResult
     */
    CommonResult getStudyProgress(User user, Long courseId);
}
