/*
    -- 删除表
    drop table if exists brc_gen_datasource;
    drop table if exists brc_gen_field_type;
    drop table if exists brc_gen_base_class;
    drop table if exists brc_gen_table;
    drop table if exists brc_gen_table_field;
    drop table if exists brc_gen_project_modify;
 */

-- 创建数据库
create database if not exists bryce_generator default charset utf8mb4;
use bryce_generator;

-- 1、数据源表
create table brc_gen_datasource
(
    id          bigint       primary key auto_increment comment 'ID',
    conn_name   varchar(100) not null comment '连接名称',
    db_type     varchar(50)  null comment '数据库类型',
    conn_url    varchar(200) null comment '连接地址',
    username    varchar(100) null comment '用户名',
    password    varchar(200) null comment '密码',
    create_time datetime     null comment '创建时间'
) engine InnoDB comment '数据源表';

-- 初始化-数据源表数据
INSERT INTO brc_gen_datasource (id, conn_name, db_type, conn_url, username, password, create_time) VALUES (1, 'bryce-cloud', 'MySQL', 'jdbc:mysql://localhost:3306/bryce_cloud_system?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullDatabaseMeansCurrent=true&useServerPrepStmts=true&cachePrepStmts=true', 'root', 'root', null);

-- 2、字段类型表
create table brc_gen_field_type
(
    id           bigint       primary key auto_increment comment 'ID',
    column_type  varchar(50)  not null comment '字段类型',
    attr_type    varchar(50)  not null comment '属性类型',
    package_name varchar(200) null comment '属性包名',
    remark       varchar(300) null comment '备注',
    create_time  datetime     null comment '创建时间',
    constraint uk_column_type unique (column_type)
) engine InnoDB comment '字段类型表';

-- 初始化-字段类型表数据
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (1, 'bit', 'Integer', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (2, 'tinyint', 'Integer', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (3, 'smallint', 'Integer', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (4, 'mediumint', 'Integer', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (5, 'int', 'Integer', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (6, 'integer', 'Integer', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (7, 'NUMBER', 'Integer', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (8, 'BINARY_INTEGER', 'Integer', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (9, 'int2', 'Integer', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (10, 'int4', 'Integer', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (11, 'int8', 'Long', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (12, 'bigint', 'Long', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (13, 'float', 'Float', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (14, 'BINARY_FLOAT', 'Float', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (15, 'double', 'Double', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (16, 'BINARY_DOUBLE', 'Double', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (17, 'char', 'String', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (18, 'varchar', 'String', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (19, 'tinytext', 'String', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (20, 'text', 'String', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (21, 'mediumtext', 'String', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (22, 'longtext', 'String', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (23, 'VARCHAR2', 'String', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (24, 'NVARCHAR', 'String', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (25, 'NVARCHAR2', 'String', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (26, 'CLOB', 'String', null, null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (27, 'decimal', 'BigDecimal', 'java.math.BigDecimal', null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (28, 'numeric', 'BigDecimal', 'java.math.BigDecimal', null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (29, 'date', 'LocalDate', 'java.time.LocalDate', null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (30, 'datetime', 'LocalDateTime', 'java.time.LocalDateTime', null, now());
insert into brc_gen_field_type (id, column_type, attr_type, package_name, remark, create_time) values (31, 'timestamp', 'LocalDateTime', 'java.time.LocalDateTime', null, now());

-- 3、基类表
create table brc_gen_base_class
(
    id           bigint       primary key auto_increment comment 'ID',
    code         varchar(100) not null comment '基类编码',
    package_name varchar(200) not null comment '基类包名',
    fields       varchar(500) null comment '基类字段（多个用英文逗号分隔）',
    remark       varchar(300) null comment '备注',
    create_time  datetime     null comment '创建时间'
) engine InnoDB comment '基类表';

-- 初始化-基类表数据
INSERT INTO brc_gen_base_class (id, code, package_name, fields, remark, create_time) VALUES (1, 'BaseEntity', 'com.brycehan.boot.common.entity', 'id,deleted,created_user_id,created_time,updated_user_id,updated_time', '使用该基类，则需要表里有这些字段', now());

-- 4、代码生成表
create table brc_gen_table
(
    id             bigint       primary key auto_increment comment 'ID',
    table_name     varchar(200) not null comment '表名',
    class_name     varchar(200) not null comment '类名',
    table_comment  varchar(200) null comment '说明',
    author         varchar(100) null comment '作者',
    email          varchar(100) null comment '邮箱',
    package_name   varchar(200) not null comment '项目包名',
    version        varchar(20)  not null comment '项目版本号',
    generator_type tinyint      null comment '生成方式（0：zip压缩包，1：自定义目录）',
    backend_path   varchar(300) null comment '后端生成路径',
    frontend_path  varchar(300) null comment '前端生成路径',
    module_name    varchar(100) null comment '模块名',
    function_name  varchar(100) null comment '功能名',
    form_layout    tinyint      null comment '表单布局（1：一列，2：两列）',
    datasource_id  bigint       not null comment '数据源ID',
    base_class_id  bigint       null comment '基类ID',
    remark         varchar(300) null comment '备注',
    create_time    datetime     null comment '创建时间'
) engine InnoDB comment '代码生成表';

-- 5、代码生成表字段
create table brc_gen_table_field
(
    id                       bigint        primary key auto_increment comment 'ID',
    table_id                 bigint        not null comment '表ID',
    field_name               varchar(200)  not null comment '字段名称',
    field_type               varchar(100)  not null comment '字段类型',
    field_comment            varchar(200)  null comment '字段说明',
    attr_name                varchar(200)  not null comment '属性名',
    attr_type                varchar(100)  not null comment '属性类型',
    package_name             varchar(200)  null comment '属性包名',
    sort                     int default 0 null comment '排序',
    auto_fill                varchar(20)   null comment '自动填充（DEFAULT, INSERT, UPDATE, INSERT_UPDATE）',
    primary_key              tinyint       null comment '主键（0：否，1：是）',
    character_maximum_length bigint        null comment '字符最大长度',
    base_field               tinyint       null comment '基类字段（0：否，1：是）',
    form_item                tinyint       null comment '表单项（0：否，1：是）',
    form_item_type           varchar(200)  null comment '表单项类型',
    form_dict                varchar(200)  null comment '表单字典类型',
    form_required            tinyint       null comment '表单必填（0：否，1：是）',
    form_validator           varchar(200)  null comment '表单校验器',
    grid_item                tinyint       null comment '列表项（0：否，1：是）',
    grid_sort                tinyint       null comment '列表排序（0：否，1：是）',
    query_item               tinyint       null comment '查询项（0：否，1：是）',
    query_type               varchar(200)  null comment '查询方式',
    query_form_type          varchar(200)  null comment '查询表单类型',
    create_time              datetime      null comment '创建时间'
) engine InnoDB comment '代码生成表的字段表';

-- 6、项目名变更表
create table brc_gen_project_modify
(
    id                             bigint       primary key auto_increment comment 'ID',
    project_name                   varchar(100) not null comment '项目名',
    project_code                   varchar(100) not null comment '项目标识',
    project_code_abbreviate        varchar(100) not null comment '项目标识缩写',
    project_package                varchar(100) not null comment '项目包名',
    project_path                   varchar(200) not null comment '项目路径',
    modify_project_name            varchar(100) null comment '变更项目名',
    modify_project_code            varchar(100) null comment '变更项目标识',
    modify_project_code_abbreviate varchar(100) null comment '变更项目标识缩写',
    modify_project_package         varchar(100) null comment '变更项目包名',
    exclusions                     varchar(200) null comment '排除文件',
    modify_suffix                  varchar(200) null comment '变更文件',
    modify_tmp_path                varchar(100) null comment '变更临时路径',
    create_time                    datetime     null comment '创建时间'
) engine InnoDB comment '项目名变更表';

-- 初始化-项目名变更表数据
insert into brc_gen_project_modify (id,project_name,project_code, project_code_abbreviate, project_package,project_path,modify_project_name,modify_project_code, modify_project_code_abbreviate, modify_project_package,exclusions,modify_suffix,modify_tmp_path,create_time) values (1,'Bryce Boot','bryce-boot', 'brc', 'com.brycehan.boot','/Users/brycehan/git/bryce-boot','Test Boot','test-boot', 'tst', 'com.test.boot','.git,.idea,target,logs','java,xml,yml,sql,md,txt',null,now());
