-- ----------------------------
-- 用户信息表
-- ----------------------------
drop table if exists `user`;
create table `user` (
    `id`                bigint(20)      not null auto_increment    comment '用户ID',
    `user_name`         varchar(64)     not null                   comment '用户账号',
    `nick_name`         varchar(64)     not null                   comment '用户昵称',
    `user_type`         varchar(2)      not null default '00'      comment '用户类型（00系统用户 01普通用户）',
    `email`             varchar(64)     default ''                 comment '用户邮箱',
    `phone_number`      varchar(32)     default ''                 comment '手机号码',
    `sex`               char(1)         default '2'                comment '用户性别（0男 1女 2未知）',
    `avatar`            varchar(128)    default ''                 comment '头像地址',
    `password`          varchar(64)     not null default ''        comment '密码',
    `status`            char(1)         default '0'                comment '帐号状态（0正常 1停用）',
    `del_flag`          int(1)          default 0                  comment '删除标志（0代表存在 1代表删除）',
    `login_ip`          varchar(128)    default ''                 comment '最后登录IP',
    `login_date`        datetime                                   comment '最后登录时间',
    `create_by`         bigint(20)      default null               comment '创建者id',
    `create_time`       datetime                                   comment '创建时间',
    `update_by`         bigint(20)      default null               comment '更新者id',
    `update_time`       datetime                                   comment '更新时间',
    `remark`            varchar(512)    default null               comment '备注',
    primary key (`id`)
) engine=innodb auto_increment=3 default charset=utf8mb4 comment = '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
-- insert into user values(1, 'admin', '等于停了丶', '00', 'hscovo@mail.ustc.edu.cn', '15888888888', '1', '', '$2a$10$WCEjT00MEcTNDqF111rCSOkafb6KmZdXMYGsYvoWhiVEMr3gbLoNO', '0', 0, '127.0.0.1', sysdate(), null , sysdate(), null , null, '系统管理员');
-- insert into user values(2, 'hsccc',   '等于停了丶', '00', 'ry@qq.com',  '15666666666', '0', '', '$2a$10$WCEjT00MEcTNDqF111rCSOkafb6KmZdXMYGsYvoWhiVEMr3gbLoNO', '0', 0, '127.0.0.1', sysdate(), 1,     sysdate(), null , null, '第一个用户');

-- ----------------------------
-- 角色信息表
-- ----------------------------
drop table if exists `role`;
create table `role` (
    `id`                   bigint(20)      not null auto_increment    comment '角色ID',
    `name`                 varchar(128)    not null                   comment '角色名称',
    `role_key`             varchar(128)    not null                   comment '角色权限字符串',
    `role_sort`            int(4)          not null                   comment '显示顺序',
    `menu_check_strictly`  tinyint(1)      default 1                  comment '菜单树选择项是否关联显示',
    `status`               char(1)         not null                   comment '角色状态（0正常 1停用）',
    `del_flag`             int(1)          default 0                  comment '删除标志（0代表存在 1代表删除）',
    `create_by`            bigint(20)      default null               comment '创建者id',
    `create_time`          datetime                                   comment '创建时间',
    `update_by`            bigint(20)      default null               comment '更新者id',
    `update_time`          datetime                                   comment '更新时间',
    `remark`               varchar(512)    default null               comment '备注',
    primary key (`id`)
) engine=innodb auto_increment=100 default charset=utf8mb4 comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
-- insert into role values(1, '超级管理员',  'admin',  1, 1, '0', '0', 1, sysdate(), null , null, '系统管理员');
-- insert into role values(2, '普通用户',    'common', 2, 1, '0', '0', 1, sysdate(), null , null, '普通角色');

-- ----------------------------
-- 菜单权限表
-- ----------------------------
drop table if exists `menu`;
create table `menu` (
    `id`                bigint(20)      not null auto_increment    comment '菜单ID',
    `name`              varchar(64)     not null                   comment '菜单名称',
    `parent_id`         bigint(20)      not null default 0         comment '父菜单ID',
    `order_num`         int(4)          default 0                  comment '显示顺序',
    `path`              varchar(255)    default ''                 comment '路由地址',
    `component`         varchar(255)    default null               comment '组件路径',
    `query`             varchar(255)    default null               comment '路由参数',
    `is_frame`          int(1)          default 0                  comment '是否为外链（0是 1否）',
    `is_cache`          int(1)          default 0                  comment '是否缓存（0缓存 1不缓存）',
    `type`              char(1)         default ''                 comment '菜单类型（M目录 C菜单 F按钮）',
    `visible`           char(1)         default 0                  comment '菜单状态（0显示 1隐藏）',
    `status`            char(1)         default 0                  comment '菜单状态（0正常 1停用）',
    `perms`             varchar(128)    default null               comment '权限标识',
    `icon`              varchar(128)    default '#'                comment '菜单图标',
    `create_by`         bigint(20)      default null               comment '创建者id',
    `create_time`       datetime                                   comment '创建时间',
    `update_by`         bigint(20)      default null               comment '更新者id',
    `update_time`       datetime                                   comment '更新时间',
    `remark`            varchar(512)    default ''                 comment '备注',
    primary key (`id`)
) engine=innodb auto_increment=2000 default charset=utf8mb4 comment = '菜单权限表';

-- ----------------------------
-- 用户和角色关联表  用户N-N角色
-- ----------------------------
drop table if exists `user_role`;
create table `user_role` (
    `id`        bigint(20) not null auto_increment comment '用户和角色关联ID',
    `user_id`   bigint(20) not null                comment '用户ID',
    `role_id`   bigint(20) not null                comment '角色ID',
    primary key (`id`),
    unique key (`user_id`, `role_id`)
) engine=innodb default charset=utf8mb4 comment = '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
-- insert into user_role values (1, '1', '1');
-- insert into user_role values (2, '2', '2');

-- ----------------------------
-- 角色和菜单关联表  角色N-N菜单
-- ----------------------------
drop table if exists `role_menu`;
create table `role_menu` (
    `id`        bigint(20) not null auto_increment comment '角色和菜单关联ID',
    `role_id`   bigint(20) not null                comment '角色ID',
    `menu_id`   bigint(20) not null                comment '菜单ID',
    primary key (`id`),
    unique key(`role_id`, `menu_id`)
) engine=innodb default charset=utf8mb4 comment = '角色和菜单关联表';
