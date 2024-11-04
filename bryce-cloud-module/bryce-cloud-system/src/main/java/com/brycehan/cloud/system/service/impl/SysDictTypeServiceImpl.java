package com.brycehan.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.brycehan.cloud.common.core.base.DictTransService;
import com.brycehan.cloud.common.core.constant.CacheConstants;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.util.DateTimeUtils;
import com.brycehan.cloud.common.core.util.ExcelUtils;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.system.entity.convert.SysDictTypeConvert;
import com.brycehan.cloud.system.entity.dto.SysDictTypeCodeDto;
import com.brycehan.cloud.system.entity.dto.SysDictTypePageDto;
import com.brycehan.cloud.system.entity.po.SysDictData;
import com.brycehan.cloud.system.entity.po.SysDictType;
import com.brycehan.cloud.system.entity.vo.SysDictTypeVo;
import com.brycehan.cloud.system.entity.vo.SysDictVo;
import com.brycehan.cloud.system.mapper.SysDictDataMapper;
import com.brycehan.cloud.system.mapper.SysDictTypeMapper;
import com.brycehan.cloud.system.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 系统字典类型服务实现类
 *
 * @since 2023/09/05
 * @author Bryce Han
 */
@Service
@SuppressWarnings("all")
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService, DictTransService, InitializingBean {

    private final SysDictDataMapper sysDictDataMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final Map<String, String> localCacheMap = new ConcurrentHashMap<>();

    @Override
    public PageResult<SysDictTypeVo> page(SysDictTypePageDto sysDictTypePageDto) {
        IPage<SysDictType> page = this.baseMapper.selectPage(getPage(sysDictTypePageDto), getWrapper(sysDictTypePageDto));
        return new PageResult<>(page.getTotal(), SysDictTypeConvert.INSTANCE.convert(page.getRecords()));
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
        List<SysDictType> sysDictTypeList = this.baseMapper.selectList(getWrapper(sysDictTypePageDto));
        List<SysDictTypeVo> sysDictTypeVoList = SysDictTypeConvert.INSTANCE.convert(sysDictTypeList);
        ExcelUtils.export(SysDictTypeVo.class, "系统字典类型_" + DateTimeUtils.today(), "系统字典类型", sysDictTypeVoList);
    }

    @Override
    public List<SysDictVo> dictList() {
        // 全部字典类型列表
        List<SysDictType> typeList = this.baseMapper.selectList(Wrappers.emptyWrapper());

        // 全部字典数据列表
        List<SysDictData> dataList = this.sysDictDataMapper.selectList(new LambdaQueryWrapper<SysDictData>()
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
        SysDictType sysDictType = this.baseMapper.selectOne(queryWrapper, false);

        // 修改时，同字典类型编码同ID为编码唯一
        return Objects.isNull(sysDictType) || Objects.equals(sysDictTypeCodeDto.getId(), sysDictType.getId());
    }

    @Override
    public void afterPropertiesSet() {
        refreshTransCache();
    }

    /**
     * 刷新字典翻译缓存
     */
    public void refreshTransCache() {
        // 异步不阻塞主线程，不会增加启动用时
        CompletableFuture.runAsync(() -> {
            // 获取所有的字典项数据
            List<SysDictData> dataList = this.sysDictDataMapper.selectList(new LambdaQueryWrapper<>());
            // 根据类型分组
            Map<Long, List<SysDictData>> dictTypeDataMap = dataList.stream().collect(Collectors
                    .groupingBy(SysDictData::getDictTypeId));

            List<SysDictType> dictTypeList = super.list();
            for (SysDictType dictType : dictTypeList) {
                List<SysDictData> dataListByType = dictTypeDataMap.get(dictType.getId());
                if (dataListByType == null) {
                    continue;
                }
                Map<String, String> dictTypeMap = dictTypeDataMap.get(dictType.getId()).stream().collect(Collectors
                        .toMap(SysDictData::getDictValue, SysDictData::getDictLabel));

                this.refreshTransDataCache(dictType.getDictType(), dictTypeMap);
            }
        });
    }

    /**
     * 刷新字典类型缓存
     *
     * @param dictTypeCode 字典类型编码
     * @param dictMap       字典数据
     */
    private void refreshTransDataCache(String dictTypeCode, Map<String, String> dictMap) {
        dictMap.keySet().forEach(key -> {
            this.set(CacheConstants.SYSTEM_DICT_TRANS_KEY + dictTypeCode + "_" + key, dictMap.get(key));
            this.set(CacheConstants.SYSTEM_DICT_UN_TRANS_KEY + dictTypeCode + "_" + dictMap.get(key), key);
        });
    }

    @Override
    public Map<String, String> getDictTransMap() {
        return localCacheMap;
    }

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        DictTransService.super.set(key, value);
    }

}
