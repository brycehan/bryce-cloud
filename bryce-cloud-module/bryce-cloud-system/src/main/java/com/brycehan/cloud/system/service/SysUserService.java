package com.brycehan.cloud.system.service;

import com.brycehan.cloud.common.server.common.IdGenerator;
import com.brycehan.cloud.common.core.base.VersionException;
import com.brycehan.cloud.common.core.entity.PageResult;
import com.brycehan.cloud.common.core.entity.dto.SysUserInfoDto;
import com.brycehan.cloud.common.mybatis.service.BaseService;
import com.brycehan.cloud.system.entity.convert.SysUserConvert;
import com.brycehan.cloud.system.entity.dto.*;
import com.brycehan.cloud.system.entity.po.SysUser;
import com.brycehan.cloud.system.entity.vo.SysUserInfoVo;
import com.brycehan.cloud.system.entity.vo.SysUserVo;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    @Retryable(retryFor = VersionException.class, backoff = @Backoff(delay = 0))
    default void update(SysUserDto sysUserDto) {
        SysUser sysUser = SysUserConvert.INSTANCE.convert(sysUserDto);

        // 更新
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
     * 批量导入保存用户
     *
     * @param list      用户列表
     * @param password 初始密码
     */
    void saveUsers(List<SysUserExcelDto> list, String password);
    /**
     * 根据账号查询用户
     *
     * @param username 账号
     * @return 系统用户
     */
    SysUser getByUsername(String username);

    /**
     * 根据手机号码查询用户
     *
     * @param phone 手机号码
     * @return 系统用户
     */
    SysUser getByPhone(String phone);

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
     * @param sysUsernameDto 用户账号Dto
     * @return 结果，true唯一
     */
    boolean checkUsernameUnique(SysUsernameDto sysUsernameDto);

    /**
     * 校验用户手机号码是否唯一
     *
     * @param sysUserPhoneDto 手机号码Dto
     * @return 结果，true唯一
     */
    boolean checkPhoneUnique(SysUserPhoneDto sysUserPhoneDto);

    /**
     * 校验用户邮箱是否唯一
     *
     * @param sysUserEmailDto 用户邮箱Dto
     * @return 结果，true唯一
     */
    boolean checkEmailUnique(SysUserEmailDto sysUserEmailDto);

    /**
     * 校验用户是否允许操作（admin放行，普通用户不能操作admin账号）
     *
     * @param sysUser 系统用户信息
     */
    void checkUserAllowed(SysUser sysUser);

    /**
     * 更新个人信息
     *
     * @param sysUserInfoDto 个人信息
     */
    void updateUserInfo(SysUserInfoDto sysUserInfoDto);

    /**
     * 更新用户头像
     *
     * @param avatar 用户头像信息
     */
    String updateAvatar(MultipartFile avatar);

    /**
     * 更新密码
     *
     * @param passwordDto 系统用户密码Dto
     */
    void updatePassword(SysUserPasswordDto passwordDto);

    /**
     * 重置密码
     *
     * @param sysResetPasswordDto 要重置的用户
     */
    void resetPassword(SysResetPasswordDto sysResetPasswordDto);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserInfoVo getUserInfo(Long userId);

}
