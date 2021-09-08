package com.jay.scourse.controller;

import com.jay.scourse.entity.User;
import com.jay.scourse.service.IPracticeService;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.NewPracticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResult addPractice(User user, @Valid NewPracticeVO practiceVO){
        return practiceService.addPractice(user, practiceVO);
    }
}
