package com.brycehan.cloud.api.storage.api;

import com.brycehan.cloud.api.storage.entity.StorageVo;
import com.brycehan.cloud.api.storage.fallback.StorageApiFallbackImpl;
import com.brycehan.cloud.common.core.base.ServerNames;
import com.brycehan.cloud.common.core.constant.DataConstants;
import com.brycehan.cloud.common.core.response.ResponseResult;
import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 存储
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
@Headers(DataConstants.INNER_CALL_HEADER)
@FeignClient(name = ServerNames.BRYCE_CLOUD_STORAGE, path = StorageApi.PATH, contextId = "storage", fallbackFactory = StorageApiFallbackImpl.class)
public interface StorageApi {

    String PATH = "/uploadFile";

    /**
     * 上传文件
     *
     * @param file 要上传的文件
     * @return 响应结果
     */
    @Operation(summary = "上传文件")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseResult<StorageVo> upload(@RequestPart MultipartFile file);

}
