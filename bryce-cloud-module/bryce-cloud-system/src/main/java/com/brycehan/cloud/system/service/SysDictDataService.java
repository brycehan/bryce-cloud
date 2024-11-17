package com.brycehan.cloud.system.service;

import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.mybatis.service.BaseService;
import com.brycehan.cloud.system.entity.dto.SysDictDataDto;
import com.brycehan.cloud.system.entity.dto.SysDictDataPageDto;
import com.brycehan.cloud.system.entity.po.SysDictData;
import com.brycehan.cloud.system.entity.vo.SysDictDataVo;

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
    void save(SysDictDataDto sysDictDataDto);

    /**
     * 更新系统字典数据
     *
     * @param sysDictDataDto 系统字典数据Dto
     */
    void update(SysDictDataDto sysDictDataDto);

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
