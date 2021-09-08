package com.jay.scourse.controller;


import com.jay.scourse.entity.CourseChapter;
import com.jay.scourse.entity.User;
import com.jay.scourse.service.ICourseChapterService;
import com.jay.scourse.vo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jay
 * @since 2021-08-24
 */
@RestController
@RequestMapping("/course-chapter")
public class CourseChapterController {

    private final ICourseChapterService courseChapterService;

    @Autowired
    public CourseChapterController(ICourseChapterService courseChapterService) {
        this.courseChapterService = courseChapterService;
    }


    @GetMapping("/list")
    public CommonResult getChapters(User user, @RequestParam("courseId") Long courseId){
        return courseChapterService.getCourseChapters(user, courseId);
    }

    @PostMapping("/add")
    public CommonResult addChapter(User user, @RequestBody CourseChapter chapter){
        return courseChapterService.addChapter(user, chapter);
    }

    @PostMapping("/delete")
    public CommonResult deleteChapter(User user, @RequestBody CourseChapter chapter){
        return courseChapterService.deleteChapter(user, chapter);
    }

}
