package com.brycehan.cloud.auth.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 图片验证码
 *
 * @author Bryce Han
 * @since 2023/10/4
 */
@Data
@Schema(description = "图片验证码")
public class CaptchaVo implements Serializable {

    /**
     * key
     */
    @Schema(description = "key")
    private String key;

    /**
     * image base64
     */
    @Schema(description = "image base64")
    private String image;

}
