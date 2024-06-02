package com.brycehan.cloud.system.service;

import com.brycehan.cloud.common.core.base.dto.SysUserAvatarDto;
import com.brycehan.cloud.common.core.base.dto.SysUserInfoDto;
import com.brycehan.cloud.common.core.base.entity.PageResult;
import com.brycehan.cloud.common.core.base.id.IdGenerator;
import com.brycehan.cloud.common.mybatis.service.BaseService;
import com.brycehan.cloud.system.entity.convert.SysUserConvert;
import com.brycehan.cloud.system.entity.dto.*;
import com.brycehan.cloud.system.entity.po.SysUser;
import com.brycehan.cloud.system.entity.vo.SysUserVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统用户服务
 *
 * @since 2022/5/08
 * @author Bryce Han
 */
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 添加系统用户
     *
     * @param sysUserDto 系统用户Dto
     */
    default void save(SysUserDto sysUserDto) {
        SysUser sysUser = SysUserConvert.INSTANCE.convert(sysUserDto);
        sysUser.setId(IdGenerator.nextId());

        this.getBaseMapper().insert(sysUser);
    }

    /**
     * 更新系统用户
     *
     * @param sysUserDto 系统用户Dto
     */
    default void update(SysUserDto sysUserDto) {
        SysUser sysUser = SysUserConvert.INSTANCE.convert(sysUserDto);
        this.getBaseMapper().updateById(sysUser);
    }

    /**
     * 系统用户分页查询
     *
     * @param sysUserPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysUserVo> page(SysUserPageDto sysUserPageDto);

    /**
     * 系统用户导出数据
     *
     * @param sysUserPageDto 系统用户查询条件
     */
    void export(SysUserPageDto sysUserPageDto);

    /**
     * 批量导入用户
     *
     * @param file Excel 文件
     * @param password 初始密码
     */
    void importByExcel(MultipartFile file, String password);

    /**
     * 根据手机号码查询用户
     *
     * @param phone 手机号码
     * @return 系统用户
     */
    SysUserVo getByPhone(String phone);

    /**
     * 角色分配用户，用户列表
     *
     * @param pageDto 查询条件
     * @return 用户列表
     */
    PageResult<SysUserVo> roleUserPage(SysRoleUserPageDto pageDto);

    /**
     * 注册用户
     *
     * @param sysUser 用户
     */
    SysUser registerUser(SysUser sysUser);

    /**
     * 校验用户账号是否唯一
     *
     * @param sysUser 用户
     * @return 结果，true唯一
     */
    boolean checkUsernameUnique(SysUser sysUser);

    /**
     * 校验用户手机号是否唯一
     *
     * @param sysUser 用户
     * @return 结果，true唯一
     */
    boolean checkPhoneUnique(SysUser sysUser);

    /**
     * 校验用户邮箱是否唯一
     *
     * @param sysUser 用户
     * @return 结果，true唯一
     */
    boolean checkEmailUnique(SysUser sysUser);

    /**
     * 校验用户是否允许操作（admin放行，普通用户不能操作admin账号）
     *
     * @param sysUser 系统用户信息
     */
    void checkUserAllowed(SysUser sysUser);

    /**
     * 重置密码
     *
     * @param sysResetPasswordDto 要重置的用户
     */
    void resetPassword(SysResetPasswordDto sysResetPasswordDto);

    /**
     * 更新个人信息
     *
     * @param sysUserInfoDto 个人信息
     */
    void updateUserInfo(SysUserInfoDto sysUserInfoDto);

    /**
     * 更新用户头像
     *
     * @param sysUserAvatarDto 用户头像信息
     */
    void updateAvatar(SysUserAvatarDto sysUserAvatarDto);

    /**
     * 更新密码
     *
     * @param passwordDto 系统用户密码 Dto
     */
    void updatePassword(SysUserPasswordDto passwordDto);

}
