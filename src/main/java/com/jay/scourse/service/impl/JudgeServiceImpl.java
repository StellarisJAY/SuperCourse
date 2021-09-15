package com.jay.scourse.service.impl;

import com.jay.scourse.entity.*;
import com.jay.scourse.exception.GlobalException;
import com.jay.scourse.service.ICourseService;
import com.jay.scourse.service.IJudgeService;
import com.jay.scourse.service.IPracticeRecordService;
import com.jay.scourse.service.IPracticeService;
import com.jay.scourse.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Jay
 * @date 2021/9/15
 **/
@Service
public class JudgeServiceImpl implements IJudgeService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ICourseService courseService;
    private final IPracticeService practiceService;
    private final IPracticeRecordService practiceRecordService;

    @Autowired
    public JudgeServiceImpl(RedisTemplate<String, Object> redisTemplate, ICourseService courseService, IPracticeService practiceService, IPracticeRecordService practiceRecordService) {
        this.redisTemplate = redisTemplate;
        this.courseService = courseService;
        this.practiceService = practiceService;
        this.practiceRecordService = practiceRecordService;
    }

    @Override
    public CommonResult judge(User user, PracticeAnsweredVO practiceVO) {
        // 不是学生用户，登录无效
        if(user.getUserType() != UserType.STUDENT){
            throw new GlobalException(CommonResultEnum.INVALID_LOGIN_ERROR);
        }
        // 获取练习信息，判断练习是否存在
        PracticeAnsweredVO practiceInfo = (PracticeAnsweredVO) practiceService.getPracticeInfo(practiceVO.getId(), true);
        if(practiceInfo == null || !practiceInfo.getCourseId().equals(practiceVO.getCourseId())
                || !practiceInfo.getChapterId().equals(practiceVO.getChapterId())){
            throw new GlobalException(CommonResultEnum.PRACTICE_NOT_EXIST_ERROR);
        }
        // 获取课程，检查课程是否已经结束
        Course course = courseService.getCourseInfo(practiceVO.getCourseId());
        if(course.getStatus() == 2){
            throw new GlobalException(CommonResultEnum.COURSE_ALREAD_END_ERROR);
        }
        // 检查是否有练习记录，不允许重复提交
        PracticeRecord practiceRecord = practiceRecordService.getPracticeRecord(user, practiceVO.getId());
        if(practiceRecord != null){
            throw new GlobalException(CommonResultEnum.DUPLICATE_PRACTICE_RECORD_ERROR);
        }

        List<List<String>> correctAnswers = practiceInfo.getAnswers();
        double score = 0.0;
        List<Integer> answerStatus = new ArrayList<>(correctAnswers.size());
        List<Double> scores = new ArrayList<>(correctAnswers.size());
        for(int i = 0; i < practiceVO.getAnswers().size(); i++){
            List<String> answer = practiceVO.getAnswers().get(i);
            if(answer != null && !answer.isEmpty()){
                List<String> correctAnswer = correctAnswers.get(i);
                int correct = 0, wrong = 0;
                // 检查提交的每个答案
                for (String a : answer) {
                    if (!correctAnswer.contains(a)) {
                        wrong++;
                        break;
                    } else {
                        correct++;
                    }

                }
                // 全部答对
                if (correct == correctAnswer.size()){
                    double s = practiceInfo.getScores().get(i);
                    scores.add(s);
                    score += s;
                    answerStatus.add(2);
                }
                // 半对
                else if(wrong == 0){
                    double s = practiceInfo.getScores().get(i) / 2;
                    scores.add(s);
                    score += s;
                    answerStatus.add(1);
                }
                // 错误
                else{
                    scores.add(0.0);
                    answerStatus.add(0);
                }
            }
            else{
                scores.add(0.0);
                answerStatus.add(0);
            }
        }
        JudgeResultVO judgeResultVO = new JudgeResultVO(practiceInfo, practiceVO.getAnswers(), answerStatus, scores, score);

        return CommonResult.success(CommonResultEnum.SUCCESS, judgeResultVO);
    }
}
