package com.jay.scourse.exception;

import com.jay.scourse.vo.CommonResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 全局自定义异常类
 * </p>
 *
 * @author Jay
 * @date 2021/8/8
 **/
@Getter
@Setter
public class GlobalException extends RuntimeException{
    private final CommonResultEnum commonResultEnum;

    public GlobalException(CommonResultEnum commonResultEnum) {
        this.commonResultEnum = commonResultEnum;
    }
}
