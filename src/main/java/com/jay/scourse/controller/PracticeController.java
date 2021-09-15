package com.jay.scourse.controller;

import com.jay.scourse.entity.User;
import com.jay.scourse.service.IPracticeService;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.NewPracticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jay
 * @since 2021-08-28
 */
@RestController
@RequestMapping("/practice")
public class PracticeController {

    private final IPracticeService practiceService;

    @Autowired
    public PracticeController(IPracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @PostMapping("/add")
    public CommonResult addPractice(User user, @RequestBody @Valid NewPracticeVO practiceVO){
        return practiceService.addPractice(user, practiceVO);
    }

    @GetMapping()
    public CommonResult getPracticeForStudent(User user, @RequestParam("practiceId") Long practiceId){
        return practiceService.getPracticeForStudent(user, practiceId);
    }

    @GetMapping("/list")
    public CommonResult getChapterPractices(User user,@RequestParam("courseId") Long courseId, @RequestParam("chapterId") Long chapterId){
        return practiceService.listChapterPractice(user, courseId, chapterId);
    }
}
