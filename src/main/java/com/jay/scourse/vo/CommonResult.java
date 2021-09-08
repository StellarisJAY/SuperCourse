package com.jay.scourse.vo;

import lombok.*;

/**
 * <p>
 * 默认返回对象
 * </p>
 *
 * @author Jay
 * @date 2021/8/7
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommonResult {
    private Integer code;
    private String message;
    private Object data;


    public static CommonResult fail(CommonResultEnum resultEnum, String message, Object data){
        return new CommonResult(resultEnum.code, message, data);
    }

    public static CommonResult fail(CommonResultEnum resultEnum, Object data){
        return new CommonResult(resultEnum.code, resultEnum.message, data);
    }

    public static CommonResult success(CommonResultEnum resultEnum, String message, Object data){
        return new CommonResult(resultEnum.code, message, data);
    }

    public static CommonResult success(CommonResultEnum resultEnum, Object data){
        return new CommonResult(resultEnum.code, resultEnum.message, data);
    }

}
