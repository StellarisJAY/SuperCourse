package com.jay.scourse.vo;

/**
 * @author Jay
 */

public enum CommonResultEnum {

    /**
     * internal error
     */
    INTERNAL_ERROR(500, "服务器内部错误"),

    /**
     * 默认成功
     */
    SUCCESS(200, "成功"),
    MODIFICATION_SUCCESS(200, "修改成功"),
    DELETE_SUCCESS(200, "删除成功"),
    INSERT_SUCCESS(200, "添加成功"),
    /**
     * 参数绑定错误
     */
    BIND_ERROR(500001, "参数绑定类型错误"),

    LOGIN_ERROR(500101, "手机号或密码错误"),
    USER_NOT_EXIST_ERROR(500102, "用户不存在"),
    INVALID_LOGIN_ERROR(500103, "登录无效，请重新登录"),
    USER_ALREADY_EXIST_ERROR(500104, "该手机号已被注册"),
    UNAUTHORIZED_OPERATION_ERROR(500105, "未授权操作"),

    COURSE_NOT_EXIST_ERROR(500201, "课程不存在"),
    COURSE_ALREAD_END_ERROR(500202, "课程已结束"),

    CHAPTER_NOT_EXIST_ERROR(500301, "章节不存在"),
    /**
     * 视频播放凭证获取错误
     */
    VIDEO_PLAY_AUTH_ERROR(500901, "视频播放凭证获取错误"),
    VIDEO_NOT_FOUND_ERROR(500902, "视频不存在"),
    CREATE_UPLOAD_VIDEO_REQUEST_ERROR(500903, "创建上传请求错误"),
    REFRESH_UPLOAD_VIDEO_REQUEST_ERROR(500904, "刷新上传请求错误"),

    PRACTICE_TOTAL_SCORE_ERROR(5010101, "练习总分必须等于100"),
    PRACTICE_NOT_EXIST_ERROR(50010102, "练习不存在"),
    DUPLICATE_PRACTICE_RECORD_ERROR(50010103, "禁止重复提交答题");
    int code;
    String message;

    CommonResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
