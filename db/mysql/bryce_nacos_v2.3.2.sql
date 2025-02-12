create database if not exists bryce_nacos default character set utf8mb4 collate utf8mb4_0900_ai_ci;
use bryce_nacos;
/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/******************************************/
/*   表名称 = config_info                  */
/******************************************/
CREATE TABLE `config_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) DEFAULT NULL COMMENT 'group_id',
  `content` longtext NOT NULL COMMENT 'content',
  `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COMMENT 'source user',
  `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) DEFAULT NULL COMMENT 'configuration description',
  `c_use` varchar(64) DEFAULT NULL COMMENT 'configuration usage',
  `effect` varchar(64) DEFAULT NULL COMMENT '配置生效的描述',
  `type` varchar(64) DEFAULT NULL COMMENT '配置的类型',
  `c_schema` text COMMENT '配置的模式',
  `encrypted_data_key` text NOT NULL COMMENT '密钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info';

/******************************************/
/*   表名称 = config_info_aggr             */
/******************************************/
CREATE TABLE `config_info_aggr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) NOT NULL COMMENT 'datum_id',
  `content` longtext NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='增加租户字段';


/******************************************/
/*   表名称 = config_info_beta             */
/******************************************/
CREATE TABLE `config_info_beta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
  `content` longtext NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COMMENT 'source user',
  `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text NOT NULL COMMENT '密钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_beta';

/******************************************/
/*   表名称 = config_info_tag              */
/******************************************/
CREATE TABLE `config_info_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
  `content` longtext NOT NULL COMMENT 'content',
  `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COMMENT 'source user',
  `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_tag';

/******************************************/
/*   表名称 = config_tags_relation         */
/******************************************/
CREATE TABLE `config_tags_relation` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增长标识',
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_tag_relation';

/******************************************/
/*   表名称 = group_capacity               */
/******************************************/
CREATE TABLE `group_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='集群、各Group容量信息表';

/******************************************/
/*   表名称 = his_config_info              */
/******************************************/
CREATE TABLE `his_config_info` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'id',
  `nid` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增标识',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
  `content` longtext NOT NULL COMMENT 'content',
  `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COMMENT 'source user',
  `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
  `op_type` char(10) DEFAULT NULL COMMENT 'operation type',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text NOT NULL COMMENT '密钥',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='多租户改造';


/******************************************/
/*   表名称 = tenant_capacity              */
/******************************************/
CREATE TABLE `tenant_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户容量信息表';


CREATE TABLE `tenant_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) default '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) default '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tenant_info';

CREATE TABLE `users` (
	`username` varchar(50) NOT NULL PRIMARY KEY COMMENT 'username',
	`password` varchar(500) NOT NULL COMMENT 'password',
	`enabled` boolean NOT NULL COMMENT 'enabled'
);

CREATE TABLE `roles` (
	`username` varchar(50) NOT NULL COMMENT 'username',
	`role` varchar(50) NOT NULL COMMENT 'role',
	UNIQUE INDEX `idx_user_role` (`username` ASC, `role` ASC) USING BTREE
);

CREATE TABLE `permissions` (
    `role` varchar(50) NOT NULL COMMENT 'role',
    `resource` varchar(128) NOT NULL COMMENT 'resource',
    `action` varchar(8) NOT NULL COMMENT 'action',
    UNIQUE INDEX `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
);

INSERT INTO users (username, password, enabled) VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', TRUE);

INSERT INTO roles (username, role) VALUES ('nacos', 'ROLE_ADMIN');

-- 配置数据
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (1, 'application-elasticsearch.yaml', 'bryce-cloud', '# Spring
spring:
  elasticsearch:
    uris: http://localhost:9200
    socket-timeout: 30s
    username: elastic
    password: 5u=dmpOsTHqga8gZ+cQK
  data:
    elasticsearch:
      repositories:
        enabled: true', '8ff012eea2807daff91dc4675df6b837', '2025-02-11 05:38:49', '2025-02-11 05:38:49', null, '172.30.0.1', '', '', null, null, null, 'yaml', null, '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (2, 'application-cache.yaml', 'bryce-cloud', '# Spring
spring:
  data: # data 配置
    redis: # data redis 配置
      # 主机
      host: localhost
      # 端口，默认为6379
      port: 6379
      # 数据库索引
      database: 1
      # 连接超时时长
      connect-timeout: 5s', '291e6e944d795321176a3866d0337ebf', '2025-02-11 05:38:49', '2025-02-11 05:38:49', null, '172.30.0.1', '', '', '', null, null, 'yaml', null, '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (3, 'application-xxl-job.yaml', 'bryce-cloud', '# XXL JOB 配置
xxl:
  job:
    enabled: true
    admin:
      ### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；为空则关闭自动注册；
      addresses: http://localhost:9080/xxl-job-admin
      ### 执行器通讯TOKEN [选填]：非空时启用；
      access-token: txuh4Pg7jKAiWVkfO4KGvVuw
      # 超时，默认3秒
      timeout: 3
    executor:
      ### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册
      appname: ${spring.application.name}
      ### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。
      address:
      ### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯实用；地址信息用于 "执行器注册" 和 "调度中心请求并触发任务"；
      ip:
      ### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；
      port: 9999
      ### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；
      log-path: /opt/applogs/xxl-job/jobhandler
      ### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；
      log-retention-days: 30', 'd9002e660ba7cacfbabbf2c5a690475c', '2025-02-11 05:38:49', '2025-02-11 12:30:20', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (4, 'application-sentinel.yaml', 'bryce-cloud', '# Spring
spring:
  cloud:
    sentinel:
      # 关闭控制台懒加载
      eager: true
      transport:
        # 控制台地址
        dashboard: localhost:8718
        port: 8719', '3f5965b030bb02620c85a8cddc5dd4ce', '2025-02-11 05:38:49', '2025-02-11 12:30:52', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (5, 'application-endpoint.yaml', 'bryce-cloud', '# actuator 暴露监控端点
management:
  endpoints:
    web:
      exposure:
        include: \'*\'
  endpoint:
    health:
      show-details: always
  zipkin:
    tracing:
      endpoint: http://localhost:9411/api/v2/spans
  tracing:
    propagation:
      type: b3_multi
    sampling:
      probability: 1

# Spring
spring:
  reactor:
    context-propagation: auto', 'bb499c4778fe39a53a1f9462ee4d1977', '2025-02-11 05:38:49', '2025-02-11 05:38:49', null, '172.30.0.1', '', '', '', null, null, 'yaml', null, '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (6, 'bryce-cloud-auth.yaml', 'bryce-cloud', '# 自定义配置
bryce:
  auth: # 权限过滤
    ignore-urls:
      all:
        - /register/**
        - /captcha/**
        - /sms/**
        - /logout
        - /api/**
      get:
        - /
        - /error
        - /webjars/**
        - /swagger-ui/**
        - /swagger-resources/**
        - /v3/api-docs/**
        - /doc.html
        - /favicon.ico
      post:
        - /loginByAccount
        - /loginByPhone

  # 用户配置
  user:
    password:
      # 密码最大错误次数
      max-retry-count: 5
      # 密码锁定间隔（默认10分钟）
      lock-duration: 10m

  # 验证码配置
  captcha:
    width: 300
    height: 80
    font-size: 54
    length: 2
    expiration: 5m', '3ddbfb58506bcbb8b6aa455b31bdc88d', '2025-02-11 05:38:49', '2025-02-11 12:35:54', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (7, 'application-feign.yaml', 'bryce-cloud', '# Spring
spring:
  cloud:
    openfeign:
      client:
        config:
          default:
            # none、basic、full，默认none
            logger-level: none
            # 连接超时时间，单位毫秒
            connect-timeout: 5000
            # 读取超时时间，单位毫秒
            read-timeout: 5000
          storage: # contextId
            # 连接超时时间，单位毫秒
            connect-timeout: 60_000
            # 读取超时时间，单位毫秒
            read-timeout: 60_000
      httpclient:
        # 开启feign对 Apache HttpClient 5 的支持
        hc5:
          enabled: true
        # 连接池最大连接数
        max-connections: 2000
        # 每个路由的最大连接数
        max-connections-per-route: 500
      compression:
        request:
          enabled: true
          min-request-size: 8192
        response:
          enabled: true
feign:
  sentinel:
    enabled: true', '95e43c2f31b1eec1883788de6e34002a', '2025-02-11 05:38:49', '2025-02-11 05:38:49', null, '172.30.0.1', '', '', '', null, null, 'yaml', null, '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (8, 'application-rabbitmq.yaml', 'bryce-cloud', '# Spring
spring:
  rabbitmq:
    # 地址，集群时逗号分隔多个地址
    addresses: localhost:5672
    username: admin
    password: admin
    # 虚拟主机
    virtual-host: /
    # 开启发送消息到交换机确认
    publisher-confirm-type: correlated
    # 开启发送消息抵达队列确认
    publisher-returns: true
    template:
      # 消息在没有被队列接收时是否强行退回，还是直接丢弃
      mandatory: true
    listener:
      simple:
        # 消费者预取的消息数量
        prefetch: 1
        acknowledge-mode: auto
        retry:
          # 开启消费者失败消费重试（阻塞试重试）
          enabled: true
          # 初始的失败等待时长为1秒
          initial-interval: 1000
          # 下次失败的等待时长倍数，下次等待时长 = multiplier * last-interval
          multiplier: 1
          # 最大重试次数（包括首次调用）
          max-attempts: 2
          # true无状态；false有状态。如果业务中包含事务，这里改为false
          stateless: true', '8a9ec8dabda451c7d4b9deda3948ae53', '2025-02-11 05:38:49', '2025-02-11 12:36:22', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (9, 'bryce-cloud-gateway.yaml', 'bryce-cloud', '# Spring
spring:
  cloud:
    gateway:
      metrics:
        enabled: true
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      httpclient:
        connect-timeout: 1000
        response-timeout: 10s
      routes:
        - id: bryce-cloud-auth
          uri: lb://bryce-cloud-auth
          predicates:
            - Path=/auth/**
          filters:
            - StripPrefix=1
        - id: bryce-cloud-system
          uri: lb://bryce-cloud-system
          order: 2
          predicates:
            - Path=/system/**
          filters:
            - StripPrefix=1
        - id: bryce-cloud-system-file
          uri: lb://bryce-cloud-system
          order: 1
          predicates:
            - Path=/system/profile/avatar,/system/user/import,/system/attachment/secure/download/**
          filters:
            - StripPrefix=1
          metadata:
            connect-timeout: 1000
            response-timeout: 60000
        - id: bryce-cloud-storage
          uri: lb://bryce-cloud-storage
          predicates:
            - Path=/storage/**
          filters:
            - StripPrefix=1
          metadata:
            connect-timeout: 2000
            response-timeout: 60000
        - id: bryce-cloud-monitor
          uri: lb://bryce-cloud-monitor
          predicates:
            - Path=/monitor/**
          filters:
            - StripPrefix=1
        - id: bryce-cloud-sms
          uri: lb://bryce-cloud-sms
          predicates:
            - Path=/sms/**
          filters:
            - StripPrefix=1
        - id: bryce-cloud-email
          uri: lb://bryce-cloud-email
          predicates:
            - Path=/email/**
          filters:
            - StripPrefix=1

knife4j:
  # 聚合 swagger 文档
  gateway:
    enabled: true
    # 指定服务发现的模式聚合微服务文档，并且是默认`default`分组
    strategy: discover
    discover:
      enabled: true
      version: openapi3
      excluded-services:
        - bryce-cloud-gateway

bryce:
  auth:
    # 不校验地址
    ignore-urls:
      - /auth/loginByAccount
      - /auth/loginByPhone
      - /auth/logout
      - /auth/register/**
      - /auth/captcha/**
      - /auth/sms/**
      - /storage/public/**
      - /*/v3/api-docs
      - /system/generator-ui/**
    # xss 过滤
    xss:
      enabled: true
      exclude-urls:
        - /system/notice  ', '0ae998580b16fc61e8d87592ddc3d257', '2025-02-11 05:38:49', '2025-02-11 12:36:44', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (10, 'application-common.yaml', 'bryce-cloud', '# 通用自定义配置
bryce:
  # id 生成器
  id:
    # 机器码位长
    worker-id-bit-length: 6
    seq-bit-length: 10
    # 起始时间
    base-time: 1692948713117

  # 线程池
  thread-pool:
    # 核心线程数
    core-pool-size: 20
    # 最大线程数
    maximum-pool-size: 100
    # 存活时间（单位：秒）
    keep-alive-time: 300
    # 任务队列的任务数
    work-queue-size: 1000

# 服务器配置
server:
  # tomcat 容器
  tomcat:
    threads:
      max: 200
      min-spare: 10

# spring 配置
spring:
  # Jackson 配置
  jackson:
    # 空值处理
    default-property-inclusion: always
  # Servlet 配置
  servlet:
    # 上传文件
    multipart:
      enabled: true
      max-file-size: 100MB
      max-request-size: 1024MB

# 接口文档
knife4j:
  enable: true
  production: false
  basic:
    enable: false
    username: swagger
    password: swagger', '29465187bf2cee9e03ad7d27c9774a23', '2025-02-11 05:38:49', '2025-02-11 12:37:12', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (11, 'bryce-cloud-system.yaml', 'bryce-cloud', '# spring 配置
spring:
  # 数据源配置
  datasource:
    dynamic:
      primary: master # 设置默认的数据源或者数据源组,默认值即为 master
      strict: false #严格匹配数据源,默认false
      p6spy: false
      hikari: # Hikari 连接池全局配置
        connection-timeout: 30000 # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没有可用的连接则发生 SQLException，默认：30秒
        minimum-idle: 2           # 最小空闲连接数
        maximum-pool-size: 10     # 最大连接数
        idle-timeout: 600000      # 连接超时的最大时长（毫秒），超时则被释放（retired），默认：10 分钟
        max-lifetime: 1800000     # 连接的生命时长（毫秒），超时而且没被使用则被释放（retired），默认：30分钟
        connection-test-query: select 1
      datasource:
        # 主库数据源
        master:
          # MySQL 8.0
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://localhost:3306/bryce_cloud_system?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullDatabaseMeansCurrent=true&useServerPrepStmts=true&cachePrepStmts=true
          username: root
          password: root
        # 从库数据源
        # slave:
          # driver-class-name: com.mysql.cj.jdbc.Driver
          # url:
          # username:
          # password:
          # postgresql
          # driver-class-name: org.postgresql.Driver
          # url: jdbc:postgresql://localhost:5432/postgres
          # username: bryce_cloud
          # password: 123456
          # 达梦
          # driver-class-name: dm.jdbc.driver.DmDriver
          # url: jdbc:dm://localhost:5436/bryce_cloud_system?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullDatabaseMeansCurrent=true
          # username: bryce_cloud
          # password: 123456

# mybatis plus 配置
mybatis-plus:
  # mapper.xml 映射文件位置
  mapper-locations:
    - classpath*:com/brycehan/cloud/*/mapper/xml/*Mapper.xml
    - classpath*:com/brycehan/boot/*/mapper/xml/*Mapper.xml
  type-aliases-package: com.brycehan.cloud.*.entity.po
  # 原生配置
  configuration:
    # 是否开启下划线和驼峰命名的映射
    map-underscore-to-camel-case: true
    # 是否开启二级缓存
    cache-enabled: false
    # 查询返回map的时候，是否返回null值对应的字段的entry
    call-setters-on-nulls: true
    # 设置参数值为null的字段的jdbcType为null
    jdbc-type-for-null: \'null\'
    # 枚举类型处理器
    default-enum-type-handler: com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler
  # 全局配置
  global-config:
    # 是否显示banner
    banner: false
    # 数据库相关配置
    db-config:
      # 用户输入ID
      id-type: input
      # 逻辑已删除值
      logic-delete-value: now()
      # 逻辑未删除值
      logic-not-delete-value: \'null\'

bryce:
  # 代码生成
  generator:
    table-prefix: brc_

  # 权限过滤
  auth:
    ignore-urls:
      all:
        - /generator-ui/**
        - /api/**
      get:
        - /
        - /error
        - /webjars/**
        - /v3/api-docs/**
        - /doc.html
        - /favicon.ico
        - /attachment/**

  # 项目配置
  application:
    # 名称
    name: Bryce Cloud
    # 版本
    version: 0.0.1-SNAPSHOT
    # 版权年份
    copyright-year: 2024', '53c784126ef509616c6eba33b3a32c31', '2025-02-11 05:38:49', '2025-02-11 12:37:57', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (12, 'sentinel-gateway-flow-rules.json', 'bryce-cloud', '[
  {
    "resource": "bryce-cloud-auth",
    "resourceMode": 0,
    "grade": 1,
    "count": 2000,
    "intervalSec": 1,
    "controlBehavior": 0,
    "burst": 0,
    "maxQueueingTimeoutMs": 500
  },
  {
    "resource": "bryce-cloud-monitor",
    "resourceMode": 0,
    "grade": 1,
    "count": 2000,
    "intervalSec": 1,
    "controlBehavior": 0,
    "burst": 0,
    "maxQueueingTimeoutMs": 500
  },
  {
    "resource": "bryce-cloud-sms",
    "resourceMode": 0,
    "grade": 1,
    "count": 2000,
    "intervalSec": 1,
    "controlBehavior": 0,
    "burst": 0,
    "maxQueueingTimeoutMs": 500
  },
  {
    "resource": "bryce-cloud-storage",
    "resourceMode": 0,
    "grade": 1,
    "count": 2000,
    "intervalSec": 1,
    "controlBehavior": 0,
    "burst": 0,
    "maxQueueingTimeoutMs": 500
  },
  {
    "resource": "bryce-cloud-system",
    "resourceMode": 0,
    "grade": 1,
    "count": 2000,
    "intervalSec": 1,
    "controlBehavior": 0,
    "burst": 0,
    "maxQueueingTimeoutMs": 500
  }
]', '87abdca0b059365e5289496939958ef7', '2025-02-11 05:38:49', '2025-02-11 05:38:49', null, '172.30.0.1', '', '', '', null, null, 'json', null, '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (13, 'bryce-cloud-storage.yaml', 'bryce-cloud', '# 自定义配置
bryce:
  # 权限过滤
  auth:
    ignore-urls:
      get:
        - /v3/api-docs
        - /public/**
        # - /download/**

  # 存储配置
  storage:
    enabled: true
    config:
      # 存储类型 local|minio|aliyun|tencent|qiniu|huawei
      type: local
      endpoint: http://localhost:8090
    local:
      # 路径 System.getProperty("user.home") + projectName + "/attachment";
      directory: ${user.home}/bryce-cloud/attachment
    minio: # minio配置
      endpoint: http://127.0.0.1:9000
      access-key: pybhzHB3EZBGIGQGQ3eh
      secret-key: Ljk81lhOK2ykR4BiGEQfOxer4OXStdUpyiMdtyxX
      bucket-name: bryce-cloud

# spring 配置
spring:
  servlet:
    # 上传文件
    multipart:
      enabled: true
      max-file-size: 1024MB
      max-request-size: 1024MB', 'e54904848d0fe497185067aa52ef1e50', '2025-02-11 05:38:49', '2025-02-11 12:38:43', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (14, 'application-task.yaml', 'bryce-cloud', '# spring 配置
spring:
  task: # 任务配置
    execution:  # 异步任务
      pool:
        core-size: 10
        max-size: 50
        queue-capacity: 100
        keep-alive: 5m
', 'ce3588e3bf127656ae3cc1ca28efe011', '2025-02-11 05:38:49', '2025-02-11 12:39:16', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (15, 'application-seata.yaml', 'bryce-cloud', '# Seata 配置
seata:
  enabled: false
  tx-service-group: default_tx_group
  enable-auto-data-source-proxy: true
  service:
    vgroup-mapping:
      default_tx_group: default', 'a76d8f76f5684d164fe4b46e95808615', '2025-02-11 05:38:49', '2025-02-11 12:40:03', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (16, 'seata-server.properties', 'SEATA_GROUP', '#For details about configuration items, see https://seata.io/zh-cn/docs/user/configurations.html
#Transport configuration, for client and server
transport.type=TCP
transport.server=NIO
transport.heartbeat=true
transport.enableTmClientBatchSendRequest=false
transport.enableRmClientBatchSendRequest=true
transport.enableTcServerBatchSendResponse=false
transport.rpcRmRequestTimeout=30000
transport.rpcTmRequestTimeout=30000
transport.rpcTcRequestTimeout=30000
transport.threadFactory.bossThreadPrefix=NettyBoss
transport.threadFactory.workerThreadPrefix=NettyServerNIOWorker
transport.threadFactory.serverExecutorThreadPrefix=NettyServerBizHandler
transport.threadFactory.shareBossWorker=false
transport.threadFactory.clientSelectorThreadPrefix=NettyClientSelector
transport.threadFactory.clientSelectorThreadSize=1
transport.threadFactory.clientWorkerThreadPrefix=NettyClientWorkerThread
transport.threadFactory.bossThreadSize=1
transport.threadFactory.workerThreadSize=default
transport.shutdown.wait=3
transport.serialization=seata
transport.compressor=none

#Transaction routing rules configuration, only for the client
service.vgroupMapping.default_tx_group=default
#If you use a registry, you can ignore it
service.default.grouplist=127.0.0.1:8091
service.enableDegrade=false
service.disableGlobalTransaction=false

client.metadataMaxAgeMs=30000
#Transaction rule configuration, only for the client
client.rm.asyncCommitBufferLimit=10000
client.rm.lock.retryInterval=10
client.rm.lock.retryTimes=30
client.rm.lock.retryPolicyBranchRollbackOnConflict=true
client.rm.reportRetryCount=5
client.rm.tableMetaCheckEnable=true
client.rm.tableMetaCheckerInterval=60000
client.rm.sqlParserType=druid
client.rm.reportSuccessEnable=false
client.rm.sagaBranchRegisterEnable=false
client.rm.sagaJsonParser=fastjson
client.rm.tccActionInterceptorOrder=-2147482648
client.tm.commitRetryCount=5
client.tm.rollbackRetryCount=5
client.tm.defaultGlobalTransactionTimeout=60000
client.tm.degradeCheck=false
client.tm.degradeCheckAllowTimes=10
client.tm.degradeCheckPeriod=2000
client.tm.interceptorOrder=-2147482648
client.undo.dataValidation=true
client.undo.logSerialization=jackson
client.undo.onlyCareUpdateColumns=true
server.undo.logSaveDays=7
server.undo.logDeletePeriod=86400000
client.undo.logTable=undo_log
client.undo.compress.enable=true
client.undo.compress.type=zip
client.undo.compress.threshold=64k
#For TCC transaction mode
tcc.fence.logTableName=tcc_fence_log
tcc.fence.cleanPeriod=1h
# You can choose from the following options: fastjson, jackson, gson
tcc.contextJsonParserType=fastjson

#Log rule configuration, for client and server
log.exceptionRate=100

#Transaction storage configuration, only for the server. The file, db, and redis configuration values are optional.
store.mode=db
store.lock.mode=db
store.session.mode=db
#Used for password encryption
store.publicKey=

#If `store.mode,store.lock.mode,store.session.mode` are not equal to `file`, you can remove the configuration block.
store.file.dir=file_store/data
store.file.maxBranchSessionSize=16384
store.file.maxGlobalSessionSize=512
store.file.fileWriteBufferCacheSize=16384
store.file.flushDiskMode=async
store.file.sessionReloadReadSize=100

#These configurations are required if the `store mode` is `db`. If `store.mode,store.lock.mode,store.session.mode` are not equal to `db`, you can remove the configuration block.
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.cj.jdbc.Driver
store.db.url=jdbc:mysql://mysql:3306/bryce_seata?useUnicode=true&rewriteBatchedStatements=true
store.db.user=root
store.db.password=root
store.db.minConn=50
store.db.maxConn=300
store.db.globalTable=global_table
store.db.branchTable=branch_table
store.db.distributedLockTable=distributed_lock
store.db.queryLimit=1000
store.db.lockTable=lock_table
store.db.maxWait=5000

#These configurations are required if the `store mode` is `redis`. If `store.mode,store.lock.mode,store.session.mode` are not equal to `redis`, you can remove the configuration block.
store.redis.mode=single
store.redis.type=pipeline
store.redis.single.host=127.0.0.1
store.redis.single.port=6379
store.redis.sentinel.masterName=
store.redis.sentinel.sentinelHosts=
store.redis.sentinel.sentinelPassword=
store.redis.maxConn=10
store.redis.minConn=1
store.redis.maxTotal=100
store.redis.database=0
store.redis.password=
store.redis.queryLimit=100

#Transaction rule configuration, only for the server
server.recovery.committingRetryPeriod=1000
server.recovery.asynCommittingRetryPeriod=1000
server.recovery.rollbackingRetryPeriod=1000
server.recovery.timeoutRetryPeriod=1000
server.maxCommitRetryTimeout=-1
server.maxRollbackRetryTimeout=-1
server.rollbackRetryTimeoutUnlockEnable=false
server.distributedLockExpireTime=10000
server.session.branchAsyncQueueSize=5000
server.session.enableBranchAsyncRemove=false
server.enableParallelRequestHandle=true
server.enableParallelHandleBranch=false

server.raft.cluster=127.0.0.1:7091,127.0.0.1:7092,127.0.0.1:7093
server.raft.snapshotInterval=600
server.raft.applyBatch=32
server.raft.maxAppendBufferSize=262144
server.raft.maxReplicatorInflightMsgs=256
server.raft.disruptorBufferSize=16384
server.raft.electionTimeoutMs=2000
server.raft.reporterEnabled=false
server.raft.reporterInitialDelay=60
server.raft.serialization=jackson
server.raft.compressor=none
server.raft.sync=true



#Metrics configuration, only for the server
metrics.enabled=false
metrics.registryType=compact
metrics.exporterList=prometheus
metrics.exporterPrometheusPort=9898
', '947afd61138c928f599bf61d4355b2c3', '2025-02-11 05:38:49', '2025-02-11 05:38:49', null, '172.30.0.1', '', '', '', null, null, 'properties', null, '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (17, 'bryce-cloud-sms.yaml', 'bryce-cloud', '# 短信配置
sms:
  config-type: yaml
  is-print: false
  login-template-id: SMS_463676981
  register-template-id: SMS_463676982
  number_text_messages_sent_per_day: 5
  blends:
    sms1:
      #框架定义的厂商名称标识
      supplier: alibaba
      #有些称为accessKey有些称之为apiKey，也有称为sdkKey或者appId。
      access-key-id: ...
      #称为accessSecret有些称之为apiSecret。
      access-key-secret: ...
      #您的短信签名
      signature: BryceAdmin
      #短信自动重试间隔时间  默认五秒
      retry-interval: 5
      # 短信重试次数，默认0次不重试，如果你需要短信重试则根据自己的需求修改值即可
      max-retries: 0

bryce:
  auth:  # 权限过滤
    ignore-urls:
      get:
        - /
        - /error
        - /webjars/**
        - /swagger-ui/**
        - /swagger-resources/**
        - /v3/api-docs/**
        - /doc.html
      all:
        - /easyTrans/proxy/**', '213867b4db718f36615d25e26b66cab6', '2025-02-11 05:38:49', '2025-02-11 12:40:57', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (18, 'application-jwt.yaml', 'bryce-cloud', '# 自定义配置
bryce:
  # 认证配置
  auth:
    # jwt 令牌
    jwt:
      # 令牌密钥
      secret: UZCiSM60eRJMOFA9mbiy
      # 权限键
      authorities-key: auth
      #令牌有效期（默认2小时）
      default-token-validity: 2h
      #App令牌有效期（默认30天）
      app-token-validity: 30d', '550fea6e736ec8d7e318b6dc9d6dcbd4', '2025-02-11 05:38:49', '2025-02-11 12:43:35', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (19, 'bryce-cloud-monitor.yaml', 'bryce-cloud', '# Spring
spring:
  security:
    user:
      name: brycehan
      password: admin
  boot:
    admin:
      ui:
        title: 布莱斯服务状态监控
', '392b9c5f96bec40a312ca1244c6a5dab', '2025-02-11 05:38:49', '2025-02-11 12:45:21', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) VALUES (20, 'bryce-cloud-email.yaml', 'bryce-cloud', '# Spring 配置
spring:
  mail:
    # smtp服务主机 smtp.163.com smtps 465 qq邮箱为smtp.qq.com
    host: smtp.163.com
    # 服务协议
    protocol: smtps
    port: 465
    # 发送邮件的账户
    username: brycehan@163.com
    # 授权码
    password: xxx
    test-connection: false
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true', '9a34ab3be4cccf1d7e476de2140cbe72', '2025-02-11 05:38:49', '2025-02-11 12:45:53', null, '172.30.0.1', '', '', '', '', '', 'yaml', '', '');
