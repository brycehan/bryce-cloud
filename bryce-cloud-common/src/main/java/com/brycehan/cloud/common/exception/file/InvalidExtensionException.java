package com.brycehan.cloud.common.exception.file;

import com.brycehan.cloud.common.base.http.UploadResponseStatus;
import com.brycehan.cloud.common.exception.BusinessException;

import java.io.Serial;
import java.util.Arrays;

/**
 * 文件上传无效扩展名异常类
 *
 * @since 2022/11/2
 * @author Bryce Han
 */
public class InvalidExtensionException extends BusinessException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidExtensionException(String filename, String extensionName, String[] allowedExtension) {
        super(UploadResponseStatus.UPLOAD_INVALID_EXTENSION, filename, extensionName, Arrays.toString(allowedExtension));
    }
}
