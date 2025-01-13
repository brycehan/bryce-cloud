package com.brycehan.cloud.storage.api;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.crypto.SecureUtil;
import com.brycehan.cloud.api.storage.api.StorageApi;
import com.brycehan.cloud.api.storage.entity.StorageVo;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.enums.AccessType;
import com.brycehan.cloud.common.core.util.FileUploadUtils;
import com.brycehan.cloud.common.core.util.MimeTypeUtils;
import com.brycehan.cloud.storage.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 上传文件存储API
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Tag(name = "上传文件存储")
@RequestMapping(path =StorageApi.PATH)
@RestController
@Validated
@RequiredArgsConstructor
public class StorageApiController implements StorageApi {

    private final StorageService storageService;

    @Override
    public ResponseResult<StorageVo> upload(@NotNull MultipartFile file, @NotNull AccessType accessType) {
        return upload(file, accessType, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
    }

    /**
     * 保存上传文件
     *
     * @param file 上传文件
     * @return 响应结果
     */
    @Override
    @Operation(summary = "上传文件")
    public ResponseResult<StorageVo> upload(@NotNull MultipartFile file, @NotNull AccessType accessType, @NotNull List<String> allowedExtensions) {
        // 是否为空
        if(file.isEmpty()) {
            return null;
        }

        // 文件格式校验
        FileUploadUtils.assertAllowed(file, allowedExtensions);

        StorageVo storageVo;
        try {
            // 上传路径
            String path = storageService.getPath(file.getOriginalFilename(), accessType);
            // 上传文件
            String url = storageService.upload(file.getInputStream(), path, accessType);

            // 上传信息
            storageVo = new StorageVo();
            storageVo.setName(file.getOriginalFilename());
            storageVo.setPath(path);
            storageVo.setUrl(url);
            storageVo.setSuffix(FileNameUtil.getSuffix(file.getOriginalFilename()));
            storageVo.setSize(file.getSize());
            storageVo.setAccessType(accessType);
            storageVo.setHash(SecureUtil.sha256(file.getInputStream()));
            storageVo.setPlatform(storageService.getPlatform());
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return ResponseResult.error("上传文件失败");
        }

        return ResponseResult.ok(storageVo);
    }

    @Override
    @Operation(summary = "下载文件")
    public ResponseEntity<byte[]> download(@NotBlank String url, @NotBlank String filename) {
        return storageService.download(url, filename);
    }
}
