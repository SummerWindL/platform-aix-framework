-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler version: 1.1.6
-- PostgreSQL version: 17.0
-- Project Site: pgmodeler.io
-- Model Author: ---

-- Database creation must be performed outside a multi lined SQL file.
-- These commands were put in this file only as a convenience.
--
-- object: pikai | type: DATABASE --
-- DROP DATABASE IF EXISTS pikai;
drop table if exists t_pikai_email_verification;
drop table if exists t_pikai_login_log;
drop table if exists t_pikai_third_party_auth;
drop table if exists t_pikai_user_conf;
drop table if exists t_pikai_timeline_images;
drop table if exists t_pikai_timeline_content;
drop table if exists t_pikai_user;
drop table if exists t_pikai_menu;
drop table if exists t_pikai_sys_dict_item;
drop table if exists t_pikai_sys_dict_type;


-- object: aix.t_pikai_user | type: TABLE --
-- DROP TABLE IF EXISTS aix.t_pikai_user CASCADE;
CREATE TABLE aix.t_pikai_user (
	user_id character varying(255) NOT NULL,
	account_id varchar(255),
	user_name character varying(255) NOT NULL,
	salt varchar(255),
	password varchar(255) NOT NULL,
	encryp_type varchar(255) NOT NULL,
	user_info jsonb,
	account_status varchar(2) NOT NULL,
	create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	update_time timestamp NOT NULL,
	CONSTRAINT t_pikai_user_pk PRIMARY KEY (user_id),
	CONSTRAINT account_id_unique UNIQUE (account_id)
);
-- ddl-end --
COMMENT ON TABLE aix.t_pikai_user IS E'用户表';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user.user_id IS E'用户ID';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user.account_id IS E'账户id(email唯一)';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user.user_name IS E'用户名称';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user.salt IS E'盐';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user.password IS E'密码';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user.encryp_type IS E'加密方式';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user.user_info IS E'用户属性（电话、邮箱、地址等）';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user.account_status IS E'账户状态(0、正常，1、销户，2、冻结)';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user.create_time IS E'创建时间';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user.update_time IS E'更新时间';
-- ddl-end --
COMMENT ON CONSTRAINT account_id_unique ON aix.t_pikai_user IS E'账号id唯一';
-- ddl-end --
ALTER TABLE aix.t_pikai_user OWNER TO postgres;
-- ddl-end --

-- object: aix.t_pikai_user_conf | type: TABLE --
-- DROP TABLE IF EXISTS aix.t_pikai_user_conf CASCADE;
CREATE TABLE aix.t_pikai_user_conf (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1 CACHE 1 ),
	user_id character varying(255),
	conf_item_code character varying(255) NOT NULL,
	conf_item_name character varying(255) NOT NULL,
	conf_item_value jsonb NOT NULL,
	create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT userid_conf_pk PRIMARY KEY (id)
);
-- ddl-end --
COMMENT ON TABLE aix.t_pikai_user_conf IS E'用户配置表';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user_conf.id IS E'ID（主键）';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user_conf.user_id IS E'用户ID';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user_conf.conf_item_code IS E'配置项编码';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user_conf.conf_item_name IS E'配置项名称';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user_conf.conf_item_value IS E'配置项值';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user_conf.create_time IS E'创建时间';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_user_conf.update_time IS E'更新时间';
-- ddl-end --
ALTER TABLE aix.t_pikai_user_conf OWNER TO postgres;
-- ddl-end --

-- 唯一约束
ALTER TABLE aix.t_pikai_user_conf ADD CONSTRAINT unique_user_conf UNIQUE (user_id, conf_item_code);

-- -- object: aix.t_pikai_user_conf_id_seq | type: SEQUENCE --
-- -- DROP SEQUENCE IF EXISTS aix.t_pikai_user_conf_id_seq CASCADE;
-- CREATE SEQUENCE aix.t_pikai_user_conf_id_seq
-- 	INCREMENT BY 1
-- 	MINVALUE 1
-- 	MAXVALUE 2147483647
-- 	START WITH 1
-- 	CACHE 1
-- 	NO CYCLE
-- 	OWNED BY NONE;
--
-- -- ddl-end --
-- ALTER SEQUENCE aix.t_pikai_user_conf_id_seq OWNER TO postgres;
-- -- ddl-end --
--
-- object: aix.t_pikai_timeline_content | type: TABLE --
-- DROP TABLE IF EXISTS aix.t_pikai_timeline_content CASCADE;
CREATE TABLE aix.t_pikai_timeline_content (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1 CACHE 1 ),
	title character varying(255) NOT NULL,
	content text NOT NULL,
	tag character varying(10) NOT NULL,
	user_id character varying(255) NOT NULL,
	img_id jsonb,
	video_id jsonb,
	create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT content_id_pk PRIMARY KEY (id)
);
-- ddl-end --
COMMENT ON TABLE aix.t_pikai_timeline_content IS E'时光纪事表记录表';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_content.id IS E'主键ID';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_content.title IS E'文章（记录）标题';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_content.content IS E'内容';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_content.tag IS E'标签分类（字典代表，逗号分隔）';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_content.user_id IS E'用户ID';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_content.img_id IS E'图片id（关联图片表id）';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_content.video_id IS E'视频ID（关联视频表id）';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_content.create_time IS E'创建时间';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_content.update_time IS E'更新时间';
-- ddl-end --
ALTER TABLE aix.t_pikai_timeline_content OWNER TO postgres;
-- ddl-end --

-- -- object: aix.t_pikai_timeline_content_id_seq | type: SEQUENCE --
-- -- DROP SEQUENCE IF EXISTS aix.t_pikai_timeline_content_id_seq CASCADE;
-- CREATE SEQUENCE aix.t_pikai_timeline_content_id_seq
-- 	INCREMENT BY 1
-- 	MINVALUE 1
-- 	MAXVALUE 2147483647
-- 	START WITH 1
-- 	CACHE 1
-- 	NO CYCLE
-- 	OWNED BY NONE;
--
-- -- ddl-end --
-- ALTER SEQUENCE aix.t_pikai_timeline_content_id_seq OWNER TO postgres;
-- -- ddl-end --
--
-- object: aix.t_pikai_timeline_images | type: TABLE --
-- DROP TABLE IF EXISTS aix.t_pikai_timeline_images CASCADE;
CREATE TABLE aix.t_pikai_timeline_images (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1 CACHE 1 ),
	content_id integer,
	image_url text NOT NULL,
	create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT imgid_pk PRIMARY KEY (id)
);
-- ddl-end --
COMMENT ON TABLE aix.t_pikai_timeline_images IS E'图片表';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_images.id IS E'主键ID';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_images.content_id IS E'文章id';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_images.image_url IS E'图片url（暂时base64）';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_images.create_time IS E'创建时间';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_timeline_images.update_time IS E'更新时间';
-- ddl-end --
ALTER TABLE aix.t_pikai_timeline_images OWNER TO postgres;
-- ddl-end --

-- -- object: aix.t_pikai_timeline_images_id_seq | type: SEQUENCE --
-- -- DROP SEQUENCE IF EXISTS aix.t_pikai_timeline_images_id_seq CASCADE;
-- CREATE SEQUENCE aix.t_pikai_timeline_images_id_seq
-- 	INCREMENT BY 1
-- 	MINVALUE 1
-- 	MAXVALUE 2147483647
-- 	START WITH 1
-- 	CACHE 1
-- 	NO CYCLE
-- 	OWNED BY NONE;
--
-- -- ddl-end --
-- ALTER SEQUENCE aix.t_pikai_timeline_images_id_seq OWNER TO postgres;
-- -- ddl-end --
--
-- object: aix.t_pikai_menu | type: TABLE --
-- DROP TABLE IF EXISTS aix.t_pikai_menu CASCADE;
CREATE TABLE aix.t_pikai_menu (
	id integer NOT NULL,
	menu_id character varying(255) NOT NULL,
	parent_menu_id character varying(255),
	menu_name character varying(255) NOT NULL,
	menu_layer character varying(1) NOT NULL,
	menu_order smallint NOT NULL,
	menu_url character varying(2000),
	menu_icon json NOT NULL,
	menu_status character varying(1) NOT NULL,
	CONSTRAINT menu_id_pk PRIMARY KEY (id)
);
-- ddl-end --
COMMENT ON TABLE aix.t_pikai_menu IS E'菜单表';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_menu.id IS E'主键ID';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_menu.menu_id IS E'菜单ID';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_menu.parent_menu_id IS E'父级菜单id';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_menu.menu_name IS E'菜单名称';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_menu.menu_layer IS E'菜单层级（0：父级菜单，1:子菜单）';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_menu.menu_order IS E'菜单顺序（1、2、3）';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_menu.menu_url IS E'菜单地址';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_menu.menu_icon IS E'菜单图标';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_menu.menu_status IS E'菜单状态（E: 启动，F: 禁用）';
-- ddl-end --
COMMENT ON CONSTRAINT menu_id_pk ON aix.t_pikai_menu IS E'主键ID';
-- ddl-end --
ALTER TABLE aix.t_pikai_menu OWNER TO postgres;
-- ddl-end --

-- object: aix.t_pikai_sys_dict_type | type: TABLE --
-- DROP TABLE IF EXISTS aix.t_pikai_sys_dict_type CASCADE;
CREATE TABLE aix.t_pikai_sys_dict_type (
	dict_code character varying(50) NOT NULL,
	dict_name character varying(100) NOT NULL,
	is_fixed boolean DEFAULT false,
	CONSTRAINT t_pikai_sys_dict_type_pkey PRIMARY KEY (dict_code)
);
-- ddl-end --
COMMENT ON TABLE aix.t_pikai_sys_dict_type IS E'字典类型表';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_sys_dict_type.dict_code IS E'字典类型编码';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_sys_dict_type.dict_name IS E'字典类型名称';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_sys_dict_type.is_fixed IS E'是否固定（false-动态 true-枚举固定）';
-- ddl-end --
ALTER TABLE aix.t_pikai_sys_dict_type OWNER TO postgres;
-- ddl-end --

-- object: aix.t_pikai_sys_dict_item_id_seq | type: SEQUENCE --
DROP SEQUENCE IF EXISTS aix.t_pikai_sys_dict_item_id_seq CASCADE;
CREATE SEQUENCE aix.t_pikai_sys_dict_item_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE aix.t_pikai_sys_dict_item_id_seq OWNER TO postgres;
-- ddl-end --

-- object: aix.t_pikai_sys_dict_item | type: TABLE --
-- DROP TABLE IF EXISTS aix.t_pikai_sys_dict_item CASCADE;
CREATE TABLE aix.t_pikai_sys_dict_item (
	id bigint NOT NULL DEFAULT nextval('aix.t_pikai_sys_dict_item_id_seq'::regclass),
	dict_code character varying(50) NOT NULL,
	item_code character varying(50) NOT NULL,
	item_name character varying(100) NOT NULL,
	item_value character varying(255) NOT NULL,
	sort integer DEFAULT 0,
	CONSTRAINT t_pikai_sys_dict_item_pkey PRIMARY KEY (id)
);
-- ddl-end --
COMMENT ON TABLE aix.t_pikai_sys_dict_item IS E'字典项表';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_sys_dict_item.id IS E'主键ID';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_sys_dict_item.dict_code IS E'关联字典类型编码';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_sys_dict_item.item_code IS E'字典项编码';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_sys_dict_item.item_name IS E'字典项名称';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_sys_dict_item.item_value IS E'字典项值';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_sys_dict_item.sort IS E'排序号';
-- ddl-end --
ALTER TABLE aix.t_pikai_sys_dict_item OWNER TO postgres;
-- ddl-end --

-- object: aix.t_pikai_login_log | type: TABLE --
-- DROP TABLE IF EXISTS aix.t_pikai_login_log CASCADE;
CREATE TABLE aix.t_pikai_login_log (
	login_id varchar(32) NOT NULL,
	account_id varchar(255) NOT NULL,
	login_ip varchar(255) NOT NULL,
	login_time timestamp NOT NULL,
	login_type varchar(10) NOT NULL,
	login_device varchar(255) NOT NULL,
	CONSTRAINT login_id_pk PRIMARY KEY (login_id)
);
-- ddl-end --
COMMENT ON TABLE aix.t_pikai_login_log IS E'登录日志表';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_login_log.login_id IS E'主键ID';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_login_log.account_id IS E'登录邮箱';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_login_log.login_ip IS E'登录IP';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_login_log.login_time IS E'登录时间';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_login_log.login_type IS E'登录类型';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_login_log.login_device IS E'登录设备';
-- ddl-end --
ALTER TABLE aix.t_pikai_login_log OWNER TO postgres;
-- ddl-end --

-- object: idx_dict_item_code | type: INDEX --
-- DROP INDEX IF EXISTS aix.idx_dict_item_code CASCADE;
CREATE INDEX idx_dict_item_code ON aix.t_pikai_sys_dict_item
USING btree
(
	dict_code,
	item_code
)
WITH (FILLFACTOR = 90);
-- ddl-end --

-- object: aix.t_pikai_third_party_auth | type: TABLE --
-- DROP TABLE IF EXISTS aix.t_pikai_third_party_auth CASCADE;
CREATE TABLE aix.t_pikai_third_party_auth (
	user_id varchar(255) NOT NULL,
	provider varchar(50) NOT NULL,
	provider_user_id varchar(255) NOT NULL,
	access_token varchar(255),
	refresh_token varchar(255),
	expires_at timestamp,
	CONSTRAINT third_party_auth_pk PRIMARY KEY (provider,provider_user_id)
);
-- ddl-end --
COMMENT ON TABLE aix.t_pikai_third_party_auth IS E'第三方登录授权信息';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_third_party_auth.user_id IS E'用户ID';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_third_party_auth.provider IS E'第三方提供商 如 wechat, github';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_third_party_auth.provider_user_id IS E'第三方用户唯一ID';
-- ddl-end --
COMMENT ON CONSTRAINT third_party_auth_pk ON aix.t_pikai_third_party_auth IS E'主键';
-- ddl-end --
ALTER TABLE aix.t_pikai_third_party_auth OWNER TO postgres;
-- ddl-end --

-- DROP TABLE IF EXISTS aix.t_pikai_email_verification CASCADE;
CREATE TABLE aix.t_pikai_email_verification (
	verification_id varchar(255) NOT NULL,
	user_id varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	verification_code varchar(32) NOT NULL,
	status varchar(10) NOT NULL,
	expire_time timestamp NOT NULL,
	create_time timestamp NOT NULL,
	update_time timestamp NOT NULL,
	CONSTRAINT t_pikai_email_verification_pk PRIMARY KEY (verification_id)
);
-- ddl-end --
COMMENT ON TABLE aix.t_pikai_email_verification IS E'邮箱验证表';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_email_verification.verification_id IS E'验证ID';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_email_verification.user_id IS E'用户ID';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_email_verification.email IS E'邮箱地址';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_email_verification.verification_code IS E'验证码';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_email_verification.status IS E'状态(PENDING、VERIFIED、EXPIRED)';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_email_verification.expire_time IS E'过期时间';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_email_verification.create_time IS E'创建时间';
-- ddl-end --
COMMENT ON COLUMN aix.t_pikai_email_verification.update_time IS E'更新时间';
-- ddl-end --
COMMENT ON CONSTRAINT t_pikai_email_verification_pk ON aix.t_pikai_email_verification IS E'主键';
-- ddl-end --
ALTER TABLE aix.t_pikai_email_verification OWNER TO postgres;
-- ddl-end --


-- object: "userid_FK" | type: CONSTRAINT --
-- ALTER TABLE aix.t_pikai_user_conf DROP CONSTRAINT IF EXISTS "userid_FK" CASCADE;
ALTER TABLE aix.t_pikai_user_conf ADD CONSTRAINT "userid_FK" FOREIGN KEY (user_id)
REFERENCES aix.t_pikai_user (user_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: "userid_FK" | type: CONSTRAINT --
-- ALTER TABLE aix.t_pikai_timeline_content DROP CONSTRAINT IF EXISTS "userid_FK" CASCADE;
ALTER TABLE aix.t_pikai_timeline_content ADD CONSTRAINT "userid_FK" FOREIGN KEY (user_id)
REFERENCES aix.t_pikai_user (user_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: "content_id_FK" | type: CONSTRAINT --
-- ALTER TABLE aix.t_pikai_timeline_images DROP CONSTRAINT IF EXISTS "content_id_FK" CASCADE;
ALTER TABLE aix.t_pikai_timeline_images ADD CONSTRAINT "content_id_FK" FOREIGN KEY (content_id)
REFERENCES aix.t_pikai_timeline_content (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: t_pikai_sys_dict_item_dict_code_fkey | type: CONSTRAINT --
-- ALTER TABLE aix.t_pikai_sys_dict_item DROP CONSTRAINT IF EXISTS t_pikai_sys_dict_item_dict_code_fkey CASCADE;
ALTER TABLE aix.t_pikai_sys_dict_item ADD CONSTRAINT t_pikai_sys_dict_item_dict_code_fkey FOREIGN KEY (dict_code)
REFERENCES aix.t_pikai_sys_dict_type (dict_code) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: "userid_FK" | type: CONSTRAINT --
-- ALTER TABLE aix.t_pikai_third_party_auth DROP CONSTRAINT IF EXISTS "userid_FK" CASCADE;
ALTER TABLE aix.t_pikai_third_party_auth ADD CONSTRAINT "userid_FK" FOREIGN KEY (user_id)
REFERENCES aix.t_pikai_user (user_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: account_id_fk | type: CONSTRAINT --
-- ALTER TABLE aix.t_pikai_login_log DROP CONSTRAINT IF EXISTS account_id_fk CASCADE;
ALTER TABLE aix.t_pikai_login_log ADD CONSTRAINT account_id_fk FOREIGN KEY (account_id)
REFERENCES aix.t_pikai_user (account_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: t_pikai_email_verification_user_fk | type: CONSTRAINT --
-- ALTER TABLE aix.t_pikai_email_verification DROP CONSTRAINT IF EXISTS t_pikai_email_verification_user_fk CASCADE;
ALTER TABLE aix.t_pikai_email_verification ADD CONSTRAINT t_pikai_email_verification_user_fk FOREIGN KEY (user_id)
REFERENCES aix.t_pikai_user (user_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --
COMMENT ON CONSTRAINT t_pikai_email_verification_user_fk ON aix.t_pikai_email_verification IS E'外键';
-- ddl-end --