package com.brycehan.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.common.base.entity.PageResult;
import com.brycehan.cloud.common.util.DateTimeUtils;
import com.brycehan.cloud.common.util.ExcelUtils;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.system.convert.SysAttachmentConvert;
import com.brycehan.cloud.system.dto.SysAttachmentPageDto;
import com.brycehan.cloud.system.entity.SysAttachment;
import com.brycehan.cloud.system.mapper.SysAttachmentMapper;
import com.brycehan.cloud.system.service.SysAttachmentService;
import com.brycehan.cloud.system.vo.SysAttachmentVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 系统附件服务实现
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachmentMapper, SysAttachment> implements SysAttachmentService {

    @Override
    public PageResult<SysAttachmentVo> page(SysAttachmentPageDto sysAttachmentPageDto) {
        IPage<SysAttachment> page = this.baseMapper.selectPage(getPage(sysAttachmentPageDto), getWrapper(sysAttachmentPageDto));
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
        wrapper.eq(StringUtils.isNotEmpty(sysAttachmentPageDto.getType()), SysAttachment::getType, sysAttachmentPageDto.getType());
        wrapper.eq(Objects.nonNull(sysAttachmentPageDto.getTenantId()), SysAttachment::getTenantId, sysAttachmentPageDto.getTenantId());
        wrapper.like(StringUtils.isNotEmpty(sysAttachmentPageDto.getName()), SysAttachment::getName, sysAttachmentPageDto.getName());
        wrapper.like(StringUtils.isNotEmpty(sysAttachmentPageDto.getPlatform()), SysAttachment::getPlatform, sysAttachmentPageDto.getPlatform());

        return wrapper;
    }

    @Override
    public void export(SysAttachmentPageDto sysAttachmentPageDto) {
        List<SysAttachment> sysAttachmentList = this.baseMapper.selectList(getWrapper(sysAttachmentPageDto));
        List<SysAttachmentVo> sysAttachmentVoList = SysAttachmentConvert.INSTANCE.convert(sysAttachmentList);
        ExcelUtils.export(SysAttachmentVo.class, "系统附件_".concat(DateTimeUtils.today()), "系统附件", sysAttachmentVoList);
    }

}
