package com.brycehan.cloud.common.base.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 路由配置vo
 *
 * @since 2022/10/21
 * @author Bryce Han
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVo {

    /**
     * 路由名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 类型（D：目录，M：菜单，B：按钮）
     */
    private String type;

    /**
     * 组件地址
     */
    private String url;

    /**
     * 子路由
     */
    private List<MenuVo> routes;

}
