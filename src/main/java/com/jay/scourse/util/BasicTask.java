package com.jay.scourse.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 *  基础的任务对象，提供任务类型的
 * </p>
 *
 * @author Jay
 * @date 2021/9/8
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BasicTask {
    private LocalDateTime startTime;
    private Runnable runnable;
}
