package com.jay.scourse.controller;


import com.jay.scourse.entity.Course;
import com.jay.scourse.entity.User;
import com.jay.scourse.entity.UserType;
import com.jay.scourse.service.ICourseService;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.CommonResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jay
 * @since 2021-08-24
 */
@RestController
@CrossOrigin
@RequestMapping("/course")
public class CourseController {

    private final ICourseService courseService;

    @Autowired
    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/teacher/list")
    public CommonResult teacherCourseList(User user){
        return courseService.teacherCourseList(user.getId());
    }

    @PostMapping("/add")
    public CommonResult addCourse(User user, @RequestBody @Valid Course course){
        if(user.getUserType() != UserType.TEACHER){
            return CommonResult.fail(CommonResultEnum.UNAUTHORIZED_OPERATION_ERROR, null);
        }
        return courseService.addCourse(course, user);
    }

    @GetMapping("/info")
    public CommonResult getCourseInfo(@NotNull User user,@NotNull Long id){
        return CommonResult.success(CommonResultEnum.SUCCESS, courseService.getCourseInfo(id));
    }

    @PostMapping("/update")
    public CommonResult updateCourse(User user, @RequestBody @Valid Course course){
        return courseService.updateCourse(course, user);
    }


    @GetMapping("/student-progress")
    public CommonResult getUserStudyProgress(User user, Long courseId){
        return courseService.getStudyProgress(user, courseId);
    }
}
