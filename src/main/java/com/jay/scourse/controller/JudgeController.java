package com.jay.scourse.controller;

import com.jay.scourse.entity.User;
import com.jay.scourse.service.IJudgeService;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.PracticeAnsweredVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author Jay
 * @date 2021/9/15
 **/
@RestController
@RequestMapping("/judge")
public class JudgeController {

    private final IJudgeService judgeService;
    @Autowired
    public JudgeController(IJudgeService judgeService) {
        this.judgeService = judgeService;
    }

    @PostMapping("/practice")
    public CommonResult judgePractice(User user, @RequestBody @NotNull @Valid PracticeAnsweredVO practiceAnsweredVO){
        return judgeService.judge(user, practiceAnsweredVO);
    }
}
