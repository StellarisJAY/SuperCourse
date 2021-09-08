package com.jay.scourse.service.impl;

import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jay.scourse.entity.Course;
import com.jay.scourse.entity.User;
import com.jay.scourse.entity.UserType;
import com.jay.scourse.entity.Video;
import com.jay.scourse.exception.GlobalException;
import com.jay.scourse.mapper.VideoMapper;
import com.jay.scourse.service.ICourseService;
import com.jay.scourse.service.IVideoService;
import com.jay.scourse.util.PlayAuthUtil;
import com.jay.scourse.util.VideoStorageUtil;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.CommonResultEnum;
import com.jay.scourse.vo.VideoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ICourseService courseService;

    /**
     * 视频信息缓存key前缀
     */
    private static final String VIDEO_CACHE_PREFIX = "video_";

    private static final String CACHE_CHAPTER_VIDEO_LIST = "chapter_video_";
    /**
     * 缓存key默认超时时间 10 h
     */
    private static final Duration VIDEO_CACHE_TIMEOUT = Duration.ofHours(10);

    @Autowired
    public VideoServiceImpl(RedisTemplate<String, Object> redisTemplate, ICourseService courseService) {
        this.redisTemplate = redisTemplate;
        this.courseService = courseService;
    }

    @Override
    public CommonResult getPlayAuth(Long id) {
        // 从缓存获取视频信息
        Video video = (Video) redisTemplate.opsForValue().get(VIDEO_CACHE_PREFIX + id);
        if(video == null){
            // 从数据库获取video信息
            video = baseMapper.selectById(id);
            // 视频不存在
            if(video == null){
                throw new GlobalException(CommonResultEnum.VIDEO_NOT_FOUND_ERROR);
            }
            // 写入缓存，设置超时时间
            redisTemplate.opsForValue().set(VIDEO_CACHE_PREFIX + id, video);
            redisTemplate.expire(VIDEO_CACHE_PREFIX + id, VIDEO_CACHE_TIMEOUT);
        }
        GetVideoPlayAuthResponse playAuth = PlayAuthUtil.getVideoPlayAuth(video.getVid());
        return CommonResult.success(CommonResultEnum.SUCCESS, "获取播放凭证成功", playAuth);
    }

    @Override
    public CommonResult createUploadRequest(User user, Video video) {
        if(checkOperationAuth(user, video)){
            throw new GlobalException(CommonResultEnum.UNAUTHORIZED_OPERATION_ERROR);
        }
        // 拼接视频文件名：课程id_章节id_视频标题_系统时间戳.mp4
        String filename = video.getCourseId() + "_" + video.getChapterId() + "_" + video.getTitle() + "_" + System.currentTimeMillis() + ".mp4";
        // 创建视频上传请求
        CreateUploadVideoResponse response = VideoStorageUtil.createUploadVideo(video.getTitle(), filename);
        return CommonResult.success(CommonResultEnum.SUCCESS, "上传请求创建成功", response);
    }

    @Override
    public CommonResult refreshUploadRequest(User user, String vid) {
        RefreshUploadVideoResponse response = VideoStorageUtil.refreshUploadVideo(vid);
        return CommonResult.success(CommonResultEnum.SUCCESS, response);
    }

    @Override
    public CommonResult addVideo(User user, Video video) {
        // 检查是否有权限添加视频
        if(checkOperationAuth(user, video)){
            throw new GlobalException(CommonResultEnum.UNAUTHORIZED_OPERATION_ERROR);
        }
        // 添加到数据库
        baseMapper.insert(video);
        // 写入缓存
        redisTemplate.opsForList().rightPush(CACHE_CHAPTER_VIDEO_LIST + video.getChapterId(), video);
        return CommonResult.success(CommonResultEnum.INSERT_SUCCESS, null);
    }

    @Override
    public CommonResult getChapterVideoList(User user, Long chapterId) {
        // 从缓存获取章节的视频列表
        List<Object> list = redisTemplate.opsForList().range(CACHE_CHAPTER_VIDEO_LIST + chapterId, 0, -1);
        List<Video> videoList;
        // 缓存命中
        if(list != null && !list.isEmpty()){
            // 转换类型
            videoList = list.stream().map((obj) -> (Video) obj).collect(Collectors.toList());
        }
        else{
            // 缓存没有命中，从数据库查询
            videoList = query().eq("chapter_id", chapterId).list();
            if(videoList != null && !videoList.isEmpty()){
                // 数据库查询结果写入缓存
                redisTemplate.opsForList().rightPushAll(CACHE_CHAPTER_VIDEO_LIST + chapterId, videoList.toArray());
            }
        }

        if(videoList != null && !videoList.isEmpty()){
            List<VideoVO> result = videoList.stream().map(VideoVO::new).collect(Collectors.toList());
            return CommonResult.success(CommonResultEnum.SUCCESS, result);
        }
        return CommonResult.success(CommonResultEnum.SUCCESS, videoList);
    }

    /**
     * 检查视频操作权限
     * @param user 用户
     * @param video 视频
     * @return boolean
     */
    private boolean checkOperationAuth(User user, Video video){
        // 用户类型是否是老师
        if(user.getUserType() != UserType.TEACHER){
            return true;
        }
        // 查找课程信息，比较该用户是否是该课程的老师
        Course courseInfo = courseService.getCourseInfo(video.getCourseId());
        return courseInfo == null || !courseInfo.getTeacherId().equals(user.getId());
    }
}
