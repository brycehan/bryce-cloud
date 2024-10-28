package com.brycehan.cloud.common.server.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.constant.CacheConstants;
import com.brycehan.cloud.common.server.config.properties.IdProperties;
import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ID生成器配置
 *
 * @since 2022/5/13
 * @author Bryce Han
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(IdProperties.class)
public class IdConfig implements InitializingBean {

    private final IdProperties idProperties;

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${spring.cloud.nacos.discovery.group}")
    private String groupName;

    private final NacosServiceManager nacosServiceManager;

    private final NacosDiscoveryProperties nacosDiscoveryProperties;

    private final RedissonClient redissonClient;

    public static final String WORKER_Id = "worker_id";

    /**
     * 注册服务的时候同时注册workerId
     */
    @Override
    public void afterPropertiesSet(){
        Map<String, String> metadata = nacosDiscoveryProperties.getMetadata();
        // 获取分布式可重入锁
        RLock lock = this.redissonClient.getLock(CacheConstants.WORKER_ID_LOCK);
        lock.lock();
        int workerId;
        try {
            workerId = getWorkerId();
        } finally {
            lock.unlock();
        }

        metadata.put(WORKER_Id, String.valueOf(workerId));
        nacosDiscoveryProperties.setMetadata(metadata);

        log.info("注册workerId[{}]到 Nacos", workerId);

        // 创建 IdGeneratorOptions 对象，可在构造函数中输入 WorkerId：
        IdGeneratorOptions options = new IdGeneratorOptions((short) workerId);

        options.WorkerIdBitLength = idProperties.getWorkerIdBitLength(); // 默认值6，限定 WorkerId 最大值为2^6-1，即默认最多支持64个节点。
        options.SeqBitLength = idProperties.getWorkerIdBitLength(); // 默认值6，限制每毫秒生成的ID个数。若生成速度超过5万个/秒，建议加大 SeqBitLength 到 10。
        options.BaseTime = idProperties.getBaseTime(); // 如果要兼容老系统的雪花算法，此处应设置为老系统的BaseTime。
        // ...... 其它参数参考 IdGeneratorOptions 定义。

        // 保存参数（务必调用，否则参数设置不生效）：
        YitIdHelper.setIdGenerator(options);

        // 以上过程只需全局一次，且应在生成ID之前完成。
    }

    /**
     * 获取可用的workerId
     *
     * @return workerId
     */
    private int getWorkerId() {
        int maxWorkerId = (1 << idProperties.getWorkerIdBitLength()) - 1;
        List<Integer> allUsedWorkerIds = getAllUsedWorkerIds();

        if(CollUtil.isEmpty(allUsedWorkerIds)) {
            return 0;
        }

        for (int i = 0; i < maxWorkerId; i++) {
            if(!allUsedWorkerIds.contains(i)) {
                return i;
            }
        }

        throw new ServerException("workerId已用完，请重新分配");
    }

    /**
     * 获取已使用的所有workerIds
     * @return workerIds
     */
    private List<Integer> getAllUsedWorkerIds() {
        List<Instance> allInstances = getAllInstances();
        List<Integer> workerIds = new ArrayList<>();

        for (Instance instance : allInstances) {
            String workerId = instance.getMetadata().get(WORKER_Id);
            if(StrUtil.isNotEmpty(workerId)) {
                workerIds.add(Integer.valueOf(workerId));
            }
        }

        return workerIds;
    }

    /**
     * 获取当前服务的所有实例
     *
     * @return 当前服务的所有实例
     */
    private List<Instance> getAllInstances() {
        try {
            return nacosServiceManager.getNamingService().getAllInstances(serviceName, groupName);
        } catch (NacosException e) {
            throw new ServerException(e.getMessage());
        }
    }

}
