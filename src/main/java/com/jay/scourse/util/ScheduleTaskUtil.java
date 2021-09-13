package com.jay.scourse.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ScheduledFuture;
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
    public static final ScheduledThreadPoolExecutor THREAD_POOL_EXECUTOR = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        private final AtomicInteger nextId = new AtomicInteger(1);
        @Override
        // 之前忘记在线程这加 r 参数，导致了一直不执行任务
        public Thread newThread(Runnable r) {
            return new Thread(r,"schedule-thread-" + nextId.getAndIncrement());
        }
    });

    public static void submitTask(BasicTask task){
        if(task.getStartTime() == null || task.getRunnable() == null){
            throw new NullPointerException("重要参数不能为空");
        }
        LocalDateTime now = LocalDateTime.now();

        if(now.isBefore(task.getStartTime())){
            long startTimeMillis = task.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            THREAD_POOL_EXECUTOR.schedule(task.getRunnable(), startTimeMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
        else{
            THREAD_POOL_EXECUTOR.execute(task.getRunnable());
        }
    }
}
