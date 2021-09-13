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
 * 注册请求实体
 * </p>
 *
 * @author Jay
 * @date 2021/8/8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterVO {
    /**
     * 11位手机号id
     */
    @IsMobile
    private String id;

    /**
     * 昵称
     */
    @NotNull
    @Length(min = 5, max = 16)
    private String nickname;

    /**
     * 两次md5加密后的密码
     */
    @NotNull
    @Length(min = 32, max = 32)
    private String password;
}
