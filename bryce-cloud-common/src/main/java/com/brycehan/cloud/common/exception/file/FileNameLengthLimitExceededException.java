package com.brycehan.cloud.common.exception.file;

import com.brycehan.cloud.common.base.http.UploadResponseStatus;
import com.brycehan.cloud.common.exception.BusinessException;

import java.io.Serial;

/**
 * 文件名称超长限制异常类
 *
 * @since 2022/11/2
 * @author Bryce Han
 */
public class FileNameLengthLimitExceededException extends BusinessException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super(UploadResponseStatus.UPLOAD_FILENAME_EXCEED_LENGTH, Integer.toString(defaultFileNameLength));
    }
}
