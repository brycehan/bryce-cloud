package com.brycehan.cloud.common.mybatis.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * 从库数据源
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
@DS("slave")
public @interface Slave {
}
