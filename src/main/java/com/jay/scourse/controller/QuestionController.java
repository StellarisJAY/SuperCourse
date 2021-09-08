package com.jay.scourse.controller;


import com.jay.scourse.entity.Collection;
import com.jay.scourse.entity.User;
import com.jay.scourse.exception.GlobalException;
import com.jay.scourse.service.ICollectionService;
import com.jay.scourse.service.IQuestionService;
import com.jay.scourse.util.CsvUtil;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.CommonResultEnum;
import com.jay.scourse.vo.QuestionFileVO;
import com.jay.scourse.vo.QuestionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jay
 * @since 2021-08-29
 */
@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    private final IQuestionService questionService;
    private final ICollectionService collectionService;
    @Autowired
    public QuestionController(IQuestionService questionService, ICollectionService collectionService) {
        this.questionService = questionService;
        this.collectionService = collectionService;
    }

    @PostMapping("/add")
    public CommonResult addQuestion(User user,@RequestBody @Valid QuestionVO questionVO){
        return questionService.addQuestion(user, questionVO);
    }

    @GetMapping("/list")
    public CommonResult questionList(User user, Long collectionId, Integer pageNum, Integer pageSize){
        return questionService.getQuestionList(user, collectionId, pageNum, pageSize);
    }

    @PostMapping("/upload")
    public CommonResult uploadCsv(User user, @RequestParam("file") MultipartFile file, QuestionFileVO questionFileVO) throws IOException {
        // 检查当前用户对每一个题集的操作权限
        for(Long collectionId : questionFileVO.getCollections()){
            Collection collection = collectionService.getById(collectionId);
            // 无操作权限
            if(!user.getId().equals(collection.getTeacherId())){
                throw new GlobalException(CommonResultEnum.UNAUTHORIZED_OPERATION_ERROR);
            }
        }
        long parseStart = System.currentTimeMillis();
        // 使用csv工具类解析
        List<QuestionVO> questions = CsvUtil.parseQuestion(file.getInputStream(), questionFileVO.getFirstRowHeader());
        System.out.println("csv解析耗时: " + (System.currentTimeMillis() - parseStart));

        CommonResult result =  questionService.addQuestionBatch(user, questions, questionFileVO.getCollections());
        log.info("批量添加耗时：{}ms", System.currentTimeMillis() - parseStart);
        return result;
    }
}
