package com.jay.scourse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jay.scourse.entity.CourseChapter;
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
public interface ICourseChapterService extends IService<CourseChapter> {

    /**
     * 获取课程的所有章节信息
     * @param user 用户
     * @param courseId 课程id
     * @return CommonResult
     */
    public CommonResult getCourseChapters(User user, Long courseId);

    /**
     * 添加章节
     * @param user 用户
     * @param chapter 章节实体
     * @return CommonResult
     */
    public CommonResult addChapter(User user, CourseChapter chapter);

    /**
     * 删除章节
     * @param user 用户
     * @param chapter 章节实体
     * @return CommonResult
     */
    public CommonResult deleteChapter(User user, CourseChapter chapter);
}
