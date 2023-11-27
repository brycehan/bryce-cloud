package com.brycehan.cloud.common.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * jwt视图对象
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Data
@Builder
@Schema(description = "Jwt令牌Vo")
public class LoginVo {

    /**
     * jwt令牌
     */
    @Schema(description = "jwt令牌")
    private String token;

    /**
     * 登录类型
     */
    @Schema(description = "登录类型")
    private String type;

}
