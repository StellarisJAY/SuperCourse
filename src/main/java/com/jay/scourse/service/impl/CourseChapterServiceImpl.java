package com.jay.scourse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jay.scourse.entity.Course;
import com.jay.scourse.entity.CourseChapter;
import com.jay.scourse.entity.User;
import com.jay.scourse.entity.UserType;
import com.jay.scourse.exception.GlobalException;
import com.jay.scourse.mapper.CourseChapterMapper;
import com.jay.scourse.service.ICourseChapterService;
import com.jay.scourse.service.ICourseService;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.CommonResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jay
 * @since 2021-08-24
 */
@Service
public class CourseChapterServiceImpl extends ServiceImpl<CourseChapterMapper, CourseChapter> implements ICourseChapterService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CACHE_COURSE_CHAPTER_PREFIX = "course_chapter_";
    private final ICourseService courseService;
    @Autowired
    public CourseChapterServiceImpl(RedisTemplate<String, Object> redisTemplate, ICourseService courseService) {
        this.redisTemplate = redisTemplate;
        this.courseService = courseService;
    }

    @Override
    public CommonResult getCourseChapters(User user, Long courseId) {
        // 从缓存获取章节信息
        List<Object> list = redisTemplate.opsForList().range(CACHE_COURSE_CHAPTER_PREFIX + courseId, 0, -1);
        List<CourseChapter> chapters;
        // 缓存没有章节信息
        if(list == null || list.isEmpty()){
            // 从数据库读取
            chapters = this.query().eq("course_id", courseId).list();
            // 重新写入缓存
            /*
                redisTemplate 的 隐藏bug，lrPushAll（key, collection）方法是用不了的
             */
            if(chapters != null && !chapters.isEmpty()){
                redisTemplate.opsForList().rightPushAll(CACHE_COURSE_CHAPTER_PREFIX + courseId, chapters.toArray());
            }
        }
        else{
            // 将Object映射为CourseChapter
            chapters = list.stream().map((obj)->(CourseChapter)obj).collect(Collectors.toList());
        }
        return CommonResult.success(CommonResultEnum.SUCCESS, chapters);
    }

    @Override
    public CommonResult addChapter(User user, CourseChapter chapter) {
        // 检查操作权限
        if(!checkOperationAuth(user, chapter.getCourseId())){
            throw new GlobalException(CommonResultEnum.UNAUTHORIZED_OPERATION_ERROR);
        }
        Long courseId = chapter.getCourseId();
        // 数据库写入
        baseMapper.insert(chapter);
        // 缓存的课程章节列表插入章节，右侧入栈
        redisTemplate.opsForList().rightPush(CACHE_COURSE_CHAPTER_PREFIX + courseId, chapter);

        return CommonResult.success(CommonResultEnum.SUCCESS, "添加章节成功", chapter);
    }

    @Override
    public CommonResult deleteChapter(User user, CourseChapter chapter) {
        // 检查操作权限
        if(!checkOperationAuth(user, chapter.getCourseId())){
            throw new GlobalException(CommonResultEnum.UNAUTHORIZED_OPERATION_ERROR);
        }
        // 删除缓存内容
        redisTemplate.opsForList().remove(CACHE_COURSE_CHAPTER_PREFIX + chapter.getCourseId(), 1, chapter);
        // 删除数据库内容
        int status = baseMapper.deleteById(chapter.getId());
        // 删除状态为0，该章节不存在
        if(status == 0){
            throw new GlobalException(CommonResultEnum.CHAPTER_NOT_EXIST_ERROR);
        }
        return CommonResult.success(CommonResultEnum.DELETE_SUCCESS, null);
    }

    /**
     * 检查用户操作权限
     * @param user 用户
     * @param courseId 课程id
     * @return 是否有权操作
     */
    private Boolean checkOperationAuth(User user, Long courseId){
        if(user.getUserType() != UserType.TEACHER){
            return false;
        }
        Course courseInfo = courseService.getCourseInfo(courseId);
        return courseInfo.getTeacherId().equals(user.getId());
    }


}
