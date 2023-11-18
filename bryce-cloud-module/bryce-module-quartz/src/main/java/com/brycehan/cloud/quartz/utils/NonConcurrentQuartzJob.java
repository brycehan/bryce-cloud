package com.brycehan.cloud.quartz.utils;

import org.quartz.DisallowConcurrentExecution;

/**
 * 非并发定时任务
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
@DisallowConcurrentExecution
public class NonConcurrentQuartzJob extends AbstractQuartzJob {

}
