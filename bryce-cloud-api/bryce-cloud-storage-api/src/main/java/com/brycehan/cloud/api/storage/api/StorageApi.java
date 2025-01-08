package com.brycehan.cloud.api.storage.api;

import com.brycehan.cloud.api.storage.entity.StorageVo;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.enums.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 存储
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
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
    ResponseResult<StorageVo> upload(@RequestParam @NotNull MultipartFile file, @RequestParam @NotNull AccessType accessType);

    /**
     * 下载文件
     *
     * @param url           文件地址
     * @param filename      文件名
     * @param accessType    访问类型
     */
    @Operation(summary = "下载文件")
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ResponseEntity<byte[]> download(@RequestParam @NotBlank String url, @RequestParam @NotBlank String filename,
                                    @RequestParam @NotNull AccessType accessType);
}
