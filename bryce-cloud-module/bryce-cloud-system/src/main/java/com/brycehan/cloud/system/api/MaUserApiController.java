package com.brycehan.cloud.system.api;

import com.brycehan.cloud.api.system.api.MaUserApi;
import com.brycehan.cloud.api.system.vo.MaUserVo;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统参数 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Tag(name = "系统用户Api实现")
@RestController
@RequiredArgsConstructor
public class MaUserApiController implements MaUserApi {

    @Override
    public ResponseResult<MaUserVo> loadMaUserByOpenid(String openid) {
        return null;
    }

}
