package com.jay.scourse.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jay.scourse.common.CacheKey;
import com.jay.scourse.entity.Collection;
import com.jay.scourse.entity.User;
import com.jay.scourse.mapper.CollectionMapper;
import com.jay.scourse.service.ICollectionService;
import com.jay.scourse.vo.CollectionVO;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.CommonResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jay
 * @since 2021-08-28
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements ICollectionService {

    private final RedisTemplate<String, Object> redisTemplate;
    @Autowired
    public CollectionServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public CommonResult getCollectionList(User user, Integer pageNum, Integer pageSize) {
        // 获取分页的题集列表
        IPage<Collection> collections = query().eq("teacher_id", user.getId()).page(new Page<>(pageNum, pageSize));
        List<Collection> records = collections.getRecords();
        // 创建vo列表
        List<Collection> collectionvos = new ArrayList<>(records.size());
        // 获取每个题目集的题目总数
        for(Collection collection : records){
            // 从缓存获取
            Integer count = (Integer)redisTemplate.opsForValue().get(CacheKey.COLLECTION_QUESTION_COUNT_PREFIX + collection.getId());
            // 缓存未命中
            if(count == null){
                // 从数据库读取题目数
                count = baseMapper.getQuestionCount(collection.getId());
                // 写入缓存
                redisTemplate.opsForValue().set(CacheKey.COLLECTION_QUESTION_COUNT_PREFIX + collection.getId(), count);
            }
            CollectionVO collectionVO = new CollectionVO(collection, count);
            collectionvos.add(collectionVO);
        }
        collections.setRecords(collectionvos);
        return CommonResult.success(CommonResultEnum.SUCCESS, collections);
    }

    @Override
    public Integer addCollectionQuestion(Long qid, Long cid, Integer type) {
        return baseMapper.addCollectionQuestion(cid, qid, type);
    }
}
