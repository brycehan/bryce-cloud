package com.brycehan.cloud.system.service;

import com.brycehan.cloud.common.base.entity.PageResult;
import com.brycehan.cloud.common.base.id.IdGenerator;
import com.brycehan.cloud.framework.mybatis.service.BaseService;
import com.brycehan.cloud.system.convert.SysDictDataConvert;
import com.brycehan.cloud.system.dto.SysDictDataDto;
import com.brycehan.cloud.system.dto.SysDictDataPageDto;
import com.brycehan.cloud.system.entity.SysDictData;
import com.brycehan.cloud.system.vo.SysDictDataVo;

/**
 * 系统字典数据服务
 *
 * @since 2023/09/08
 * @author Bryce Han
 */
public interface SysDictDataService extends BaseService<SysDictData> {

    /**
     * 添加系统字典数据
     *
     * @param sysDictDataDto 系统字典数据Dto
     */
    default void save(SysDictDataDto sysDictDataDto) {
        SysDictData sysDictData = SysDictDataConvert.INSTANCE.convert(sysDictDataDto);
        sysDictData.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(sysDictData);
    }

    /**
     * 更新系统字典数据
     *
     * @param sysDictDataDto 系统字典数据Dto
     */
    default void update(SysDictDataDto sysDictDataDto) {
        SysDictData sysDictData = SysDictDataConvert.INSTANCE.convert(sysDictDataDto);
        this.getBaseMapper().updateById(sysDictData);
    }

    /**
     * 系统字典数据分页查询
     *
     * @param sysDictDataPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysDictDataVo> page(SysDictDataPageDto sysDictDataPageDto);

    /**
     * 系统字典数据导出数据
     *
     * @param sysDictDataPageDto 系统字典数据查询条件
     */
    void export(SysDictDataPageDto sysDictDataPageDto);

}
