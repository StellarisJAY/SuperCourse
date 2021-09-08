package com.jay.scourse.util;


import com.aliyun.vod.upload.impl.PutObjectProgressListener;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import com.jay.scourse.exception.GlobalException;
import com.jay.scourse.vo.CommonResultEnum;

/**
 * <p>
 * 视频点播服务工具
 * </p>
 *
 * @author Jay
 * @date 2021/8/24
 **/
public class VideoStorageUtil {

    /**
     * 阿里云访问凭证
     */
    private static final String ACCESS_KEY_ID = "*************************";

    private static final String ACCESS_KEY_SECRET = "**********************";

    /**
     * 获取vod客户端
     * @return vod client
     */
    public static DefaultAcsClient initVodClient() {
        String regionId = "cn-shenzhen";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        return new DefaultAcsClient(profile);
    }

    /**
     * 服务端上传视频，上传服务端本地文件
     * @param title 标题
     * @param filename 文件绝对路径
     */
    public static void uploadVideo(String title, String filename){
        UploadVideoRequest request = new UploadVideoRequest(ACCESS_KEY_ID, ACCESS_KEY_SECRET, title, filename);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        request.setEnableCheckpoint(false);
        /* 开启默认上传进度回调 */
        request.setPrintProgress(true);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        request.setProgressListener(new PutObjectProgressListener());
        /* 点播服务接入点 */
        request.setApiRegionId("cn-shenzhen");

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if(response.isSuccess()){
            System.out.println(response.getVideoId());
            System.out.println(response.getCode());
        }
    }

    /**
     * 创建视频上传请求，获得上传地址和上传凭证
     * @param title 视频标题
     * @return 上传地址 & 上传凭证
     */
    public static CreateUploadVideoResponse createUploadVideo(String title, String filename) {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle(title);
        request.setFileName(filename);
        try{
            DefaultAcsClient client = initVodClient();
            return client.getAcsResponse(request);
        }catch (ClientException e){
            e.printStackTrace();
            throw new GlobalException(CommonResultEnum.CREATE_UPLOAD_VIDEO_REQUEST_ERROR);
        }
    }

    /**
     * 刷新上传请求，通过vid获取上传凭证和地址
     * @param vid 视频vid，由点播服务提供
     * @return Response
     */
    public static RefreshUploadVideoResponse refreshUploadVideo(String vid) {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setVideoId(vid);
        try{
            DefaultAcsClient client = initVodClient();
            return client.getAcsResponse(request);
        }catch (ClientException e){
            e.printStackTrace();
            throw new GlobalException(CommonResultEnum.REFRESH_UPLOAD_VIDEO_REQUEST_ERROR);
        }
    }
}
