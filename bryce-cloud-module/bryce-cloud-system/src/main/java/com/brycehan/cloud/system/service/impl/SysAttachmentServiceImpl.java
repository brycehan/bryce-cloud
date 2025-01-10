package com.brycehan.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.api.storage.api.StorageApi;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 系统附件服务实现
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Service
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
        this.baseMapper.insert(sysAttachment);
    }

    /**
     * 更新系统附件
     *
     * @param sysAttachmentDto 系统附件Dto
     */
    public void update(SysAttachmentDto sysAttachmentDto) {
        SysAttachment sysAttachment = SysAttachmentConvert.INSTANCE.convert(sysAttachmentDto);
        this.baseMapper.updateById(sysAttachment);
    }

    @Override
    public PageResult<SysAttachmentVo> page(SysAttachmentPageDto sysAttachmentPageDto) {
        IPage<SysAttachment> page = this.baseMapper.selectPage(sysAttachmentPageDto.toPage(), getWrapper(sysAttachmentPageDto));
        return new PageResult<>(page.getTotal(), SysAttachmentConvert.INSTANCE.convert(page.getRecords()));
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
    public ResponseEntity<byte[]> download(Long id) {
        SysAttachment sysAttachment = this.baseMapper.selectById(id);
        if (sysAttachment == null) {
            throw new RuntimeException("附件不存在");
        }

        return storageApi.download(sysAttachment.getPath(), sysAttachment.getName());
    }
}
