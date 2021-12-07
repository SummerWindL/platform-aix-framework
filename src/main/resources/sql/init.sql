CREATE TABLE DR_SQL_LOADER_LOG(
serialid varchar(32),
sql_file_name varchar(500),
sql_file_path varchar(4000),
sql_file_charset varchar2(20),
sql_file_execute_flag  varchar(1),
sql_file_execute_log  clob
);
COMMENT ON TABLE DR_SQL_LOADER_LOG IS 'sql文件装载日志表';
COMMENT ON COLUMN DR_SQL_LOADER_LOG.serialid IS 'id';
COMMENT ON COLUMN DR_SQL_LOADER_LOG.sql_file_name IS 'sql文件名称';
COMMENT ON COLUMN DR_SQL_LOADER_LOG.sql_file_path IS 'sql文件路径';
COMMENT ON COLUMN DR_SQL_LOADER_LOG.sql_file_charset IS 'sql文件编码格式';
COMMENT ON COLUMN DR_SQL_LOADER_LOG.sql_file_execute_flag IS 'sql文件执行标记 0-失败 1-成功';
COMMENT ON COLUMN DR_SQL_LOADER_LOG.sql_file_execute_log IS 'sql文件执行日志';