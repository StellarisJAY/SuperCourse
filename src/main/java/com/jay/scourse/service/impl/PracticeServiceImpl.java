package com.jay.scourse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jay.scourse.common.CacheKey;
import com.jay.scourse.entity.*;
import com.jay.scourse.exception.GlobalException;
import com.jay.scourse.mapper.CourseMapper;
import com.jay.scourse.mapper.PracticeMapper;
import com.jay.scourse.mapper.PracticeRecordMapper;
import com.jay.scourse.service.IPracticeService;
import com.jay.scourse.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jay
 * @since 2021-08-28
 */
@Service
public class PracticeServiceImpl extends ServiceImpl<PracticeMapper, Practice> implements IPracticeService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final CourseMapper courseMapper;
    private final PracticeRecordMapper practiceRecordMapper;
    private static final double STANDARD_TOTAL_SCORE = 100.0;
    @Autowired
    public PracticeServiceImpl(RedisTemplate<String, Object> redisTemplate, CourseMapper courseMapper, PracticeRecordMapper practiceRecordMapper) {
        this.redisTemplate = redisTemplate;
        this.courseMapper = courseMapper;
        this.practiceRecordMapper = practiceRecordMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult addPractice(User user, NewPracticeVO practiceVO) {
        // 检查用户类型
        if(user.getUserType() != UserType.TEACHER){
            throw new GlobalException(CommonResultEnum.INVALID_LOGIN_ERROR);
        }
        // 检查练习题总分
        double sum = 0.0;
        for(Double score : practiceVO.getScores()){
            sum += score;
        }
        if(sum != STANDARD_TOTAL_SCORE){
            throw new GlobalException(CommonResultEnum.PRACTICE_TOTAL_SCORE_ERROR);
        }

        // 检查练习所属课程是否属于该用户
        Course course = getCourse(practiceVO.getCourseId());
        if(!user.getId().equals(course.getTeacherId())){
            throw new GlobalException(CommonResultEnum.UNAUTHORIZED_OPERATION_ERROR);
        }
        // 添加练习到数据库
        baseMapper.insert(practiceVO);
        List<Long> questions = practiceVO.getQuestions();
        List<Double> scores = practiceVO.getScores();

        // 添加练习-题目关系
        for(int i = 0; i < questions.size(); i++){
            baseMapper.addPracticeQuestion(practiceVO.getId(), questions.get(i), scores.get(i));
        }
        return CommonResult.success(CommonResultEnum.INSERT_SUCCESS, practiceVO);
    }

    @Override
    public CommonResult getPracticeForStudent(User user, Long practiceId) {
        if(user.getUserType() != UserType.STUDENT){
            throw new GlobalException(CommonResultEnum.INVALID_LOGIN_ERROR);
        }
        // 查询学生练习记录
        PracticeRecord practiceRecord = getPracticeRecord(user.getId(), practiceId);
        // 已完成练习，返回带有答案的题目列表
        if(practiceRecord != null){
            return CommonResult.success(CommonResultEnum.SUCCESS, null);
        }
        // 未完成练习，返回无答案题目列表
        else{
            return CommonResult.success(CommonResultEnum.SUCCESS, getPracticeNoAnswers(practiceId));
        }
    }

    /**
     * 获取课程信息
     * @param courseId 课程id
     * @return Course
     */
    private Course getCourse(Long courseId){
        // 缓存读取
        Course course = (Course)redisTemplate.opsForValue().get(CacheKey.COURSE_INFO_PREFIX + courseId);
        // 缓存未命中，从数据库获取
        if(course == null){
            course = courseMapper.selectById(courseId);
            // 写回缓存
            redisTemplate.opsForValue().set(CacheKey.COURSE_INFO_PREFIX + courseId, course);
        }
        return course;
    }

    /**
     * 获取练习记录
     * @param userId 用户id
     * @param practiceId 练习id
     * @return PracticeRecord
     */
    private PracticeRecord getPracticeRecord(String userId, Long practiceId){
        PracticeRecord practiceRecord = (PracticeRecord)redisTemplate.opsForValue().get(CacheKey.STUDENT_PRACTICE_RECORD + userId + ":" + practiceId);
        if(practiceRecord == null){
            practiceRecord = practiceRecordMapper.selectByIds(userId, practiceId);
            redisTemplate.opsForValue().set(CacheKey.STUDENT_PRACTICE_RECORD + userId + ":" + practiceId, practiceRecord);
        }
        return practiceRecord;
    }


    private PracticeVO getPracticeNoAnswers(Long practiceId){
        List<Object> rawQuestionList = redisTemplate.opsForList().range(CacheKey.PRACTICE_QUESTION + practiceId, 0, -1);
        List<Question> questions;
        // 缓存命中
        if(rawQuestionList != null && !rawQuestionList.isEmpty()){
            questions = rawQuestionList.stream().map(raw -> (Question) raw).collect(Collectors.toList());
        }
        // 缓存未命中，从数据库获取
        else{
            questions = baseMapper.getQuestionsNoAnswer(practiceId);
            // 写回缓存
            redisTemplate.opsForList().rightPushAll(CacheKey.PRACTICE_QUESTION + practiceId, questions.toArray());
        }

        List<Object> rawScoreList = redisTemplate.opsForList().range(CacheKey.PRACTICE_SCORE_PREFIX + practiceId, 0, -1);
        List<Double> scores;
        if(rawScoreList != null && !rawScoreList.isEmpty()){
            scores = rawScoreList.stream().map(raw->(Double)raw).collect(Collectors.toList());
        }
        // 缓存未命中，从数据库获取
        else{
            scores = baseMapper.getQuestionScores(practiceId);
            // 写回缓存
            redisTemplate.opsForList().rightPushAll(CacheKey.PRACTICE_SCORE_PREFIX + practiceId, scores.toArray());
        }
        PracticeVO practiceVO = new PracticeVO();
        practiceVO.setQuestions(questions);
        practiceVO.setScores(scores);
        return practiceVO;
    }

}
