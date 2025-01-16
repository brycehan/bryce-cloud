package com.brycehan.cloud.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.brycehan.cloud.common.core.base.LoginUser;
import com.brycehan.cloud.common.core.base.LoginUserContext;
import com.brycehan.cloud.common.core.base.ServerException;
import com.brycehan.cloud.common.core.base.response.SystemResponseStatus;
import com.brycehan.cloud.common.core.constant.DataConstants;
import com.brycehan.cloud.common.core.entity.dto.IdsDto;
import com.brycehan.cloud.common.core.util.TreeUtils;
import com.brycehan.cloud.common.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.cloud.common.server.common.IdGenerator;
import com.brycehan.cloud.system.entity.convert.SysOrgConvert;
import com.brycehan.cloud.system.entity.dto.SysOrgDto;
import com.brycehan.cloud.system.entity.po.SysOrg;
import com.brycehan.cloud.system.entity.po.SysUser;
import com.brycehan.cloud.system.entity.vo.SysOrgVo;
import com.brycehan.cloud.system.mapper.SysOrgMapper;
import com.brycehan.cloud.system.service.SysOrgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统机构服务实现类
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysOrgServiceImpl extends BaseServiceImpl<SysOrgMapper, SysOrg> implements SysOrgService {

    /**
     * 添加系统机构
     *
     * @param sysOrgDto 系统机构Dto
     */
    public void save(SysOrgDto sysOrgDto) {
        SysOrg sysOrg = SysOrgConvert.INSTANCE.convert(sysOrgDto);
        sysOrg.setId(IdGenerator.nextId());
        baseMapper.insert(sysOrg);
    }

    @Override
    public void update(SysOrgDto sysOrgDto) {
        checkOrgDataScope(sysOrgDto.getId());
        SysOrg sysOrg = SysOrgConvert.INSTANCE.convert(sysOrgDto);

        // 上级机构不能为自身
        if (sysOrg.getId().equals(sysOrgDto.getParentId())) {
            throw new ServerException("上级机构不能为自身");
        }

        // 上级机构不能为下级
        List<Long> subOrgIds = getSubOrgIds(sysOrg.getId());
        if (subOrgIds.contains(sysOrg.getParentId())) {
            throw new ServerException("上级机构不能为下级");
        }

        baseMapper.updateById(sysOrg);
    }

    @Override
    @Transactional
    public void delete(IdsDto idsDto) {
        idsDto.getIds().forEach(this::checkOrgDataScope);
        // 判断是否有子机构
        long orgCount = this.count(new LambdaQueryWrapper<SysOrg>().in(SysOrg::getParentId, idsDto.getIds()));
        if (orgCount > 0) {
            throw new ServerException(SystemResponseStatus.ORG_EXIST_CHILDREN_CANNOT_BE_DELETED);
        }

        // 判断机构下面是否有用户
        Long userCount = Db.lambdaQuery(SysUser.class).in(SysUser::getOrgId, idsDto.getIds()).count();
        if (userCount > 0) {
            throw new ServerException(SystemResponseStatus.ORG_EXIST_CHILDREN_CANNOT_BE_DELETED);
        }

        // 删除
        baseMapper.deleteByIds(idsDto.getIds());
    }

    @Override
    public List<SysOrgVo> list(SysOrgDto sysOrgDto) {
        Map<String, Object> params = BeanUtil.beanToMap(sysOrgDto, false, false);
        // 数据权限过滤
        params.put(DataConstants.DATA_SCOPE, getDataScope("so", "id"));

        // 机构列表
        List<SysOrg> sysOrgList = baseMapper.list(params);
        return TreeUtils.build(SysOrgConvert.INSTANCE.convert(sysOrgList));
    }

    @Override
    public List<Long> getSubOrgIds(Long id) {
        if (id == null) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SysOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysOrg::getId, SysOrg::getParentId);

        // 所有机构的id、parentId列表
        List<SysOrg> orgList = baseMapper.selectList(queryWrapper);

        // 递归查询所有子机构IDs
        List<Long> subIds = new ArrayList<>();
        this.getTree(id, orgList, subIds);

        // 本机构也添加进去
        subIds.add(id);

        return subIds;
    }

    /**
     * 递归查询所有子机构ID列表
     *
     * @param id      当前机构ID
     * @param orgList 所有机构的列表
     * @param subIds  当前机构的子机构列表
     */
    private void getTree(Long id, List<SysOrg> orgList, List<Long> subIds) {
        for (SysOrg sysOrg : orgList) {
            if (sysOrg.getParentId().equals(id)) {
                this.getTree(sysOrg.getId(), orgList, subIds);
                subIds.add(sysOrg.getId());
            }
        }
    }

    @Override
    public String getOrgNameById(Long orgId) {
        if (orgId != null) {
            LambdaQueryWrapper<SysOrg> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(SysOrg::getName);
            queryWrapper.eq(SysOrg::getId, orgId);
            SysOrg sysOrg = baseMapper.selectOne(queryWrapper, false);
            if (sysOrg != null) {
                return sysOrg.getName();
            }
        }
        return "";
    }

    @Override
    public void checkOrgDataScope(Long orgId) {
        if (orgId == null || LoginUser.isSuperAdmin(LoginUserContext.currentUser())) {
            return;
        }

        LambdaQueryWrapper<SysOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOrg::getId, orgId);

        dataScopeWrapper(queryWrapper);
        if (!baseMapper.exists(queryWrapper)) {
            throw new RuntimeException("没有权限访问用户数据");
        }
    }
}
