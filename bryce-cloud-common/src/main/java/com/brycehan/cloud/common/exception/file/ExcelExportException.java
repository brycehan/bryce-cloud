package com.brycehan.cloud.common.exception.file;

import com.brycehan.cloud.common.base.http.HttpResponseStatus;
import com.brycehan.cloud.common.exception.BusinessException;

import java.io.Serial;

/**
 * Excel导出异常类
 *
 * @since 2022/11/2
 * @author Bryce Han
 */
public class ExcelExportException extends BusinessException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ExcelExportException() {
        super(HttpResponseStatus.HTTP_INTERNAL_ERROR, "导出文件出错了");
    }
}
