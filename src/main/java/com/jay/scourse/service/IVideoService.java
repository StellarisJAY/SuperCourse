package com.jay.scourse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jay.scourse.entity.User;
import com.jay.scourse.entity.Video;
import com.jay.scourse.vo.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jay
 * @since 2021-08-24
 */
public interface IVideoService extends IService<Video> {

    /**
     * 获取视频播放凭证
     * @param id 视频id
     * @return commonResult
     */
    CommonResult getPlayAuth(Long id);

    /**
     * 创建上传请求
     * @param user 用户
     * @param video 视频信息
     * @return CommonResult
     */
    CommonResult createUploadRequest(User user, Video video);

    /**
     * 刷新上传凭证
     * @param user 用户
     * @param vid 视频id
     * @return CommonResult
     */
    CommonResult refreshUploadRequest(User user, String vid);

    /**
     * 添加视频（不是上传）
     * @param user 用户
     * @param video 视频
     * @return CommonResult
     */
    CommonResult addVideo(User user, Video video);

    /**
     * 获取章节的视频列表
     * @param user 用户
     * @param chapterId 章节id
     * @return CommonResult（视频列表）
     */
    CommonResult getChapterVideoList(User user, Long chapterId);
}
