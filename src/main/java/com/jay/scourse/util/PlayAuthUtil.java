package com.jay.scourse.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.jay.scourse.exception.GlobalException;
import com.jay.scourse.vo.CommonResultEnum;

/**
 * <p>
 * 视频点播服务播放凭证工具
 * </p>
 *
 * @author Jay
 * @date 2021/8/7
 **/
public class PlayAuthUtil {

    /**
     * 获取视频播放凭证
     * @param vid 视频id
     * @return 视频信息&播放凭证
     * @throws RuntimeException RuntimeException
     */
    public static GetVideoPlayAuthResponse getVideoPlayAuth(String vid) throws RuntimeException {
        try{
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(vid);
            DefaultAcsClient client = VideoStorageUtil.initVodClient();
            return client.getAcsResponse(request);
        }catch (Exception e){
            throw new GlobalException(CommonResultEnum.VIDEO_PLAY_AUTH_ERROR);
        }
    }
}
