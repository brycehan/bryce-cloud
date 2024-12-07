package com.brycehan.cloud.common.operatelog.aspect;

import com.brycehan.cloud.common.core.entity.BaseDto;
import com.brycehan.cloud.common.core.enums.OperateStatus;
import com.brycehan.cloud.common.core.enums.SourceClientType;
import com.brycehan.cloud.common.operatelog.annotation.OperatedType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统操作日志数据传输对象
 *
 * @since 2022/11/18
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统操作日志Dto")
public class OperateLogDto extends BaseDto {

    /**
     * ID
     */
    private Long id;

    /**
     * 操作名称
     */
    private String name;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 返回结果
     */
    private String jsonResult;

    /**
     * 错误消息
     */
    private String errorMessage;

    /**
     * 操作类型
     */
    private OperatedType operatedType;

    /**
     * 操作时间
     */
    private LocalDateTime operatedTime;

    /**
     * 执行时长（毫秒）
     */
    private Integer duration;

    /**
     * 操作状态（0：失败，1：成功）
     */
    private OperateStatus status;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 操作地点
     */
    private String location;

    /**
     * 来源客户端
     */
    private SourceClientType sourceClient;

    /**
     * User Agent
     */
    private String userAgent;

    /**
     * 操作人ID
     */
    private Long userId;

    /**
     * 操作人账号
     */
    private String username;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

}
