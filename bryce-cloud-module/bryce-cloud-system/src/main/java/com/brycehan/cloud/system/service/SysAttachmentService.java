package com.brycehan.cloud.system.service;

import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.base.IdGenerator;
import com.brycehan.cloud.common.mybatis.service.BaseService;
import com.brycehan.cloud.system.entity.convert.SysAttachmentConvert;
import com.brycehan.cloud.system.entity.dto.SysAttachmentDto;
import com.brycehan.cloud.system.entity.dto.SysAttachmentPageDto;
import com.brycehan.cloud.system.entity.po.SysAttachment;
import com.brycehan.cloud.system.entity.vo.SysAttachmentVo;

/**
 * 系统附件服务
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
public interface SysAttachmentService extends BaseService<SysAttachment> {

    /**
     * 添加系统附件
     *
     * @param sysAttachmentDto 系统附件Dto
     */
    default void save(SysAttachmentDto sysAttachmentDto) {
        SysAttachment sysAttachment = SysAttachmentConvert.INSTANCE.convert(sysAttachmentDto);
        sysAttachment.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(sysAttachment);
    }

    /**
     * 更新系统附件
     *
     * @param sysAttachmentDto 系统附件Dto
     */
    default void update(SysAttachmentDto sysAttachmentDto) {
        SysAttachment sysAttachment = SysAttachmentConvert.INSTANCE.convert(sysAttachmentDto);
        this.getBaseMapper().updateById(sysAttachment);
    }

    /**
     * 系统附件分页查询
     *
     * @param sysAttachmentPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysAttachmentVo> page(SysAttachmentPageDto sysAttachmentPageDto);

    /**
     * 系统附件导出数据
     *
     * @param sysAttachmentPageDto 系统附件查询条件
     */
    void export(SysAttachmentPageDto sysAttachmentPageDto);

}
