package com.brycehan.cloud.system.api;

import com.brycehan.cloud.api.system.StorageApi;
import com.brycehan.cloud.api.system.vo.StorageVo;
import com.brycehan.cloud.framework.storage.service.StorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 存储 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Tag(name = "存储 Api 实现", description = "storage")
@RestController
@RequiredArgsConstructor
public class StorageApiController implements StorageApi {

    private final StorageService storageService;

    @Override
    public StorageVo upload(MultipartFile file) throws IOException {
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
        storageVo.setUrl(url);
        storageVo.setSize(file.getSize());

        return storageVo;
    }

}
