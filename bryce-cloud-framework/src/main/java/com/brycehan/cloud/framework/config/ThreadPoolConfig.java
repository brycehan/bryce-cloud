package com.brycehan.cloud.framework.config;

import com.brycehan.cloud.common.util.Threads;
import com.brycehan.cloud.framework.config.properties.ThreadPoolProperties;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * 线程池配置
 *
 * @since 2022/2/23
 * @author Bryce Han
 */
@Configuration
@EnableConfigurationProperties({ThreadPoolProperties.class})
public class ThreadPoolConfig {

    /**
     * 普通线程池
     *
     * @param poolProperties 线程池属性
     * @return ThreadPoolExecutor线程池对象
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolProperties poolProperties) {
        return new ThreadPoolExecutor(poolProperties.getCorePoolSize(),
                poolProperties.getMaximumPoolSize(),
                poolProperties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(poolProperties.getWorkQueueSize()),
                new BasicThreadFactory.Builder().namingPattern("thread-pool-%d").daemon(true).build(),
                // 线程池对拒绝任务（无线程可用）的处理策略
                // CallerRunsPolicy()由提交任务到线程池的线程来执行
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 执行周期性或定时任务
     *
     * @param threadPoolProperties 线程池属性
     * @return ScheduledExecutorService线程池对象
     */
    @Bean
    public ScheduledExecutorService scheduledExecutorService(ThreadPoolProperties threadPoolProperties) {
        return new ScheduledThreadPoolExecutor(threadPoolProperties.getCorePoolSize(),
                new BasicThreadFactory.Builder().namingPattern("thread-pool-%d").daemon(true).build(),
                new ThreadPoolExecutor.CallerRunsPolicy()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                Threads.printException(r, t);
            }
        };
    }

}
