create database if not exists bryce_cloud_system default character set utf8mb4 collate utf8mb4_0900_ai_ci;
use bryce_cloud_system;

/*
    -- 删除表
    drop table if exists brc_sys_org;
    drop table if exists brc_sys_user;
    drop table if exists brc_sys_role;
    drop table if exists brc_sys_user_role;
    drop table if exists brc_sys_post;
    drop table if exists brc_sys_user_post;
    drop table if exists brc_sys_menu;
    drop table if exists brc_sys_role_menu;
    drop table if exists brc_sys_role_data_scope;
    drop table if exists brc_sys_login_log;
    drop table if exists brc_sys_operate_log;
    drop table if exists brc_sys_dict_type;
    drop table if exists brc_sys_dict_data;
    drop table if exists brc_sys_param;
    drop table if exists brc_sys_attachment;
    drop table if exists brc_sys_notice;
*/

-- 1、系统机构表
create table brc_sys_org
(
    id              bigint            primary key comment 'ID',
    name            varchar(100)      not null comment '机构名称',
    code            varchar(30)       null comment '机构编码',
    parent_id       bigint            not null comment '父机构ID',
    ancestor        varchar(255)      null comment '祖级机构列表',
    leader          varchar(50)       null comment '负责人',
    contact_number  varchar(20)       null comment '联系电话',
    email           varchar(50)       null comment '邮箱',
    remark          varchar(500)      null comment '备注',
    sort            int     default 0 null comment '显示顺序',
    status          tinyint default 1 null comment '状态（0：停用，1：正常）',
    deleted         datetime          null comment '删除标识',
    created_user_id bigint            null comment '创建者ID',
    created_time    datetime          null comment '创建时间',
    updated_user_id bigint            null comment '修改者ID',
    updated_time    datetime          null comment '修改时间'
) engine InnoDB comment '系统机构表';

create index idx_parent_id on brc_sys_org (parent_id);

-- 初始化-系统机构表数据
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (100, 'Bryce科技', null, 0, '0', '韩先生', '15800008001', 'brycehan@163.com', null, 0, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (101, '北京总公司', null, 100, '0,100', '韩先生', '15800008002', 'brycehan@163.com', null, 1, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (102, '济南分公司', null, 100, '0,100', '韩先生', '15800008003', 'brycehan@163.com', null, 2, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (103, '研发部门', null, 101, '0,100,101', '韩先生', '15800008004', 'brycehan@163.com', null, 1, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (104, '市场部门', null, 101, '0,100,101', '韩先生', '15800008005', 'brycehan@163.com', null, 2, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (105, '测试部门', null, 101, '0,100,101', '韩先生', '15800008006', 'brycehan@163.com', null, 3, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (106, '财务部门', null, 101, '0,100,101', '韩先生', '15800008007', 'brycehan@163.com', null, 4, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (107, '运维部门', null, 101, '0,100,101', '韩先生', '15800008008', 'brycehan@163.com', null, 5, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (108, '市场部门', null, 102, '0,100,102', '韩先生', '15800008009', 'brycehan@163.com', null, 1, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (109, '财务部门', null, 102, '0,100,102', '韩先生', '15800008010', 'brycehan@163.com', null, 2, 1, null, 1, now(), null, null);

-- 2、系统用户表
create table brc_sys_user
(
    id                 bigint                   primary key comment 'ID',
    username           varchar(50)              not null comment '账号',
    password           varchar(255)             null comment '密码',
    nickname           varchar(50)              null comment '姓名',
    avatar             varchar(200)             null comment '头像地址',
    gender             char                     null comment '性别（M：男, F：女，N：未知）',
    type               smallint   default 0     null comment '用户类型（0：系统用户）',
    phone              varchar(20)              null comment '手机号码',
    email              varchar(50)              null comment '邮箱',
    birthday           datetime                 null comment '生日',
    profession         varchar(50)              null comment '职业',
    sort               int          default 0   null comment '显示顺序',
    org_id             bigint                   null comment '机构ID',
    super_admin        tinyint                  null comment '超级管理员',
    status             tinyint(1)   default 1   null comment '状态（0：停用，1：正常）',
    remark             varchar(500)             null comment '备注',
    account_non_locked tinyint(1)   default 1   null comment '账号锁定状态（0：锁定，1：正常）',
    last_login_ip      varchar(128)             null comment '最后登录IP',
    last_login_time    datetime                 null comment '最后登录时间',
    deleted            datetime                 null comment '删除标识',
    created_user_id    bigint                   null comment '创建者ID',
    created_time       datetime                 null comment '创建时间',
    updated_user_id    bigint                   null comment '修改者ID',
    updated_time       datetime                 null comment '修改时间',
    constraint ck_gender check (gender in ('M', 'F'))
) engine InnoDB comment '系统用户表';

-- 初始化-系统用户表数据
INSERT INTO brc_sys_user (id, username, password, nickname, avatar, gender, type, phone, email, sort, org_id, super_admin, status, remark, account_non_locked, last_login_ip, last_login_time, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, 'admin', '$2a$10$qpmiSNpGEXXXWsv52Pjfte5CcbWUEtx0We//w9Jz8G0XMdEOS7uNu', '超级管理员', null, 'M', 0, '15853155402', 'brycehan@163.com', 0, 103, 1, 1, '超级管理员', 1, '127.0.0.1', now(), null, 1, now(), null, null);

-- 3、系统角色表
create table brc_sys_role
(
    id              bigint            primary key comment 'ID',
    name            varchar(50)       not null comment '角色名称',
    code            varchar(50)       not null comment '角色编码',
    data_scope      smallint          null comment '数据范围（1：全部数据，2：本机构及以下机构数据，3：本机构数据，4：本人数据，5：自定义数据）',
    sort            int     default 0 null comment '显示顺序',
    status          tinyint default 1 null comment '状态（0：停用，1：正常）',
    remark          varchar(500)      null comment '备注',
    org_id          bigint            null comment '机构ID',
    deleted         datetime          null comment '删除标识',
    created_user_id bigint            null comment '创建者ID',
    created_time    datetime          null comment '创建时间',
    updated_user_id bigint            null comment '修改者ID',
    updated_time    datetime          null comment '修改时间'
) engine InnoDB comment '系统角色表';

-- 初始化-系统角色表数据
INSERT INTO brc_sys_role (id, name, code, data_scope, sort, status, remark, org_id, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '管理员', 'admin', 2, 0, 1, '管理员', null, null, 1, now(), null, null);
INSERT INTO brc_sys_role (id, name, code, data_scope, sort, status, remark, org_id, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '默认角色', 'default', 2, 0, 1, '默认角色', null, null, 1, now(), null, null);

-- 4、系统用户角色关系表
create table brc_sys_user_role
(
    id              bigint   primary key comment 'ID',
    user_id         bigint   not null comment '用户ID',
    role_id         bigint   not null comment '角色ID',
    deleted         datetime null comment '删除标识',
    created_user_id bigint   null comment '创建者ID',
    created_time    datetime null comment '创建时间',
    updated_user_id bigint   null comment '修改者ID',
    updated_time    datetime null comment '修改时间'
) engine InnoDB comment '系统用户角色关系表';

create index idx_user_id on brc_sys_user_role (user_id);
create index idx_role_id on brc_sys_user_role (role_id);

-- 5、系统岗位表
create table brc_sys_post
(
    id              bigint            primary key comment 'ID',
    name            varchar(50)       not null comment '岗位名称',
    code            varchar(30)       not null comment '岗位编码',
    sort            int     default 0 null comment '显示顺序',
    status          tinyint default 1 null comment '状态（0：停用，1：正常）',
    remark          varchar(500)      null comment '备注',
    deleted         datetime          null comment '删除标识',
    created_user_id bigint            null comment '创建者ID',
    created_time    datetime          null comment '创建时间',
    updated_user_id bigint            null comment '修改者ID',
    updated_time    datetime          null comment '修改时间'
) engine InnoDB comment '系统岗位表';

-- 初始化-系统岗位表数据
INSERT INTO brc_sys_post (id, name, code, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '董事长', 'ceo', 1, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_post (id, name, code, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '项目经理', 'se', 2, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_post (id, name, code, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '人力资源', 'hr', 3, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_post (id, name, code, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '普通员工', 'user', 4, 1, null, null, 1, now(), null, null);

-- 6、系统用户岗位关系表
create table brc_sys_user_post
(
    id              bigint   primary key comment 'ID',
    user_id         bigint   not null comment '用户ID',
    post_id         bigint   not null comment '岗位ID',
    deleted         datetime null comment '删除标识',
    created_user_id bigint   null comment '创建者ID',
    created_time    datetime null comment '创建时间',
    updated_user_id bigint   null comment '修改者ID',
    updated_time    datetime null comment '修改时间'
) engine InnoDB comment '系统用户岗位关系表';

create index idx_user_id on brc_sys_user_post (user_id);
create index idx_post_id on brc_sys_user_post (post_id);

insert into brc_sys_user_post (id, user_id, post_id, deleted, created_user_id, created_time, updated_user_id, updated_time)
values (1, 1, 2, null, 1, now(), null, null);

-- 7、系统菜单表
create table brc_sys_menu
(
    id              bigint               primary key comment 'ID',
    name            varchar(50)          not null comment '菜单名称',
    type            char                 not null comment '类型（M：菜单，B：按钮，I：接口）',
    parent_id       bigint               not null comment '父菜单ID，一级菜单为0',
    url             varchar(255)         null comment '组件路径',
    authority       varchar(100)         null comment '权限标识',
    icon            varchar(100)         null comment '菜单图标',
    open_style      tinyint(1)           null comment '打开方式（0：内部，1：外部）',
    sort            int        default 0 null comment '显示顺序',
    remark          varchar(500)         null comment '备注',
    status          tinyint(1) default 1 null comment '状态（0：停用，1：正常）',
    deleted         datetime             null comment '删除标识',
    created_user_id bigint               null comment '创建者ID',
    created_time    datetime             null comment '创建时间',
    updated_user_id bigint               null comment '修改者ID',
    updated_time    datetime             null comment '修改时间'
) engine InnoDB comment '系统菜单表';

-- 初始化-系统菜单表数据
-- 一级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '权限管理', 'M', 0, null, null, 'icon-safetycertificate', 0, 1, '系统管理目录', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '系统设置', 'M', 0, null, null, 'icon-setting', 0, 2, '系统管理目录', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '系统监控', 'M', 0, null, null, 'icon-Report', 0, 3, '系统监控目录', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '系统工具', 'M', 0, null, null, 'icon-wrench', 0, 4, '系统工具目录', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (5, '日志管理', 'M', 0, null, null, 'icon-filedone', 0, 5, '系统日志目录', 1, null, 1, now(), null, null);
-- 二级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (101, '用户管理', 'M', 1, 'system/user/index', 'system:user:page', 'icon-user', 0, 1, '用户管理菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (102, '机构管理', 'M', 1, 'system/org/index', 'system:org:page', 'icon-cluster', 0, 2, '机构管理菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (103, '岗位管理', 'M', 1, 'system/post/index', 'system:post:page', 'icon-solution', 0, 3, '岗位管理菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (104, '角色管理', 'M', 1, 'system/role/index', 'system:role:page', 'icon-team', 0, 4, '角色管理菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (105, '菜单管理', 'M', 1, 'system/menu/index', 'system:menu:page', 'icon-menu', 0, 5, '菜单管理菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (112, '数据字典', 'M', 2, 'system/dict/index', 'system:dictType:page', 'icon-insertrowabove', 0, 2, '数据字典菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (113, '参数设置', 'M', 2, 'system/param/index', 'system:param:page', 'icon-control', 0, 3, '参数设置菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (114, '附件管理', 'M', 2, 'system/attachment/index', 'system:attachment:page', 'icon-folder', 0, 4, '参数设置菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (116, '通知公告', 'M', 2, 'system/notice/index', 'system:notice:page', 'icon-message', 0, 5, '通知公告菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (121, '在线用户', 'M', 3, 'monitor/onlineUser/index', 'monitor:onlineUser:page', 'icon-solution', 0, 1, '在线用户菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (122, 'Sentinel控制台', 'M', 3, 'http://localhost:8718', 'monitor:sentinel:page', 'icon-sentinel', 1, 1, '流量控制菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (123, 'Nacos控制台', 'M', 3, 'http://localhost:8848/nacos', 'monitor:nacos:page', 'icon-nacos', 1, 1, '服务治理菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (124, 'Admin控制台', 'M', 3, 'http://localhost:9013/login', 'monitor:server:page', 'icon-linechart', 1, 1, '服务监控菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (131, '代码生成', 'M', 4, '{{apiUrl}}/system/generator-ui/index.html', 'generator:generator:page', 'icon-code', 1, 1, '代码生成菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (132, '接口文档', 'M', 4, '{{apiUrl}}/doc.html', 'system:swagger:page', 'icon-file-text', 1, 2, '接口文档菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (141, '登录日志', 'M', 5, 'system/loginLog/index', 'system:loginLog:page', 'icon-solution', 0, 1, '登录日志菜单', 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (142, '操作日志', 'M', 5, 'system/operateLog/index', 'system:operateLog:page', 'icon-file-text', 0, 2, '操作日志菜单', 1, null, 1, now(), null, null);
-- 三级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1011, '用户新增', 'B', 101, null, 'system:user:save', '', 0, 1, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1012, '用户修改', 'B', 101, null, 'system:user:update', '', 0, 2, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1013, '用户删除', 'B', 101, null, 'system:user:delete', '', 0, 3, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1014, '用户详情', 'B', 101, null, 'system:user:info', '', 0, 4, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1015, '用户导出', 'B', 101, null, 'system:user:export', '', 0, 5, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1016, '用户导入', 'B', 101, null, 'system:user:import', '', 0, 6, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1017, '重置密码', 'B', 101, null, 'system:user:resetPwd', '', 0, 7, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1021, '机构新增', 'B', 102, null, 'system:org:save', '', 0, 1, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1022, '机构修改', 'B', 102, null, 'system:org:update', '', 0, 2, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1023, '机构删除', 'B', 102, null, 'system:org:delete', '', 0, 3, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1024, '机构详情', 'B', 102, null, 'system:org:info', '', 0, 4, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1025, '机构导出', 'B', 102, null, 'system:org:export', '', 0, 5, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1026, '机构导入', 'B', 102, null, 'system:org:import', '', 0, 6, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1031, '岗位新增', 'B', 103, null, 'system:post:save', '', 0, 1, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1032, '岗位修改', 'B', 103, null, 'system:post:update', '', 0, 2, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1033, '岗位删除', 'B', 103, null, 'system:post:delete', '', 0, 3, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1034, '岗位详情', 'B', 103, null, 'system:post:info', '', 0, 4, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1035, '岗位导出', 'B', 103, null, 'system:post:export', '', 0, 5, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1036, '岗位导入', 'B', 103, null, 'system:post:import', '', 0, 6, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1041, '角色新增', 'B', 104, null, 'system:role:save', '', 0, 1, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1042, '角色修改', 'B', 104, null, 'system:role:update', '', 0, 2, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1043, '角色删除', 'B', 104, null, 'system:role:delete', '', 0, 3, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1044, '角色详情', 'B', 104, null, 'system:role:info', '', 0, 4, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1045, '角色导出', 'B', 104, null, 'system:role:export', '', 0, 5, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1046, '角色导入', 'B', 104, null, 'system:role:import', '', 0, 6, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1047, '角色列表', 'B', 104, null, 'system:role:list', '', 0, 7, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1048, '角色菜单', 'B', 104, null, 'system:role:menu', '', 0, 8, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1051, '菜单新增', 'B', 105, null, 'system:menu:save', '', 0, 1, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1052, '菜单修改', 'B', 105, null, 'system:menu:update', '', 0, 2, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1053, '菜单删除', 'B', 105, null, 'system:menu:delete', '', 0, 3, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1054, '菜单详情', 'B', 105, null, 'system:menu:info', '', 0, 4, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1055, '菜单导出', 'B', 105, null, 'system:menu:export', '', 0, 5, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1056, '菜单导入', 'B', 105, null, 'system:menu:import', '', 0, 6, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1121, '字典类型新增', 'B', 112, null, 'system:dictType:save', '', 0, 1, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1122, '字典类型修改', 'B', 112, null, 'system:dictType:update', '', 0, 2, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1123, '字典类型删除', 'B', 112, null, 'system:dictType:delete', '', 0, 3, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1124, '字典类型详情', 'B', 112, null, 'system:dictType:info', '', 0, 4, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1125, '字典类型导出', 'B', 112, null, 'system:dictType:export', '', 0, 5, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1126, '字典类型导入', 'B', 112, null, 'system:dictType:import', '', 0, 6, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1127, '字典数据管理', 'B', 112, 'system/dictData/index', 'system:dictData:page', 'icon-menu', 0, 0, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1128, '字典数据新增', 'B', 112, null, 'system:dictData:save', '', 0, 1, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1129, '字典数据修改', 'B', 112, null, 'system:dictData:update', '', 0, 2, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1130, '字典数据删除', 'B', 112, null, 'system:dictData:delete', '', 0, 3, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1131, '字典数据详情', 'B', 112, null, 'system:dictData:info', '', 0, 4, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1132, '字典数据导出', 'B', 112, null, 'system:dictData:export', '', 0, 5, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1133, '字典数据导入', 'B', 112, null, 'system:dictData:import', '', 0, 6, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1134, '参数新增', 'B', 113, null, 'system:param:save', '', 0, 1, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1135, '参数修改', 'B', 113, null, 'system:param:update', '', 0, 2, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1136, '参数删除', 'B', 113, null, 'system:param:delete', '', 0, 3, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1137, '参数详情', 'B', 113, null, 'system:param:info', '', 0, 4, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1138, '参数导出', 'B', 113, null, 'system:param:export', '', 0, 5, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1139, '参数导入', 'B', 113, null, 'system:param:import', '', 0, 6, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1141, '附件上传', 'B', 114, null, 'system:attachment:save', '', 0, 1, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1142, '附件删除', 'B', 114, null, 'system:attachment:delete', '', 0, 3, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1143, '附件详情', 'B', 114, null, 'system:attachment:info', '', 0, 4, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1161, '通知公告新增', 'B', 116, null, 'system:notice:save', '', 0, 1, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1162, '通知公告修改', 'B', 116, null, 'system:notice:update', '', 0, 2, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1163, '通知公告删除', 'B', 116, null, 'system:notice:delete', '', 0, 3, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1164, '通知公告详情', 'B', 116, null, 'system:notice:info', '', 0, 4, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1165, '通知公告导出', 'B', 116, null, 'system:notice:export', '', 0, 5, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1166, '通知公告导入', 'B', 116, null, 'system:notice:import', '', 0, 6, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1211, '在线用户强退', 'B', 121, null, 'monitor:onlineUser:delete', '', 0, 1, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1411, '登录日志删除', 'B', 141, null, 'system:loginLog:delete', '', 0, 3, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1412, '登录日志详情', 'B', 141, null, 'system:loginLog:info', '', 0, 4, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1413, '登录日志导出', 'B', 141, null, 'system:loginLog:export', '', 0, 5, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1421, '操作日志删除', 'B', 142, null, 'system:operateLog:delete', '', 0, 3, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1422, '操作日志详情', 'B', 142, null, 'system:operateLog:info', '', 0, 4, null, 1, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1423, '操作日志导出', 'B', 142, null, 'system:operateLog:export', '', 0, 5, null, 1, null, 1, now(), null, null);

-- 8、系统角色菜单关系表
create table brc_sys_role_menu
(
    id              bigint   primary key comment 'ID',
    role_id         bigint   not null comment '角色ID',
    menu_id         bigint   not null comment '菜单ID',
    deleted         datetime null comment '删除标识',
    created_user_id bigint   null comment '创建者ID',
    created_time    datetime null comment '创建时间',
    updated_user_id bigint   null comment '修改者ID',
    updated_time    datetime null comment '修改时间'
) engine InnoDB comment '系统角色菜单关系表';

create index idx_role_id on brc_sys_role_menu (role_id);
create index idx_menu_id on brc_sys_role_menu (menu_id);

-- 9、系统角色数据范围表
create table brc_sys_role_data_scope
(
    id              bigint   primary key comment 'ID',
    role_id         bigint   not null comment '角色ID',
    org_id          bigint   not null comment '机构ID',
    deleted         datetime null comment '删除标识',
    created_user_id bigint   null comment '创建者ID',
    created_time    datetime null comment '创建时间',
    updated_user_id bigint   null comment '修改者ID',
    updated_time    datetime null comment '修改时间'
) engine InnoDB comment '系统角色数据范围表';

-- 10、系统登录日志表
create table brc_sys_login_log
(
    id           bigint       primary key comment 'ID',
    username     varchar(50)  null comment '用户账号',
    info         smallint     null comment '操作信息',
    ip           varchar(128) null comment '登录IP地址',
    location     varchar(255) null comment '登录地点',
    browser      varchar(50)  null comment '浏览器类型',
    os           varchar(50)  null comment '操作系统',
    user_agent   varchar(500) null comment 'User Agent',
    status       tinyint      null comment '状态（0：失败，1：成功）',
    access_time  datetime     null comment '访问时间',
    created_time datetime     null comment '创建时间'
) engine InnoDB comment '系统登录日志表';

-- 11、系统操作日志表
create table brc_sys_operate_log
(
    id             bigint        primary key comment 'ID',
    name           varchar(50)   null comment '操作名称',
    module_name    varchar(50)   null comment '模块名',
    request_uri    varchar(2048) null comment '请求URI',
    request_method varchar(10)   null comment '请求方法',
    request_param  text          null comment '请求参数',
    result_message varchar(500)  null comment '返回消息',
    operated_type  varchar(20)   null comment '操作类型',
    operated_time  datetime      null comment '操作时间',
    duration       int           null comment '执行时长（毫秒）',
    status         tinyint       null comment '操作状态（0：失败，1：成功）',
    user_agent     varchar(500)  null comment 'User Agent',
    ip             varchar(128)  null comment '操作IP',
    location       varchar(255)  null comment '操作地点',
    user_id        bigint        null comment '操作人ID',
    username       varchar(50)   null comment '操作人账号',
    org_id         bigint        null comment '机构ID',
    created_time   datetime      null comment '创建时间'
) engine InnoDB comment '系统操作日志表';

-- 12、系统字典类型表
create table brc_sys_dict_type
(
    id              bigint            primary key comment 'ID',
    dict_name       varchar(100)      null comment '字典名称',
    dict_type       varchar(100)      null comment '字典类型',
    sort            int     default 0 null comment '显示顺序',
    status          tinyint default 1 null comment '状态（0：停用，1：正常）',
    remark          varchar(500)      null comment '备注',
    deleted         datetime          null comment '删除标识',
    created_user_id bigint            null comment '创建者ID',
    created_time    datetime          null comment '创建时间',
    updated_user_id bigint            null comment '修改者ID',
    updated_time    datetime          null comment '修改时间',
    constraint uk_dict_type unique (dict_type) comment '字典类型'
) engine InnoDB comment '系统字典类型表';

-- 初始化-系统字典类型表数据
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '用户性别', 'sys_user_gender', 0, 1, '用户性别列表', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '系统开关', 'sys_status', 0, 1, '系统开关列表', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '数据范围', 'sys_data_scope', 0, 1, '根据范围权限', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '操作状态', 'sys_operate_status', 0, 1, '操作状态列表', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (5, '登录状态', 'sys_login_status', 0, 1, '登录状态列表', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (6, '操作类型', 'sys_operate_type', 0, 1, '操作类型列表', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (7, '通知类型', 'sys_notice_type', 0, 1, '通知类型列表', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (8, '通知状态', 'sys_notice_status', 0, 1, '通知状态列表', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (9, '系统是否', 'sys_yes_no', 0, 1, '系统是否列表', null, 1, now(), null, null);

-- 13、系统字典数据表
create table brc_sys_dict_data
(
    id              bigint            primary key comment 'ID',
    dict_label      varchar(100)      null comment '字典标签',
    dict_value      varchar(100)      null comment '字典值',
    dict_type_id    bigint            null comment '字典类型',
    label_class     varchar(100)      null comment '标签属性',
    sort            int     default 0 null comment '显示顺序',
    status          tinyint default 1 null comment '状态（0：停用，1：正常）',
    remark          varchar(500)      null comment '备注',
    deleted         datetime          null comment '删除标识',
    created_user_id bigint            null comment '创建者ID',
    created_time    datetime          null comment '创建时间',
    updated_user_id bigint            null comment '修改者ID',
    updated_time    datetime          null comment '修改时间'
) engine InnoDB comment '系统字典数据表';

-- 初始化-系统字典数据表数据
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (11, '男', 'M', 1, '', 1, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (12, '女', 'F', 1, '', 2, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (13, '未知', 'N', 1, '', 3, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (21, '正常', 'true', 2, 'primary', 1, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (22, '停用', 'false', 2, 'danger', 2, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (31, '全部数据', '1', 3, '', 1, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (32, '本机构及以下机构数据', '2', 3, '', 2, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (33, '本机构数据', '3', 3, '', 3, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (34, '本人数据', '4', 3, '', 4, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (35, '自定义数据', '5', 3, '', 5, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (41, '成功', 'true', 4, 'primary', 1, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (42, '失败', 'false', 4, 'danger', 2, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (51, '登录成功', '0', 5, 'primary', 1, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (52, '退出成功', '1', 5, 'success', 2, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (53, '验证码错误', '2', 5, 'warning', 3, 1, '', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (54, '账号密码错误', '3', 5, 'danger', 4, 1, '', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (61, '其他', 'OTHER', 6, 'info', 99, 1, '', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (62, '新增', 'INSERT', 6, 'primary', 1, 1, '', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (63, '修改', 'UPDATE', 6, 'info', 2, 1, '', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (64, '删除', 'DELETE', 6, 'danger', 3, 1, '', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (65, '授权', 'GRANT', 6, 'warning', 7, 1, '', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (66, '导出', 'EXPORT', 6, 'warning', 5, 1, '', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (67, '导入', 'IMPORT', 6, 'warning', 6, 1, '', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (68, '强退', 'FORCE_QUIT', 6, 'danger', 7, 1, '', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (69, '生成代码', 'GEN_CODE', 6, 'success', 8, 1, '', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (70, '清空数据', 'CLEAN_DATA', 6, 'danger', 9, 1, '', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (71, '查询', 'GET', 6, '', 4, 1, '', null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (73, '通知', '1', 7, 'warning', 1, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (74, '公告', '2', 7, 'success', 2, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (81, '正常', '0', 8, 'primary', 1, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (82, '关闭', '1', 8, 'danger', 2, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (91, '是', 'Y', 9, 'primary', 1, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (92, '否', 'N', 9, 'danger', 2, 1, null, null, 1, now(), null, null);

-- 14、系统参数表
create table brc_sys_param
(
    id              bigint       primary key comment 'ID',
    param_name      varchar(100) null comment '参数名称',
    param_key       varchar(100) null comment '参数键',
    param_value     text         null comment '参数值',
    param_type      varchar(20)  null comment '参数类型（builtIn：内置，system：系统）',
    remark          varchar(500) null comment '备注',
    deleted         datetime     null comment '删除标识',
    created_user_id bigint       null comment '创建者ID',
    created_time    datetime     null comment '创建时间',
    updated_user_id bigint       null comment '修改者ID',
    updated_time    datetime     null comment '修改时间',
    constraint uk_param_key unique (param_key)
) engine InnoDB comment '系统参数表';

-- 初始化-系统配置表数据
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '用户登录-图片验证码开关', 'system.login.captcha.enabled', 'false', 'system', '是否开启登录图片验证码功能（true：开启，false：关闭）', null, 1, now(), null, null);
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '用户登录-短信验证码开关', 'system.login.sms.enabled', 'false', 'system', '是否开启登录短信验证码功能（true：开启，false：关闭）', null, 1, now(), null, null);
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '用户注册-开关', 'system.register.enabled', 'true', 'system', '是否开启注册功能（true：开启，false：关闭）', null, 1, now(), null, null);
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '用户注册-图片验证码开关', 'system.register.captcha.enabled', 'true', 'system', '是否开启注册图片验证码功能（true：开启，false：关闭）', null, 1, now(), null, null);
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (5, '用户注册-短信验证码开关', 'system.register.sms.enabled', 'true', 'system', '是否开启注册短信验证码功能（true：开启，false：关闭）', null, 1, now(), null, null);
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (6, '短信-开关', 'system.sms.enabled', 'true', 'system', '是否开启短信功能（true：开启，false：关闭）', null, 1, now(), null, null);
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (7, '微信公众号菜单', 'wechat.mp.menu', '', 'builtIn', '微信公众号菜单内容', null, 1, now(), null, null);

-- 15、系统附件表
create table brc_sys_attachment
(
    id              bigint       primary key comment 'ID',
    name            varchar(100) null comment '附件名称',
    url             varchar(255) null comment '附件地址',
    size            bigint       null comment '附件大小（单位字节）',
    type            varchar(50)  null comment '附件类型',
    suffix          varchar(10)  null comment '附件名后缀',
    hash            varchar(255) null comment '哈希码',
    platform        varchar(50)  null comment '存储平台',
    deleted         datetime     null comment '删除标识',
    created_user_id bigint       null comment '创建者ID',
    created_time    datetime     null comment '创建时间',
    updated_user_id bigint       null comment '修改者ID',
    updated_time    datetime     null comment '修改时间'
) engine InnoDB comment '系统附件表';

create index idx_created_time on brc_sys_attachment (created_time) comment '创建时间索引';

-- 16、系统通知公告表
create table brc_sys_notice
(
    id              bigint            primary key comment 'ID',
    title           varchar(100)      not null comment '标题',
    content         longtext          null comment '内容',
    type            smallint          not null comment '公告类型（0：通知，1：公告）',
    status          tinyint default 1 null comment '状态（0：关闭，1：正常）',
    remark          varchar(500)      null comment '备注',
    deleted         datetime          null comment '删除标识',
    created_user_id bigint            null comment '创建者ID',
    created_time    datetime          null comment '创建时间',
    updated_user_id bigint            null comment '修改者ID',
    updated_time    datetime          null comment '修改时间'
) engine InnoDB comment '系统通知公告表';

-- 初始化-系统通知公告表数据
INSERT INTO brc_sys_notice (id, title, content, type, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '温馨提醒：2022-10-01 Bryce Boot 新版本发布啦', '<p>新版本内容</p>', 2, 1, null, null, 1, now(), null, null);
INSERT INTO brc_sys_notice (id, title, content, type, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '维护通知：2022-10-01 Bryce Boot 系统凌晨维护', '<p>维护内容</p>', 1, 1, null, null, 1, now(), null, null);

-- 17、地区编码表
create table brc_sys_area_code
(
    id            int          primary key comment 'ID',
    parent_id     int          not null comment '父ID',
    deep          int          not null comment '层级',
    name          varchar(255) not null comment '名称',
    code          varchar(255) not null comment '编码',
    pinyin_prefix varchar(255) not null comment '拼音前缀',
    pinyin        varchar(255) not null comment '拼音',
    ext_id        varchar(50)  not null comment '扩展ID',
    ext_name      varchar(255) not null comment '扩展名称'
) comment '地区编码表';

-- 18 seata undo_log v2.0.0
-- https://github.com/apache/incubator-seata/tree/v2.0.0/script/client/at/db
-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
    ) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT ='AT transaction mode undo table';
ALTER TABLE `undo_log` ADD INDEX `ix_log_created` (`log_created`);