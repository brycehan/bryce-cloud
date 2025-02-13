/*
    -- 删除表
    drop table if exists brc_gen_datasource;
    drop table if exists brc_gen_field_type;
    drop table if exists brc_gen_base_class;
    drop table if exists brc_gen_table;
    drop table if exists brc_gen_table_field;
    drop table if exists brc_gen_project_modify;
 */

-- 1、数据源表
create table brc_gen_datasource
(
    id          bigint          identity not null,
    conn_name   varchar(100)    not null,
    db_type     varchar(50),
    conn_url    varchar(200),
    username    varchar(100),
    password    varchar(200),
    create_time datetime,
    primary key (id)
);

comment on table brc_gen_datasource is '数据源表';
comment on column brc_gen_datasource.id is 'ID';
comment on column brc_gen_datasource.conn_name is '连接名称';
comment on column brc_gen_datasource.db_type is '数据库类型';
comment on column brc_gen_datasource.conn_url is '连接地址';
comment on column brc_gen_datasource.username is '用户名';
comment on column brc_gen_datasource.password is '密码';
comment on column brc_gen_datasource.create_time is '创建时间';

-- 初始化-数据源表数据
INSERT INTO brc_gen_datasource (id, conn_name, db_type, conn_url, username, password, create_time) VALUES (1, 'bryce-cloud', 'MySQL', 'jdbc:mysql://localhost:3306/bryce_cloud_system?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullDatabaseMeansCurrent=true&useServerPrepStmts=true&cachePrepStmts=true', 'root', 'root', null);

-- 2、字段类型表
create table brc_gen_field_type
(
    id           bigint         identity not null,
    column_type  varchar(50)    not null,
    attr_type    varchar(50)    not null,
    package_name varchar(200),
    remark       varchar(300),
    create_time  datetime,
    primary key (id)
);

create unique index uk_brc_gen_column_type on brc_gen_field_type(column_type);

comment on table brc_gen_field_type is '字段类型表';
comment on column brc_gen_field_type.id is 'ID';
comment on column brc_gen_field_type.column_type is '字段类型';
comment on column brc_gen_field_type.attr_type is '属性类型';
comment on column brc_gen_field_type.package_name is '属性包名';
comment on column brc_gen_field_type.remark is '备注';
comment on column brc_gen_field_type.create_time is '创建时间';

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
    id           bigint         identity not null,
    code         varchar(100)   not null,
    package_name varchar(200)   not null,
    fields       varchar(500),
    remark       varchar(300),
    create_time  datetime,
    primary key (id)
);

comment on table brc_gen_base_class is '基类表';
comment on column brc_gen_base_class.id is 'ID';
comment on column brc_gen_base_class.code is '基类编码';
comment on column brc_gen_base_class.package_name is '基类包名';
comment on column brc_gen_base_class.fields is '基类字段（多个用英文逗号分隔）';
comment on column brc_gen_base_class.remark is '备注';
comment on column brc_gen_base_class.create_time is '创建时间';

-- 初始化-基类表数据
insert into brc_gen_base_class (id,code,package_name,fields,remark,create_time) values (1,'BaseEntity','com.brycehan.boot.common.entity','id,create_user_id,create_time,update_user_id,update_time','使用该基类，则需要表里有这些字段',now());

-- 4、代码生成表
create table brc_gen_table
(
    id             bigint       identity not null,
    table_name     varchar(200) not null,
    class_name     varchar(200) not null,
    table_comment  varchar(200),
    author         varchar(100),
    email          varchar(100),
    package_name   varchar(200) not null,
    version        varchar(20),
    generator_type int,
    backend_path   varchar(300),
    frontend_path  varchar(300),
    module_name    varchar(100),
    function_name  varchar(100),
    form_layout    int,
    datasource_id  bigint       not null,
    base_class_id   bigint,
    remark         varchar(300),
    create_time    datetime,
    primary key (id)
);

create unique index uk_brc_gen_table_name on brc_gen_table(table_name);

comment on table brc_gen_table is '代码生成表';
comment on column brc_gen_table.id is 'ID';
comment on column brc_gen_table.table_name is '表名';
comment on column brc_gen_table.class_name is '类名';
comment on column brc_gen_table.table_comment is '说明';
comment on column brc_gen_table.author is '作者';
comment on column brc_gen_table.email is '邮箱';
comment on column brc_gen_table.package_name is '项目包名';
comment on column brc_gen_table.version is '项目版本号';
comment on column brc_gen_table.generator_type is '生成方式（0：zip压缩包，1：自定义目录）';
comment on column brc_gen_table.backend_path is '后端生成路径';
comment on column brc_gen_table.frontend_path is '前端生成路径';
comment on column brc_gen_table.module_name is '模块名';
comment on column brc_gen_table.function_name is '功能名';
comment on column brc_gen_table.form_layout is '表单布局（1：一列，2：两列）';
comment on column brc_gen_table.datasource_id is '数据源ID';
comment on column brc_gen_table.base_class_id is '基类ID';
comment on column brc_gen_table.remark is '备注';
comment on column brc_gen_table.create_time is '创建时间';

-- 5、代码生成表字段
create table brc_gen_table_field
(
    id              bigint       identity not null,
    table_id        bigint       not null,
    field_name      varchar(200) not null,
    field_type      varchar(100) not null,
    field_comment   varchar(200),
    attr_name       varchar(200) not null,
    attr_type       varchar(100) not null,
    package_name    varchar(200),
    sort            int,
    auto_fill       varchar(20),
    primary_key     bit,
    character_maximum_length bigint,
    base_field      bit,
    form_item       bit,
    form_item_type  varchar(200),
    form_dict       varchar(200),
    form_required   bit,
    form_validator  varchar(200),
    grid_item       bit,
    grid_sort       bit,
    query_item      bit,
    query_type      varchar(200),
    query_form_type varchar(200),
    create_time     datetime,
    primary key (id)
);

comment on table brc_gen_table_field is '代码生成表的字段表';
comment on column brc_gen_table_field.id is 'ID';
comment on column brc_gen_table_field.table_id is '表ID';
comment on column brc_gen_table_field.field_name is '字段名称';
comment on column brc_gen_table_field.field_type is '字段类型';
comment on column brc_gen_table_field.field_comment is '字段说明';
comment on column brc_gen_table_field.attr_name is '属性名';
comment on column brc_gen_table_field.attr_type is '属性类型';
comment on column brc_gen_table_field.package_name is '属性包名';
comment on column brc_gen_table_field.sort is '排序';
comment on column brc_gen_table_field.auto_fill is '自动填充（DEFAULT, INSERT, UPDATE, INSERT_UPDATE）';
comment on column brc_gen_table_field.primary_key is '主键（0：否，1：是）';
comment on column brc_gen_table_field.character_maximum_length is '字符最大长度';
comment on column brc_gen_table_field.base_field is '基类字段（0：否，1：是）';
comment on column brc_gen_table_field.form_item is '表单项（0：否，1：是）';
comment on column brc_gen_table_field.form_item_type is '表单项类型';
comment on column brc_gen_table_field.form_dict is '表单字典类型';
comment on column brc_gen_table_field.form_required is '表单必填（0：否，1：是）';
comment on column brc_gen_table_field.form_validator is '表单校验器';
comment on column brc_gen_table_field.grid_item is '列表项（0：否，1：是）';
comment on column brc_gen_table_field.grid_sort is '列表排序（0：否，1：是）';
comment on column brc_gen_table_field.query_item is '查询项（0：否，1：是）';
comment on column brc_gen_table_field.query_type is '查询方式';
comment on column brc_gen_table_field.query_form_type is '查询表单类型';
comment on column brc_gen_table_field.create_time is '创建时间';

-- 6、项目名变更表
create table brc_gen_project_modify
(
    id                             bigint       identity not null,
    project_name                   varchar(100) not null,
    project_code                   varchar(100) not null,
    project_code_abbreviate        varchar(100) not null,
    project_package                varchar(100) not null,
    project_path                   varchar(200) not null,
    modify_project_name            varchar(100),
    modify_project_code            varchar(100),
    modify_project_code_abbreviate varchar(100),
    modify_project_package         varchar(100),
    exclusions                     varchar(200),
    modify_suffix                  varchar(200),
    modify_tmp_path                varchar(100),
    create_time                    datetime,
    primary key (id)
);

comment on table brc_gen_project_modify is '项目名变更表';
comment on column brc_gen_project_modify.id is 'ID';
comment on column brc_gen_project_modify.project_name is '项目名';
comment on column brc_gen_project_modify.project_code is '项目标识';
comment on column brc_gen_project_modify.project_code_abbreviate is '项目标识缩写';
comment on column brc_gen_project_modify.project_package is '项目包名';
comment on column brc_gen_project_modify.project_path is '项目路径';
comment on column brc_gen_project_modify.modify_project_name is '变更项目名';
comment on column brc_gen_project_modify.modify_project_code is '变更项目标识';
comment on column brc_gen_project_modify.modify_project_code_abbreviate is '变更项目标识缩写';
comment on column brc_gen_project_modify.modify_project_package is '变更项目包名';
comment on column brc_gen_project_modify.exclusions is '排除文件';
comment on column brc_gen_project_modify.modify_suffix is '变更文件';
comment on column brc_gen_project_modify.modify_tmp_path is '变更临时路径';
comment on column brc_gen_project_modify.create_time is '创建时间';

-- 初始化-项目名变更表数据
insert into brc_gen_project_modify (id,project_name,project_code, project_code_abbreviate, project_package,project_path,modify_project_name,modify_project_code, modify_project_code_abbreviate, modify_project_package,exclusions,modify_suffix,modify_tmp_path,create_time) values (1,'Bryce Boot','bryce-boot', 'brc', 'com.brycehan.boot','/Users/brycehan/git/bryce-boot','Test Boot','test-boot', 'tst', 'com.test.boot','.git,.idea,target,logs','java,xml,yml,sql,md,txt',null,now());
