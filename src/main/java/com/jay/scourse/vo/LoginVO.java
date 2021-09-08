package com.jay.scourse.vo;

import com.jay.scourse.annotation.IsMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 前端发送登录请求实体
 * </p>
 *
 * @author Jay
 * @date 2021/8/8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginVO {
    @IsMobile
    private String id;
    @NotNull
    @Length(min = 32, max = 32)
    private String password;

}
