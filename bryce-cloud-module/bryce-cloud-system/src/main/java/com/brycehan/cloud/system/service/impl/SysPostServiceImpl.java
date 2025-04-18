package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.entity.dto.IdsDto;
import com.brycehan.cloud.common.core.enums.StatusType;
import com.brycehan.cloud.common.core.util.excel.ExcelUtils;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.common.server.common.IdGenerator;
import com.brycehan.cloud.system.entity.convert.SysPostConvert;
import com.brycehan.cloud.system.entity.dto.SysPostCodeDto;
import com.brycehan.cloud.system.entity.dto.SysPostDto;
import com.brycehan.cloud.system.entity.dto.SysPostPageDto;
import com.brycehan.cloud.system.entity.po.SysPost;
import com.brycehan.cloud.system.entity.vo.SysPostVo;
import com.brycehan.cloud.system.mapper.SysPostMapper;
import com.brycehan.cloud.system.service.SysPostService;
import com.brycehan.cloud.system.service.SysUserPostService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    /**
     * 添加系统岗位
     *
     * @param sysPostDto 系统岗位Dto
     */
    public void save(SysPostDto sysPostDto) {
        SysPost sysPost = SysPostConvert.INSTANCE.convert(sysPostDto);
        sysPost.setId(IdGenerator.nextId());
        baseMapper.insert(sysPost);
    }

    /**
     * 更新系统岗位
     *
     * @param sysPostDto 系统岗位Dto
     */
    public void update(SysPostDto sysPostDto) {
        SysPost sysPost = SysPostConvert.INSTANCE.convert(sysPostDto);
        baseMapper.updateById(sysPost);
    }

    @Override
    public void delete(IdsDto idsDto) {
        // 删除岗位
        baseMapper.deleteByIds(idsDto.getIds());

        // 删除岗位用户关系
        sysUserPostService.deleteByPostIds(idsDto.getIds());
    }

    @Override
    public PageResult<SysPostVo> page(SysPostPageDto sysPostPageDto) {
        IPage<SysPost> page = baseMapper.selectPage(sysPostPageDto.toPage(), getWrapper(sysPostPageDto));
        return PageResult.of(SysPostConvert.INSTANCE.convert(page.getRecords()), page.getTotal());
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
        List<SysPost> sysPostList = baseMapper.selectList(getWrapper(sysPostPageDto));
        List<SysPostVo> sysPostVoList = SysPostConvert.INSTANCE.convert(sysPostList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysPostVo.class, "岗位数据_".concat(today), "岗位数据", sysPostVoList);
    }

    @Override
    public List<SysPostVo> list(SysPostPageDto sysPostPageDto) {
        // 正常岗位列表
        sysPostPageDto.setStatus(StatusType.ENABLE);
        List<SysPost> sysPostList = baseMapper.selectList(getWrapper(sysPostPageDto));

        return SysPostConvert.INSTANCE.convert(sysPostList);
    }

    @Override
    public List<String> getPostNameList(List<Long> postIdList) {
        if (CollUtil.isEmpty(postIdList)) {
            return List.of();
        }

        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysPost::getName);
        queryWrapper.in(SysPost::getId, postIdList);
        return baseMapper.selectList(queryWrapper).stream().map(SysPost::getName).toList();
    }

    @Override
    public boolean checkPostCodeUnique(SysPostCodeDto sysPostCodeDto) {
        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysPost::getCode, SysPost::getId)
                .eq(SysPost::getCode, sysPostCodeDto.getCode());
        SysPost sysPost = baseMapper.selectOne(queryWrapper, false);

        // 修改时，同岗位编码同ID为编码唯一
        return Objects.isNull(sysPost) || Objects.equals(sysPostCodeDto.getId(), sysPost.getId());
    }

}
