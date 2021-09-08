package com.jay.scourse.controller;


import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.jay.scourse.entity.User;
import com.jay.scourse.entity.Video;
import com.jay.scourse.exception.GlobalException;
import com.jay.scourse.service.IVideoService;
import com.jay.scourse.util.PlayAuthUtil;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.CommonResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jay
 * @since 2021-08-24
 */
@RestController
@RequestMapping("/video")
public class VideoController {


    private final IVideoService videoService;

    @Autowired
    public VideoController(IVideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/play-auth")
    public CommonResult getPlayAuth(User user, Long id){
        return videoService.getPlayAuth(id);
    }

    @GetMapping("/playAuth")
    public CommonResult getPlayAuth(User user, String vid){
        if(user == null){
            throw new GlobalException(CommonResultEnum.UNAUTHORIZED_OPERATION_ERROR);
        }
        GetVideoPlayAuthResponse response = PlayAuthUtil.getVideoPlayAuth(vid);
        return CommonResult.success(CommonResultEnum.SUCCESS, response);
    }

    @PostMapping("/uploadRequest")
    public CommonResult createUploadRequest(User user, @RequestBody @NotNull Video video){
        return videoService.createUploadRequest(user, video);
    }

    @GetMapping("/uploadRequest/refresh")
    public CommonResult refreshUploadRequest(User user, @RequestParam("vid") @NotNull String vid){
        return videoService.refreshUploadRequest(user, vid);
    }


    @PostMapping("/add")
    public CommonResult addNewVideo(User user, @RequestBody @NotNull Video video) {
        return videoService.addVideo(user, video);
    }

    @GetMapping("/list")
    public CommonResult getChapterVideoList(User user, @RequestParam("chapterId") Long chapterId){
        return videoService.getChapterVideoList(user, chapterId);
    }
}
