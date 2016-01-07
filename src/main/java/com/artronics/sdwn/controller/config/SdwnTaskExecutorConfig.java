package com.artronics.sdwn.controller.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class SdwnTaskExecutorConfig
{
    private final static Logger log = Logger.getLogger(SdwnTaskExecutorConfig.class);

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setQueueCapacity(25);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }
}
