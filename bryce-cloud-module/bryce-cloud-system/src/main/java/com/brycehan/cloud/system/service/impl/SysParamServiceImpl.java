package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.cloud.common.base.dto.IdsDto;
import com.brycehan.cloud.common.base.entity.PageResult;
import com.brycehan.cloud.common.base.id.IdGenerator;
import com.brycehan.cloud.common.constant.CacheConstants;
import com.brycehan.cloud.common.util.DateTimeUtils;
import com.brycehan.cloud.common.util.ExcelUtils;
import com.brycehan.cloud.common.util.JsonUtils;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.system.convert.SysParamConvert;
import com.brycehan.cloud.system.dto.SysParamDto;
import com.brycehan.cloud.system.dto.SysParamPageDto;
import com.brycehan.cloud.system.entity.SysParam;
import com.brycehan.cloud.system.mapper.SysParamMapper;
import com.brycehan.cloud.system.service.SysParamService;
import com.brycehan.cloud.system.vo.SysParamVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
        boolean exists = this.baseMapper.exists(sysParamDto.getParamKey());
        if (exists) {
            throw new RuntimeException("参数键已存在");
        }

        SysParam sysParam = SysParamConvert.INSTANCE.convert(sysParamDto);
        sysParam.setId(IdGenerator.nextId());
        this.baseMapper.insert(sysParam);

        // 保存到缓存
        this.stringRedisTemplate.opsForHash()
                .put(CacheConstants.SYSTEM_PARAM_KEY, sysParam.getParamKey(), sysParam.getParamValue());
    }

    @Override
    public void update(SysParamDto sysParamDto) {
        SysParam entity = this.baseMapper.selectById(sysParamDto.getId());

        // 如果参数键修改过
        if (!StrUtil.equalsIgnoreCase(entity.getParamKey(), sysParamDto.getParamKey())) {
            // 判断新参数键是否存在
            boolean exists = this.baseMapper.exists(sysParamDto.getParamKey());
            if (exists) {
                throw new RuntimeException("参数键已存在");
            }

            // 删除修改前的缓存
            this.stringRedisTemplate.opsForHash()
                    .delete(CacheConstants.SYSTEM_PARAM_KEY, entity.getParamKey());
        }

        // 修改数据
        SysParam sysParam = SysParamConvert.INSTANCE.convert(sysParamDto);
        this.baseMapper.updateById(sysParam);

        // 保存到缓存
        this.stringRedisTemplate.opsForHash()
                .put(CacheConstants.SYSTEM_PARAM_KEY, sysParam.getParamKey(), sysParam.getParamValue());
    }

    @Override
    public void delete(IdsDto idsDto) {
        // 过滤无效参数
        List<Long> ids = idsDto.getIds().stream()
                .filter(Objects::nonNull)
                .toList();
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        // 查询列表
        List<SysParam> sysParams = this.baseMapper.selectBatchIds(ids);

        // 删除数据
        this.baseMapper.deleteBatchIds(ids);

        // 删除缓存
        Object[] paramKeys = sysParams.stream().map(SysParam::getParamKey)
                .filter(StrUtil::isNotBlank).toArray();
        if(ArrayUtil.isNotEmpty(paramKeys)) {
            this.stringRedisTemplate.opsForHash()
                    .delete(CacheConstants.SYSTEM_PARAM_KEY, paramKeys);
        }
    }

    @Override
    public PageResult<SysParamVo> page(SysParamPageDto sysParamPageDto) {
        IPage<SysParam> page = this.baseMapper.selectPage(getPage(sysParamPageDto), getWrapper(sysParamPageDto));
        return new PageResult<>(page.getTotal(), SysParamConvert.INSTANCE.convert(page.getRecords()));
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
        wrapper.eq(StringUtils.isNotEmpty(sysParamPageDto.getBuiltIn()), SysParam::getBuiltIn, sysParamPageDto.getBuiltIn());
        wrapper.eq(Objects.nonNull(sysParamPageDto.getTenantId()), SysParam::getTenantId, sysParamPageDto.getTenantId());

        if (sysParamPageDto.getCreatedTimeStart() != null && sysParamPageDto.getCreatedTimeEnd() != null) {
            wrapper.between(SysParam::getCreatedTime, sysParamPageDto.getCreatedTimeStart(), sysParamPageDto.getCreatedTimeEnd());
        } else if (sysParamPageDto.getCreatedTimeStart() != null) {
            wrapper.ge(SysParam::getCreatedTime, sysParamPageDto.getCreatedTimeStart());
        } else if (sysParamPageDto.getCreatedTimeEnd() != null) {
            wrapper.ge(SysParam::getCreatedTime, sysParamPageDto.getCreatedTimeEnd());
        }

        return wrapper;
    }

    @Override
    public void export(SysParamPageDto sysParamPageDto) {
        List<SysParam> sysParamList = this.baseMapper.selectList(getWrapper(sysParamPageDto));
        List<SysParamVo> sysParamVoList = SysParamConvert.INSTANCE.convert(sysParamList);
        ExcelUtils.export(SysParamVo.class, "系统参数_".concat(DateTimeUtils.today()), "系统参数", sysParamVoList);
    }

    @Override
    public boolean exists(String paramKey) {
        // 判断新参数键是否存在
        return this.baseMapper.exists(paramKey);
    }

    @Override
    public SysParamVo getByParamKey(String paramKey) {
        SysParam sysParam = this.baseMapper.selectOne(paramKey);
        return SysParamConvert.INSTANCE.convert(sysParam);
    }

    @Override
    public String getString(String paramKey) {
        // 从缓存中查询
        String paramValue = (String) this.stringRedisTemplate.opsForHash().get(CacheConstants.SYSTEM_PARAM_KEY, paramKey);
        if (StringUtils.isNotEmpty(paramValue)) {
            return paramValue;
        }

        // 缓存没有时，从数据库中查询
        SysParam sysParam = this.baseMapper.selectOne(paramKey);
        if (Objects.isNull(sysParam)) {
            throw new RuntimeException("参数值不存在，paramKey：".concat(paramKey));
        }

        // 添加到缓存中
        this.stringRedisTemplate.opsForHash().put(CacheConstants.SYSTEM_PARAM_KEY, sysParam.getParamKey(), sysParam.getParamValue());

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

}
