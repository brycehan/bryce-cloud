package com.brycehan.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.api.storage.api.StorageApi;
import com.brycehan.cloud.common.core.base.response.ResponseResult;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.common.server.common.IdGenerator;
import com.brycehan.cloud.system.entity.convert.SysAttachmentConvert;
import com.brycehan.cloud.system.entity.dto.SysAttachmentDto;
import com.brycehan.cloud.system.entity.dto.SysAttachmentPageDto;
import com.brycehan.cloud.system.entity.po.SysAttachment;
import com.brycehan.cloud.system.entity.vo.SysAttachmentVo;
import com.brycehan.cloud.system.mapper.SysAttachmentMapper;
import com.brycehan.cloud.system.service.SysAttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * 系统附件服务实现
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachmentMapper, SysAttachment> implements SysAttachmentService {

    private final StorageApi storageApi;

    /**
     * 添加系统附件
     *
     * @param sysAttachmentDto 系统附件Dto
     */
    public void save(SysAttachmentDto sysAttachmentDto) {
        SysAttachment sysAttachment = SysAttachmentConvert.INSTANCE.convert(sysAttachmentDto);
        sysAttachment.setId(IdGenerator.nextId());
        baseMapper.insert(sysAttachment);
    }

    /**
     * 更新系统附件
     *
     * @param sysAttachmentDto 系统附件Dto
     */
    public void update(SysAttachmentDto sysAttachmentDto) {
        SysAttachment sysAttachment = SysAttachmentConvert.INSTANCE.convert(sysAttachmentDto);
        baseMapper.updateById(sysAttachment);
    }

    @Override
    public PageResult<SysAttachmentVo> page(SysAttachmentPageDto sysAttachmentPageDto) {
        IPage<SysAttachment> page = baseMapper.selectPage(sysAttachmentPageDto.toPage(), getWrapper(sysAttachmentPageDto));
        return PageResult.of(SysAttachmentConvert.INSTANCE.convert(page.getRecords()), page.getTotal());
    }

    /**
     * 封装查询条件
     *
     * @param sysAttachmentPageDto 系统附件分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysAttachment> getWrapper(SysAttachmentPageDto sysAttachmentPageDto) {
        LambdaQueryWrapper<SysAttachment> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(sysAttachmentPageDto.getName()), SysAttachment::getName, sysAttachmentPageDto.getName());
        wrapper.like(StringUtils.isNotEmpty(sysAttachmentPageDto.getPlatform()), SysAttachment::getPlatform, sysAttachmentPageDto.getPlatform());

        return wrapper;
    }

    @Override
    public ResponseEntity<StreamingResponseBody> download(Long id) {
        SysAttachment sysAttachment = baseMapper.selectById(id);
        if (sysAttachment == null) {
            throw new RuntimeException("附件不存在");
        }

        ResponseResult<byte[]> download = storageApi.download(sysAttachment.getPath(), sysAttachment.getName());

        Assert.notNull(download, "远程调用存储服务失败");
        if (download.getCode() == 404) {
            throw new RuntimeException("附件不存在");
        }

        // 获取文件的字节数组
        byte[] data = download.getData();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(sysAttachment.getName(), StandardCharsets.UTF_8));
        headers.setAccessControlExposeHeaders(List.of(HttpHeaders.CONTENT_DISPOSITION));
        headers.setContentLength(data.length);

        // 获取文件的响应体
        StreamingResponseBody responseBody = outputStream -> outputStream.write(Objects.requireNonNull(data, "附件不存在"));

        // 获取文件的字节数组
        return ResponseEntity.ok()
                .headers(headers)
                .body(responseBody);
    }
}
