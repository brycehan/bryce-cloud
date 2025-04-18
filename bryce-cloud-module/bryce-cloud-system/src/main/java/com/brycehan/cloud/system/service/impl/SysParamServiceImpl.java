package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.constant.CacheConstants;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.entity.dto.IdsDto;
import com.brycehan.cloud.common.core.util.excel.ExcelUtils;
import com.brycehan.cloud.common.core.util.JsonUtils;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.common.server.common.IdGenerator;
import com.brycehan.cloud.system.entity.convert.SysParamConvert;
import com.brycehan.cloud.system.entity.dto.SysParamDto;
import com.brycehan.cloud.system.entity.dto.SysParamKeyDto;
import com.brycehan.cloud.system.entity.dto.SysParamPageDto;
import com.brycehan.cloud.system.entity.po.SysParam;
import com.brycehan.cloud.system.entity.vo.SysParamVo;
import com.brycehan.cloud.system.mapper.SysParamMapper;
import com.brycehan.cloud.system.service.SysParamService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 系统参数服务实现
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysParamServiceImpl extends BaseServiceImpl<SysParamMapper, SysParam> implements SysParamService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void save(SysParamDto sysParamDto) {
        // 判断参数键是否存在
        boolean exists = baseMapper.exists(sysParamDto.getParamKey());
        if (exists) {
            throw new ServerException("参数键已存在");
        }

        SysParam sysParam = SysParamConvert.INSTANCE.convert(sysParamDto);
        sysParam.setId(IdGenerator.nextId());
        baseMapper.insert(sysParam);

        // 保存到缓存
        stringRedisTemplate.opsForHash()
                .put(CacheConstants.SYSTEM_PARAM_KEY, sysParam.getParamKey(), sysParam.getParamValue());
    }

    @Override
    public void update(SysParamDto sysParamDto) {
        SysParam entity = baseMapper.selectById(sysParamDto.getId());

        // 如果参数键修改过
        if (!StrUtil.equalsIgnoreCase(entity.getParamKey(), sysParamDto.getParamKey())) {
            // 判断新参数键是否存在
            boolean exists = baseMapper.exists(sysParamDto.getParamKey());
            if (exists) {
                throw new ServerException("参数键已存在");
            }

            // 删除修改前的缓存
            stringRedisTemplate.opsForHash()
                    .delete(CacheConstants.SYSTEM_PARAM_KEY, entity.getParamKey());
        }

        // 修改数据
        SysParam sysParam = SysParamConvert.INSTANCE.convert(sysParamDto);
        baseMapper.updateById(sysParam);

        // 保存到缓存
        stringRedisTemplate.opsForHash()
                .put(CacheConstants.SYSTEM_PARAM_KEY, sysParam.getParamKey(), sysParam.getParamValue());
    }

    @Override
    public void delete(IdsDto idsDto) {
        // 查询列表
        List<SysParam> sysParams = baseMapper.selectByIds(idsDto.getIds());

        // 删除数据
        baseMapper.deleteByIds(idsDto.getIds());

        // 删除缓存
        List<String> paramKeys = sysParams.stream().map(SysParam::getParamKey)
                .filter(StrUtil::isNotBlank).toList();
        if(ArrayUtil.isNotEmpty(paramKeys)) {
            stringRedisTemplate.opsForHash()
                    .delete(CacheConstants.SYSTEM_PARAM_KEY, paramKeys);
        }
    }

    @Override
    public PageResult<SysParamVo> page(SysParamPageDto sysParamPageDto) {
        IPage<SysParam> page = baseMapper.selectPage(sysParamPageDto.toPage(), getWrapper(sysParamPageDto));
        return PageResult.of(SysParamConvert.INSTANCE.convert(page.getRecords()), page.getTotal());
    }

    /**
     * 封装查询条件
     *
     * @param sysParamPageDto 系统参数分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysParam> getWrapper(SysParamPageDto sysParamPageDto) {
        LambdaQueryWrapper<SysParam> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(sysParamPageDto.getParamName()), SysParam::getParamName, sysParamPageDto.getParamName());
        wrapper.like(StringUtils.isNotEmpty(sysParamPageDto.getParamKey()), SysParam::getParamKey, sysParamPageDto.getParamKey());
        wrapper.eq(sysParamPageDto.getParamType() != null, SysParam::getParamType, sysParamPageDto.getParamType());
        addTimeRangeCondition(wrapper, SysParam::getCreatedTime, sysParamPageDto.getCreatedTimeStart(), sysParamPageDto.getCreatedTimeEnd());

        return wrapper;
    }

    @Override
    public void export(SysParamPageDto sysParamPageDto) {
        List<SysParam> sysParamList = baseMapper.selectList(getWrapper(sysParamPageDto));
        List<SysParamVo> sysParamVoList = SysParamConvert.INSTANCE.convert(sysParamList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysParamVo.class, "参数数据_".concat(today), "参数数据", sysParamVoList);
    }

    @Override
    public boolean exists(String paramKey) {
        // 判断新参数键是否存在
        return baseMapper.exists(paramKey);
    }

    @Override
    public SysParamVo getByParamKey(String paramKey) {
        SysParam sysParam = baseMapper.selectOne(paramKey);
        return SysParamConvert.INSTANCE.convert(sysParam);
    }

    @Override
    public String getString(String paramKey) {
        // 从缓存中查询
        String paramValue = (String) stringRedisTemplate.opsForHash().get(CacheConstants.SYSTEM_PARAM_KEY, paramKey);
        if (StringUtils.isNotEmpty(paramValue)) {
            return paramValue;
        }

        // 缓存没有时，从数据库中查询
        SysParam sysParam = baseMapper.selectOne(paramKey);
        if (Objects.isNull(sysParam)) {
            throw new ServerException("参数值不存在，paramKey：".concat(paramKey));
        }

        // 添加到缓存中
        stringRedisTemplate.opsForHash().put(CacheConstants.SYSTEM_PARAM_KEY, sysParam.getParamKey(), sysParam.getParamValue());

        return sysParam.getParamValue();
    }

    @Override
    public Integer getInteger(String paramKey) {
        String value = getString(paramKey);
        return Integer.parseInt(value);
    }

    @Override
    public boolean getBoolean(String paramKey) {
        String value = getString(paramKey);
        return Boolean.parseBoolean(value);
    }

    @Override
    public <T> T getJSONObject(String paramKey, Class<T> valueType) {
        String value = getString(paramKey);
        return JsonUtils.readValue(value, valueType);
    }

    @Override
    public boolean checkParamKeyUnique(SysParamKeyDto sysParamKeyDto) {
        LambdaQueryWrapper<SysParam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysParam::getParamKey, SysParam::getId)
                .eq(SysParam::getParamKey, sysParamKeyDto.getParamKey());
        SysParam sysParam = baseMapper.selectOne(queryWrapper, false);

        // 修改时，同参数键名同ID为编码唯一
        return Objects.isNull(sysParam) || Objects.equals(sysParamKeyDto.getId(), sysParam.getId());
    }

}
