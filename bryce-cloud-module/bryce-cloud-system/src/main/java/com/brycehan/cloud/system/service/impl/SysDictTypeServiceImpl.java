package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.util.excel.ExcelUtils;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.common.server.common.IdGenerator;
import com.brycehan.cloud.system.entity.convert.SysDictTypeConvert;
import com.brycehan.cloud.system.entity.dto.SysDictTypeCodeDto;
import com.brycehan.cloud.system.entity.dto.SysDictTypeDto;
import com.brycehan.cloud.system.entity.dto.SysDictTypePageDto;
import com.brycehan.cloud.system.entity.po.SysDictData;
import com.brycehan.cloud.system.entity.po.SysDictType;
import com.brycehan.cloud.system.entity.vo.SysDictTypeVo;
import com.brycehan.cloud.system.entity.vo.SysDictVo;
import com.brycehan.cloud.system.mapper.SysDictTypeMapper;
import com.brycehan.cloud.system.service.SysDictDataService;
import com.brycehan.cloud.system.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 系统字典类型服务实现类
 *
 * @since 2023/09/05
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    private final SysDictDataService sysDictDataService;

     /**
     * 添加系统字典类型
     *
     * @param sysDictTypeDto 系统字典类型Dto
     */
    public void save(SysDictTypeDto sysDictTypeDto) {
        SysDictType sysDictType = SysDictTypeConvert.INSTANCE.convert(sysDictTypeDto);
        sysDictType.setId(IdGenerator.nextId());
        baseMapper.insert(sysDictType);
    }

    /**
     * 更新系统字典类型
     *
     * @param sysDictTypeDto 系统字典类型Dto
     */
    public void update(SysDictTypeDto sysDictTypeDto) {
        SysDictType sysDictType = SysDictTypeConvert.INSTANCE.convert(sysDictTypeDto);
        baseMapper.updateById(sysDictType);
    }

    @Override
    public PageResult<SysDictTypeVo> page(SysDictTypePageDto sysDictTypePageDto) {
        IPage<SysDictType> page = baseMapper.selectPage(sysDictTypePageDto.toPage(), getWrapper(sysDictTypePageDto));
        return PageResult.of(SysDictTypeConvert.INSTANCE.convert(page.getRecords()), page.getTotal());
    }

    /**
     * 封装查询条件
     *
     * @param sysDictTypePageDto 系统字典类型分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysDictType> getWrapper(SysDictTypePageDto sysDictTypePageDto) {
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysDictTypePageDto.getStatus()), SysDictType::getStatus, sysDictTypePageDto.getStatus());
        wrapper.like(StringUtils.isNotEmpty(sysDictTypePageDto.getDictName()), SysDictType::getDictName, sysDictTypePageDto.getDictName());
        wrapper.like(StringUtils.isNotEmpty(sysDictTypePageDto.getDictType()), SysDictType::getDictType, sysDictTypePageDto.getDictType());
        addTimeRangeCondition(wrapper, SysDictType::getCreatedTime, sysDictTypePageDto.getCreatedTimeStart(), sysDictTypePageDto.getCreatedTimeEnd());

        return wrapper;
    }

    @Override
    public void export(SysDictTypePageDto sysDictTypePageDto) {
        List<SysDictType> sysDictTypeList = baseMapper.selectList(getWrapper(sysDictTypePageDto));
        List<SysDictTypeVo> sysDictTypeVoList = SysDictTypeConvert.INSTANCE.convert(sysDictTypeList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysDictTypeVo.class, "字典类型_" + today, "字典类型", sysDictTypeVoList);
    }

    @Override
    public List<SysDictVo> dictList() {
        // 全部字典类型列表
        List<SysDictType> typeList = baseMapper.selectList(Wrappers.emptyWrapper());
        // 全部字典数据列表
        List<SysDictData> dataList = sysDictDataService.list(new LambdaQueryWrapper<SysDictData>()
                .orderByAsc(SysDictData::getSort));

        // 全部字典列表
        List<SysDictVo> dictVoList = new ArrayList<>(typeList.size());
        for (SysDictType sysDictType : typeList) {
            SysDictVo sysDictVo = new SysDictVo();
            sysDictVo.setDictType(sysDictType.getDictType());

            List<SysDictVo.DictData> list = dataList.stream()
                    .filter(data -> sysDictType.getId().equals(data.getDictTypeId()))
                    .map(data -> new SysDictVo.DictData(data.getDictLabel(), data.getDictValue(), data.getLabelClass()))
                    .toList();

            sysDictVo.getDatalist().addAll(list);
            dictVoList.add(sysDictVo);
        }
        return dictVoList;
    }

    @Override
    public boolean checkDictTypeCodeUnique(SysDictTypeCodeDto sysDictTypeCodeDto) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysDictType::getDictType, SysDictType::getId)
                .eq(SysDictType::getDictType, sysDictTypeCodeDto.getDictType());
        SysDictType sysDictType = baseMapper.selectOne(queryWrapper, false);

        // 修改时，同字典类型编码同ID为编码唯一
        return Objects.isNull(sysDictType) || Objects.equals(sysDictTypeCodeDto.getId(), sysDictType.getId());
    }

}
