package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.util.ExcelUtils;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.common.server.common.IdGenerator;
import com.brycehan.cloud.system.entity.convert.SysDictDataConvert;
import com.brycehan.cloud.system.entity.dto.SysDictDataDto;
import com.brycehan.cloud.system.entity.dto.SysDictDataPageDto;
import com.brycehan.cloud.system.entity.po.SysDictData;
import com.brycehan.cloud.system.entity.vo.SysDictDataVo;
import com.brycehan.cloud.system.mapper.SysDictDataMapper;
import com.brycehan.cloud.system.service.SysDictDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 系统字典数据服务实现类
 *
 * @since 2023/09/08
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    /**
     * 添加系统字典数据
     *
     * @param sysDictDataDto 系统字典数据Dto
     */
    public void save(SysDictDataDto sysDictDataDto) {
        SysDictData sysDictData = SysDictDataConvert.INSTANCE.convert(sysDictDataDto);
        sysDictData.setId(IdGenerator.nextId());
        this.baseMapper.insert(sysDictData);
    }

    /**
     * 更新系统字典数据
     *
     * @param sysDictDataDto 系统字典数据Dto
     */
    public void update(SysDictDataDto sysDictDataDto) {
        SysDictData sysDictData = SysDictDataConvert.INSTANCE.convert(sysDictDataDto);
        this.baseMapper.updateById(sysDictData);
    }

    @Override
    public PageResult<SysDictDataVo> page(SysDictDataPageDto sysDictDataPageDto) {
        IPage<SysDictData> page = this.baseMapper.selectPage(sysDictDataPageDto.toPage(), getWrapper(sysDictDataPageDto));
        return new PageResult<>(page.getTotal(), SysDictDataConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysDictDataPageDto 系统字典数据分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysDictData> getWrapper(SysDictDataPageDto sysDictDataPageDto) {
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysDictDataPageDto.getDictTypeId()), SysDictData::getDictTypeId, sysDictDataPageDto.getDictTypeId());
        wrapper.eq(Objects.nonNull(sysDictDataPageDto.getStatus()), SysDictData::getStatus, sysDictDataPageDto.getStatus());

        return wrapper;
    }

    @Override
    public void export(SysDictDataPageDto sysDictDataPageDto) {
        List<SysDictData> sysDictDataList = this.baseMapper.selectList(getWrapper(sysDictDataPageDto));
        List<SysDictDataVo> sysDictDataVoList = SysDictDataConvert.INSTANCE.convert(sysDictDataList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysDictDataVo.class, "系统字典数据" + today, "系统字典数据", sysDictDataVoList);
    }

}
