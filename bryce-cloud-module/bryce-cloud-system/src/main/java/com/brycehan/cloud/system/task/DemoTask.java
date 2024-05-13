package com.brycehan.cloud.system.task;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * XXL-JOB 示例任务
 *
 * @author Bryce Han
 * @since 2024/3/22
 */
@Component
@Slf4j
public class DemoTask {

    @XxlJob(value = "demoJobHandler")
    public void demoJobHandler(){
        log.info("执行示例定时任务");
    }
}
