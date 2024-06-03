package com.brycehan.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.common.core.base.dto.IdsDto;
import com.brycehan.cloud.common.core.base.entity.PageResult;
import com.brycehan.cloud.common.core.enums.DataStatusType;
import com.brycehan.cloud.common.core.util.DateTimeUtils;
import com.brycehan.cloud.common.core.util.ExcelUtils;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.system.entity.convert.SysPostConvert;
import com.brycehan.cloud.system.entity.dto.SysPostPageDto;
import com.brycehan.cloud.system.entity.po.SysPost;
import com.brycehan.cloud.system.entity.vo.SysPostVo;
import com.brycehan.cloud.system.mapper.SysPostMapper;
import com.brycehan.cloud.system.service.SysPostService;
import com.brycehan.cloud.system.service.SysUserPostService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * 系统岗位服务实现
 *
 * @since 2022/10/31
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysPostServiceImpl extends BaseServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    private final SysUserPostService sysUserPostService;

    @Override
    public void delete(IdsDto idsDto) {
        // 过滤无效参数
        List<Long> ids = idsDto.getIds().stream()
                .filter(Objects::nonNull).toList();
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        // 删除岗位
        this.baseMapper.deleteBatchIds(ids);

        // 删除岗位用户关系
        this.sysUserPostService.deleteByPostIds(ids);
    }

    @Override
    public PageResult<SysPostVo> page(SysPostPageDto sysPostPageDto) {
        IPage<SysPost> page = this.baseMapper.selectPage(getPage(sysPostPageDto), getWrapper(sysPostPageDto));
        return new PageResult<>(page.getTotal(), SysPostConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysPostPageDto 系统岗位分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysPost> getWrapper(SysPostPageDto sysPostPageDto) {
        LambdaQueryWrapper<SysPost> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysPostPageDto.getStatus()), SysPost::getStatus, sysPostPageDto.getStatus());
        wrapper.like(StringUtils.isNotEmpty(sysPostPageDto.getName()), SysPost::getName, sysPostPageDto.getName());
        wrapper.like(StringUtils.isNotEmpty(sysPostPageDto.getCode()), SysPost::getCode, sysPostPageDto.getCode());

        return wrapper;
    }

    @Override
    public void export(SysPostPageDto sysPostPageDto) {
        List<SysPost> sysPostList = this.baseMapper.selectList(getWrapper(sysPostPageDto));
        List<SysPostVo> sysPostVoList = SysPostConvert.INSTANCE.convert(sysPostList);
        ExcelUtils.export(SysPostVo.class, "系统岗位_".concat(DateTimeUtils.today()), "系统岗位", sysPostVoList);
    }

    @Override
    public List<SysPostVo> list(SysPostPageDto sysPostPageDto) {
        // 正常岗位列表
        sysPostPageDto.setStatus(DataStatusType.ENABLE.isValue());
        List<SysPost> sysPostList = this.baseMapper.selectList(getWrapper(sysPostPageDto));

        return SysPostConvert.INSTANCE.convert(sysPostList);
    }

    @Override
    public List<String> getPostNameList(List<Long> postIdList) {

        if (CollectionUtils.isNotEmpty(postIdList)) {
            return this.baseMapper.selectList(new LambdaQueryWrapper<SysPost>().in(SysPost::getId, postIdList))
                    .stream().map(SysPost::getName).toList();
        }
        return List.of();
    }

}
