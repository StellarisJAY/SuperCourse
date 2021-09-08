package com.jay.scourse.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jay.scourse.common.CacheKey;
import com.jay.scourse.entity.Collection;
import com.jay.scourse.entity.Question;
import com.jay.scourse.entity.User;
import com.jay.scourse.entity.UserType;
import com.jay.scourse.exception.GlobalException;
import com.jay.scourse.mapper.CollectionMapper;
import com.jay.scourse.mapper.QuestionMapper;
import com.jay.scourse.service.IQuestionService;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.CommonResultEnum;
import com.jay.scourse.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jay
 * @since 2021-08-29
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final CollectionMapper collectionMapper;
    @Autowired
    public QuestionServiceImpl(RedisTemplate<String, Object> redisTemplate, CollectionMapper collectionMapper) {
        this.redisTemplate = redisTemplate;
        this.collectionMapper = collectionMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult addQuestion(User user, QuestionVO questionVO) {
        // 检查权限
        if(!checkOperationAuth(user, questionVO)){
            throw new GlobalException(CommonResultEnum.UNAUTHORIZED_OPERATION_ERROR);
        }
        // 存入数据库
        save(questionVO);
        // 添加题目-题集关系
        baseMapper.saveCollectionQuestion(questionVO.getId(), questionVO.getCollectionId());
        // 添加答案
        for(String answer : questionVO.getAnswers()){
            baseMapper.saveAnswer(questionVO.getId(), answer);
        }
        // 缓存题集题目数 + 1
        redisTemplate.opsForValue().increment(CacheKey.COLLECTION_QUESTION_COUNT_PREFIX + questionVO.getCollectionId());
        // 删除缓存
        redisTemplate.delete(CacheKey.COLLECTION_QUESTION_ID_PREFIX + questionVO.getCollectionId());
        return CommonResult.success(CommonResultEnum.INSERT_SUCCESS, questionVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult addQuestionBatch(User user, List<QuestionVO> questions, List<Long> collectionIds) {
        // 转换类型到数据库实体
        List<Question> batch = questions.stream()
                .map(questionVO -> (Question) questionVO)
                .collect(Collectors.toList());
        // 批量添加，添加后得到题目id
        saveBatch(batch);

        // 题目答案
        for(int i = 0; i < questions.size(); i++){
            QuestionVO questionVO = questions.get(i);
            // 为题目添加答案
            for(String answer : questionVO.getAnswers()){
                baseMapper.saveAnswer(batch.get(i).getId(), answer);
            }
        }

        // 添加题目到题目集
        for(Long collectionId : collectionIds){
            for(Question question : batch){
                baseMapper.saveCollectionQuestion(question.getId(), collectionId);
            }
            redisTemplate.opsForValue().increment(CacheKey.COLLECTION_QUESTION_COUNT_PREFIX + collectionId, batch.size());
            // 删除缓存中的 题集-题目关系
            redisTemplate.delete(CacheKey.COLLECTION_QUESTION_ID_PREFIX + collectionId);
        }
        return CommonResult.success(CommonResultEnum.INSERT_SUCCESS, batch);
    }

    @Override
    public CommonResult getQuestionList(User user, Long collectionId, Integer pageNum, Integer pageSize) {
        // 检查读取权限
        if(!checkReadAuth(user, collectionId)){
            throw new GlobalException(CommonResultEnum.UNAUTHORIZED_OPERATION_ERROR);
        }
        // 从缓存获取题目集的所有题目id
        List<Object> cacheResult = redisTemplate.opsForList().range(CacheKey.COLLECTION_QUESTION_ID_PREFIX + collectionId, 0, -1);

        List<Long> questionIds;
        // 缓存未命中
        if(cacheResult == null || cacheResult.isEmpty()){
            // 查询所有题目id
            questionIds = baseMapper.getCollectionQuestions(collectionId);
            if(!questionIds.isEmpty()){
                // 写回缓存
                redisTemplate.opsForList().rightPushAll(CacheKey.COLLECTION_QUESTION_ID_PREFIX + collectionId, questionIds.toArray());
            }
        }
        else{
            // 缓存命中，转换类型
            questionIds = cacheResult.stream()
                    .map(obj->Long.valueOf((Integer)obj))
                    .collect(Collectors.toList());
        }

        // 查询题目详情
        IPage<Question> result = query().in("id", questionIds).page(new Page<>(pageNum, pageSize));
        return CommonResult.success(CommonResultEnum.SUCCESS, result);
    }

    /**
     * 检查操作权限
     * 检查题目的目标题集是否属于该用户
     * @param user user
     * @param questionVO 题目实体
     * @return boolean
     */
    private boolean checkOperationAuth(User user, QuestionVO questionVO){
        // 仅教师用户有权操作collection
        if(user.getUserType() != UserType.TEACHER){
            return false;
        }
        // 获取collection实体
        Collection collection = collectionMapper.selectById(questionVO.getCollectionId());

        // 检查该collection是否存在和是否 属于该用户
        return collection != null && collection.getTeacherId().equals(user.getId());
    }

    /**
     * 检查题目列表读取权限
     * @param user 用户
     * @param collectionId collectionId
     * @return boolean
     */
    private boolean checkReadAuth(User user, Long collectionId){
        Collection collection = collectionMapper.selectById(collectionId);
        // 检查读取题集权限，是否是公开题集，用户是否拥有题集
        return collection != null && (collection.getPrivateCollection() == 0 || collection.getTeacherId().equals(user.getId()));
    }
}
