package com.jay.scourse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jay.scourse.common.CacheKey;
import com.jay.scourse.entity.Course;
import com.jay.scourse.entity.User;
import com.jay.scourse.entity.UserType;
import com.jay.scourse.exception.GlobalException;
import com.jay.scourse.mapper.CourseMapper;
import com.jay.scourse.mapper.VideoMapper;
import com.jay.scourse.service.ICourseService;
import com.jay.scourse.service.IUserCourseService;
import com.jay.scourse.service.IWatchRecordService;
import com.jay.scourse.util.ScheduleTaskUtil;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.CommonResultEnum;
import com.jay.scourse.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
 * @since 2021-08-24
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final IUserCourseService userCourseService;
    private final VideoMapper videoMapper;
    private final IWatchRecordService watchRecordService;
    /**
     * 课程信息缓存key
     */
    private static final String CACHE_COURSE_PREFIX = "course_";
    /**
     * 课程订阅量缓存key
     */
    private static final String CACHE_COURSE_SUBSCRIBE_PREFIX = "course_sub_count_";



    @Autowired
    public CourseServiceImpl(RedisTemplate<String, Object> redisTemplate, IUserCourseService userCourseService, VideoMapper videoMapper, IWatchRecordService watchRecordService) {
        this.redisTemplate = redisTemplate;
        this.userCourseService = userCourseService;
        this.videoMapper = videoMapper;
        this.watchRecordService = watchRecordService;
    }

    @Override
    public CommonResult teacherCourseList(String teacherId) {
        List<Course> courses = query().eq("teacher_id", teacherId).list();
        // 转换成CourseVO类型
        List<CourseVO> courseList = courses.stream().map((CourseVO::new)).collect(Collectors.toList());
        // 从缓存获取关注人数
        for(CourseVO courseVO : courseList){
            Integer subCount = (Integer)redisTemplate.opsForValue().get(CACHE_COURSE_SUBSCRIBE_PREFIX + courseVO.getId());
            // 缓存没有则从数据库获取并写入缓存
            if(subCount == null){
                subCount = userCourseService.query().eq("course_id", courseVO.getId()).count();
                redisTemplate.opsForValue().set(CACHE_COURSE_SUBSCRIBE_PREFIX + courseVO.getId(), subCount);
            }
            // 写入课程vo
            courseVO.setSubscribeCount(subCount);
        }
        return CommonResult.success(CommonResultEnum.SUCCESS, courseList);
    }

    @Override
    public Course getCourseInfo(Long id) {
        // 从缓存获取课程信息
        Course course = (Course)redisTemplate.opsForValue().get(CACHE_COURSE_PREFIX + id);
        // 缓存中没有课程
        if(course == null){
            // 从数据库查询
            course = baseMapper.selectById(id);
            if(course != null){
                // 查询结果写入缓存
                redisTemplate.opsForValue().set(CACHE_COURSE_PREFIX + id, course);
            }
        }
        return course;
    }

    @Override
    public CommonResult addCourse(Course course, User user) {
        course.setTeacherName(user.getNickname());
        course.setTeacherId(user.getId());
        // 开始时间在当前时间之后，设置状态 未开始
        if(LocalDateTime.now().isBefore(course.getStartTime())){
            course.setStatus(0);
        }
        // 状态 已开始
        else if(LocalDateTime.now().isBefore(course.getEndTime())){
            course.setStatus(1);
        }
        else{
            course.setStatus(2);
        }
        baseMapper.insert(course);
        // 提交课程 开始 状态切换任务
        ScheduleTaskUtil.submitTask(new CourseStatusTask(course.getId(), 1, redisTemplate, baseMapper), course.getStartTime());
        // 提交课程 结束 状态切换任务
        ScheduleTaskUtil.submitTask(new CourseStatusTask(course.getId(), 2, redisTemplate, baseMapper), course.getEndTime());
        // 设置课程关注人数缓存
        redisTemplate.opsForValue().set(CACHE_COURSE_SUBSCRIBE_PREFIX + course.getId(), 0);
        return CommonResult.success(CommonResultEnum.SUCCESS, course);
    }

    @Override
    public CommonResult updateCourse(Course course, User user) {
        if(user.getUserType() != UserType.TEACHER){
            throw new GlobalException(CommonResultEnum.UNAUTHORIZED_OPERATION_ERROR);
        }
        // 查询原课程信息
        Course courseInfo = getCourseInfo(course.getId());
        // 课程存在，且是当前教师用户发布
        if(courseInfo != null && courseInfo.getTeacherId().equals(user.getId())){
            baseMapper.updateById(course);
            // 删除缓存旧值
            redisTemplate.delete(CACHE_COURSE_PREFIX + course.getId());
            return CommonResult.success(CommonResultEnum.MODIFICATION_SUCCESS, null);
        }
        else{
            throw new GlobalException(CommonResultEnum.COURSE_NOT_EXIST_ERROR);
        }
    }

    @Override
    public CommonResult getStudyProgress(User user, Long courseId) {
        // 从缓存获取视频总量和练习总量
        Integer videoTotal = (Integer)redisTemplate.opsForValue().get(CacheKey.COURSE_VIDEO_COUNT_PREFIX + courseId);
        Integer practiceTotal = (Integer)redisTemplate.opsForValue().get(CacheKey.COURSE_PRACTICE_COUNT_PREFIX + courseId);

        if(videoTotal == null){
            // 缓存未命中，从数据库获取
            videoTotal = videoMapper.getCourseVideoCount(courseId);
            // 写入缓存
            redisTemplate.opsForValue().set(CacheKey.COURSE_VIDEO_COUNT_PREFIX + courseId, videoTotal);
        }
        if(practiceTotal == null){
            // 练习总数缓存未命中
            practiceTotal = 0;
        }

        // 数据库查询视频观看记录
        Integer watchedVideo = watchRecordService.query()
                .eq("user_id", user.getId())
                .eq("course_id", courseId)
                .eq("finished", 1)
                .count();
        // 封装结果map
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("videoTotal", videoTotal);
        resultMap.put("practiceTotal", practiceTotal);
        resultMap.put("watchedVideo", watchedVideo);
        resultMap.put("finishedPractice", 0);
        return CommonResult.success(CommonResultEnum.SUCCESS, resultMap);
    }


    /**
     * 课程状态定时修改任务
     */
    static class CourseStatusTask implements Runnable{
        /**
         * 课程id
         */
        private final Long courseId;
        private final Integer targetStatus;
        private final RedisTemplate<String, Object> redisTemplate;
        private final CourseMapper courseMapper;

        public CourseStatusTask(Long courseId, Integer targetStatus, RedisTemplate<String, Object> redisTemplate, CourseMapper courseMapper) {
            this.courseId = courseId;
            this.targetStatus = targetStatus;
            this.redisTemplate = redisTemplate;
            this.courseMapper = courseMapper;
        }

        @Override
        public void run() {
            // 删除缓存中的课程信息
            redisTemplate.delete(CACHE_COURSE_PREFIX + courseId);
            // 修改数据库信息
            courseMapper.updateCourseStatus(courseId, targetStatus);
        }
    }
}
