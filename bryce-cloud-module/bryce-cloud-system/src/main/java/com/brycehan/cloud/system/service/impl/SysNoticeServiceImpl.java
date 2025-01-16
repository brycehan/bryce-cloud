package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.util.excel.ExcelUtils;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.common.server.common.IdGenerator;
import com.brycehan.cloud.system.entity.convert.SysNoticeConvert;
import com.brycehan.cloud.system.entity.dto.SysNoticeDto;
import com.brycehan.cloud.system.entity.dto.SysNoticePageDto;
import com.brycehan.cloud.system.entity.po.SysNotice;
import com.brycehan.cloud.system.entity.po.SysUser;
import com.brycehan.cloud.system.entity.vo.SysNoticeVo;
import com.brycehan.cloud.system.mapper.SysNoticeMapper;
import com.brycehan.cloud.system.service.SysNoticeService;
import com.brycehan.cloud.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统通知公告服务实现
 *
 * @since 2023/10/13
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysNoticeServiceImpl extends BaseServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {

    private final SysUserService sysUserService;

     /**
     * 添加系统通知公告
     *
     * @param sysNoticeDto 系统通知公告Dto
     */
    public void save(SysNoticeDto sysNoticeDto) {
        SysNotice sysNotice = SysNoticeConvert.INSTANCE.convert(sysNoticeDto);
        sysNotice.setId(IdGenerator.nextId());
        baseMapper.insert(sysNotice);
    }

    /**
     * 更新系统通知公告
     *
     * @param sysNoticeDto 系统通知公告Dto
     */
    public void update(SysNoticeDto sysNoticeDto) {
        SysNotice sysNotice = SysNoticeConvert.INSTANCE.convert(sysNoticeDto);
        baseMapper.updateById(sysNotice);
    }

    @Override
    public SysNoticeVo get(Long id) {
        SysNotice sysNotice = getById(id);
        SysNoticeVo sysNoticeVo = SysNoticeConvert.INSTANCE.convert(sysNotice);
        // 处理创建用户名称
        Map<Long, String> usernames = sysUserService.getUsernamesByIds(List.of(sysNoticeVo.getCreatedUserId()));
        sysNoticeVo.setCreatedUsername(usernames.get(sysNotice.getCreatedUserId()));
        return sysNoticeVo;
    }

    @Override
    public PageResult<SysNoticeVo> page(SysNoticePageDto sysNoticePageDto) {
        LambdaQueryWrapper<SysNotice> wrapper = getWrapper(sysNoticePageDto);

        // 创建者账号条件处理
        if (StrUtil.isNotBlank(sysNoticePageDto.getCreatedUsername())) {
            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(SysUser::getId).like(SysUser::getUsername, sysNoticePageDto.getCreatedUsername());
            List<SysUser> list = sysUserService.list(queryWrapper);

            if (CollUtil.isEmpty(list)) {
                // 没有匹配用户时返回空
                return new PageResult<>(0, new ArrayList<>(0));
            }

            wrapper.in(SysNotice::getCreatedUserId, list.stream().map(SysUser::getId).toList());
        }

        // 分页查询
        IPage<SysNotice> page = baseMapper.selectPage(sysNoticePageDto.toPage(), wrapper);
        List<SysNoticeVo> sysNoticeVoList = SysNoticeConvert.INSTANCE.convert(page.getRecords());

        // 处理创建用户名称
        Map<Long, String> usernames = sysUserService.getUsernamesByIds(sysNoticeVoList.stream().map(SysNoticeVo::getCreatedUserId).toList());
        sysNoticeVoList.forEach(sysNotice -> sysNotice.setCreatedUsername(usernames.get(sysNotice.getCreatedUserId())));

        return new PageResult<>(page.getTotal(), sysNoticeVoList);
    }

    /**
     * 封装查询条件
     *
     * @param sysNoticePageDto 系统通知公告分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<SysNotice> getWrapper(SysNoticePageDto sysNoticePageDto) {
        LambdaQueryWrapper<SysNotice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(sysNoticePageDto.getTitle()), SysNotice::getTitle, sysNoticePageDto.getTitle());
        wrapper.eq(Objects.nonNull(sysNoticePageDto.getType()), SysNotice::getType, sysNoticePageDto.getType());
        wrapper.eq(Objects.nonNull(sysNoticePageDto.getStatus()), SysNotice::getStatus, sysNoticePageDto.getStatus());

        return wrapper;
    }

    @Override
    public void export(SysNoticePageDto sysNoticePageDto) {
        List<SysNotice> sysNoticeList = baseMapper.selectList(getWrapper(sysNoticePageDto));
        List<SysNoticeVo> sysNoticeVoList = SysNoticeConvert.INSTANCE.convert(sysNoticeList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysNoticeVo.class, "系统通知公告_".concat(today), "系统通知公告", sysNoticeVoList);
    }

}
