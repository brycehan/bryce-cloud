package com.brycehan.cloud.storage.controller;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.crypto.SecureUtil;
import com.brycehan.cloud.common.core.base.http.ResponseResult;
import com.brycehan.cloud.common.operatelog.annotation.OperateLog;
import com.brycehan.cloud.common.operatelog.annotation.OperateType;
import com.brycehan.cloud.storage.service.StorageService;
import com.brycehan.cloud.storage.vo.StorageVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 上传文件存储API
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Tag(name = "上传文件存储")
@RequestMapping("/uploadFile")
@RestController
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    /**
     * 保存上传文件
     *
     * @param file 上传文件
     * @return 响应结果
     */
    @Operation(summary = "上传文件")
    @OperateLog(type = OperateType.INSERT)
    @PostMapping
    public ResponseResult<StorageVo> upload(MultipartFile file) throws IOException {
        // 是否为空
        if(file.isEmpty()) {
            return null;
        }

        // 上传路径
        String path = this.storageService.getPath(file.getOriginalFilename());
        // 上传文件
        String url = this.storageService.upload(file.getInputStream(), path);

        // 上传信息
        StorageVo storageVo = new StorageVo();
        storageVo.setName(file.getOriginalFilename());
        storageVo.setSize(file.getSize());
        storageVo.setSuffix(FileNameUtil.getSuffix(file.getOriginalFilename()));
        storageVo.setHash(SecureUtil.sha256(file.getInputStream()));
        storageVo.setUrl(url);
        storageVo.setPlatform(this.storageService.storageProperties.getConfig().getType().name());

        return ResponseResult.ok(storageVo);
    }

}
