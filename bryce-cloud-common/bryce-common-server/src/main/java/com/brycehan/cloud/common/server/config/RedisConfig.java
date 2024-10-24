package com.brycehan.cloud.common.server.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.lettuce.core.ReadFrom;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis配置
 *
 * @since 2023/5/8
 * @author Bryce Han
 */
@Configuration
public class RedisConfig {

    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }

    @Bean
    @Primary
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // Key、HashKey使用String来序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        // Value、HashValue使用Jackson2JsonRedisSerializer来序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 配置lettuce读写分离
     *
     * @return lettuce客户端自定义配置
     */
    @Bean
    public LettuceClientConfigurationBuilderCustomizer clientConfigurationBuilderCustomizer() {
        return clientConfigurationBuilder -> clientConfigurationBuilder.readFrom(ReadFrom.REPLICA_PREFERRED);
    }

    /**
     * RedissonClient配置
     *
     * @param redisProperties RedisProperties
     * @return RedissonClient
     */
    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();
        // 哨兵模式
        RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
        if (sentinel != null) {
            config.useSentinelServers()
                    .setDatabase(redisProperties.getDatabase())
                    .setUsername(redisProperties.getUsername())
                    .setPassword(redisProperties.getPassword())
                    .setMasterName(sentinel.getMaster())
                    .addSentinelAddress(sentinel.getNodes().stream().map(node -> "redis://" + node).toArray(String[]::new))
                    .setSentinelUsername(sentinel.getUsername())
                    .setSentinelPassword(sentinel.getPassword());

            return Redisson.create(config);
        }

        // 集群模式
        RedisProperties.Cluster cluster = redisProperties.getCluster();
        if (cluster != null) {
            config.useClusterServers()
                    .addNodeAddress(redisProperties.getCluster().getNodes().stream().map(node -> "redis://" + node).toArray(String[]::new))
                    .setReadMode(ReadMode.SLAVE)
                    .setUsername(redisProperties.getUsername())
                    .setPassword(redisProperties.getPassword());
            return Redisson.create(config);
        }

        // 单机模式
        config.useSingleServer()
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setDatabase(redisProperties.getDatabase())
                .setUsername(redisProperties.getUsername())
                .setPassword(redisProperties.getPassword());
        return Redisson.create(config);
    }


}
