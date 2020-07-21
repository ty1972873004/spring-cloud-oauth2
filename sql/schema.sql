-- 客户端应用注册详情
create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY, -- 客户端应用的账号
  resource_ids VARCHAR(256),		-- 客户端应用可访问的资源服务器列表,(空代表所有资源服务器都可以访问)
  client_secret VARCHAR(256),	-- 客户端应用的密码
  scope VARCHAR(256),	-- 资源服务器拥有的所有权限列表 (get add delete update)  
  authorized_grant_types VARCHAR(256), -- 客户端支持的授权码模式列表
  web_server_redirect_uri VARCHAR(256), -- 授权码模式,申请授权码后重定向的uri.
  authorities VARCHAR(256),
  access_token_validity INTEGER,   -- 设置颁发token的有效期
  refresh_token_validity INTEGER,  -- 颁发refresh_token的有效期(不设置不会同时颁发refresh_token)
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)     -- 设置为true,授权码模式下自动授权
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

-- 存放颁发的token
create table oauth_access_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication BLOB,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication BLOB
);

-- 授权码模式下,存放颁发的授权码
create table oauth_code (
  code VARCHAR(256), authentication BLOB
);

create table oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt DATETIME,
	lastModifiedAt DATETIME
);

CREATE TABLE `sys_user` (
  `id` bigint(32) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `enable_` tinyint(1) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(32) DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `modified_user` bigint(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
);