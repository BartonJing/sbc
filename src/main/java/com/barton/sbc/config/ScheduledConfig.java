package com.barton.sbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author barton
 * 定时任务配置
 */
@Configuration
public class ScheduledConfig {
    /**
     * 存放所有启动定时任务对象，极其重要
     */
    public static ConcurrentHashMap<String, ScheduledFuture<?>> scheduleMap = new ConcurrentHashMap<>();
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
}
