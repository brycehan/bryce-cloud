package com.brycehan.cloud.api.system;

import com.brycehan.cloud.api.ServerNames;
import com.brycehan.cloud.api.system.vo.StorageVo;
import com.brycehan.cloud.common.base.http.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 存储服务Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
@FeignClient(name = ServerNames.BRYCE_CLOUD_STORAGE, contextId = "storage")
public interface StorageApi {

    /**
     * 文件上传
     *
     * @param file MultipartFile
     * @return http资源地址
     */
    @PostMapping(path = "/api/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    StorageVo upload(@RequestParam MultipartFile file) throws IOException;

    /**
     * 获取存储平台值
     *
     * @return 响应结果，存储平台值
     */
    @GetMapping(path = "/api/platform")
    ResponseResult<String> getPlatform();

}
