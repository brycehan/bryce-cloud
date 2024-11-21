package com.brycehan.cloud.common.core.util;

import com.alibaba.excel.EasyExcel;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.util.excel.ExcelDataListener;
import com.brycehan.cloud.common.core.util.excel.ExcelFinishCallback;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Excel工具类
 *
 * @since 2023/4/23
 * @author Bryce Han
 */
@SuppressWarnings("all")
public class ExcelUtils {

    private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 导出数据到文件
     *
     * @param head 实体类表头
     * @param filename 导出的文件名称
     * @param sheetName sheet 名称
     * @param data 数据列表
     * @param <T> 实体类型
     */
    public static <T> void export(Class<T> head, String filename, String sheetName, List<T> data){
        try {
            HttpServletResponse response = ServletUtils.getResponse();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String filenameEncoded = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + filenameEncoded + ".xlsx");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), head)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(StringUtils.isBlank(sheetName) ? "Sheet1": sheetName)
                    .doWrite(data);
        }catch (IOException e){
            throw new ServerException(e.getMessage());
        }
    }

    /**
     * 读取 Excel 文件
     *
     * @param file Excel 文件
     * @param head 列名类
     * @param callback 回调
     * @param <T> 数据类型
     */
    public static <T> void read(MultipartFile file, Class<T> head, ExcelFinishCallback<T> callback) {
        try {
            EasyExcel.read(file.getInputStream(), head, new ExcelDataListener<>(callback)).sheet().doRead();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    /**
     * 读取 Excel 文件
     *
     * @param file Excel 文件
     * @param head 列名类
     * @param callback 回调
     * @param <T> 数据类型
     */
    public static <T> void read(File file, Class<T> head, ExcelFinishCallback<T> callback) {
        EasyExcel.read(file, head, new ExcelDataListener<>(callback)).sheet().doRead();
    }

}
