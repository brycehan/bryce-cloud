package com.brycehan.cloud.api.system;

import com.brycehan.cloud.api.ServerNames;
import com.brycehan.cloud.api.system.vo.StorageVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
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
@FeignClient(name = ServerNames.BRYCE_CLOUD_SYSTEM, contextId = "storage")
public interface StorageApi {

    /**
     * 文件上传
     *
     * @param file MultipartFile
     * @return http资源地址
     */
    @PostMapping(path = "/api/storage/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    StorageVo upload(@RequestParam MultipartFile file) throws IOException;

}
