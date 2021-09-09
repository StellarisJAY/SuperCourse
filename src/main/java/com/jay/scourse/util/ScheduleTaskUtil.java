package com.jay.scourse.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 定时任务工具
 * </p>
 *
 * @author Jay
 * @date 2021/8/27
 **/
@Slf4j
public class ScheduleTaskUtil {
    private static final ScheduledThreadPoolExecutor THREAD_POOL_EXECUTOR = new ScheduledThreadPoolExecutor(3, new ThreadFactory() {
        private final AtomicInteger nextId = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread("schedule-thread-" + nextId.getAndIncrement());
        }
    });

    /**
     * 提交定时任务
     * @param task 任务
     * @param startTime 开始时间
     */
    public static void submitTask(Runnable task, LocalDateTime startTime){
        LocalDateTime now = LocalDateTime.now();
        // 开始时间在当前时间之前
        if(!now.isAfter(startTime)){
            // 直接执行任务
            long startTimeMillis = startTime.toEpochSecond(ZoneOffset.of("+8"));
            long currentTimeMillis = System.currentTimeMillis();
            THREAD_POOL_EXECUTOR.schedule(task, startTimeMillis - currentTimeMillis, TimeUnit.MILLISECONDS);
            log.info("任务: {} 已提交，执行时间：{}", task.toString(), startTime.toLocalTime());
        }
    }
}
