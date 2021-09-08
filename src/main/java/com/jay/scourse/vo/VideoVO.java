package com.jay.scourse.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jay.scourse.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 视频vo
 * </p>
 *
 * @author Jay
 * @date 2021/8/27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoVO {
    private static final long serialVersionUID = 1L;

    /**
     * 视频id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 封面url
     */
    private String coverUrl;

    private String title;

    /**
     * 视频所属章节id
     */
    private Long chapterId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 时长，单位s
     */
    private Integer duration;

    public VideoVO(Video video){
        this(video.getId(), video.getCoverUrl(), video.getTitle(), video.getChapterId(), video.getCourseId(), video.getDuration());
    }
}
