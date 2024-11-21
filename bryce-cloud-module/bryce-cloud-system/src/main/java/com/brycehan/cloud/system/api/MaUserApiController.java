package com.brycehan.cloud.system.api;

import com.brycehan.cloud.api.system.api.MaUserApi;
import com.brycehan.cloud.api.system.entity.vo.MaUserVo;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信小程序用户Api
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Tag(name = "微信小程序用户Api")
@RequestMapping(path = MaUserApi.PATH)
@RestController
@RequiredArgsConstructor
public class MaUserApiController implements MaUserApi {

    /**
     * 根据微信openid获取用户信息
     *
     * @param openid 微信openid
     * @return 用户信息
     */
    @Override
    @Operation(summary = "根据微信openid获取用户信息")
    @PreAuthorize("@innerAuth.hasAuthority()")
    public ResponseResult<MaUserVo> loadMaUserByOpenid(String openid) {
        return null;
    }

}
