package com.brycehan.cloud.common.core.constant;

/**
 * 数据状态常量
 *
 * @since 2022/7/21
 * @author Bryce Han
 */
public class DataConstants {

    /** 默认 */
    public static final int DEFAULT = 1;

    /** 非默认 */
    public static final int NON_DEFAULT = 0;

    /** 通用成功标识 */
    public static final boolean SUCCESS = true;

    /** 通用失败标识 */
    public static final boolean FAIL = false;

    /**
     * 是
     */
    public static final String YES = "Y";

    /**
     * 否
     */
    public static final String NO = "N";

    /**
     * 默认角色ID
     */
    public static final Long DEFAULT_ROLE_ID = 2L;

    /** 分页页码 */
    public static final String PAGE = "page";

    /** 根ID */
    public static final Long TREE_ROOT_ID = 0L;

    /** 数据权限范围 */
    public static final String DATA_SCOPE = "dataScope";

    /**
     * 默认排序列
     */
    public static final String DEFAULT_SORT_COLUMN = "sort";

    /**
     * 默认排序升序
     */
    public static final boolean DEFAULT_SORT_IS_ASC = true;

    /**
     * 超级管理员名称
     */
    public static final String SUPER_ADMIN_NAME = "超级管理员";

    /**
     * 角色前缀
     */
    public static final String ROLE_PREFIX = "ROLE_";
}
