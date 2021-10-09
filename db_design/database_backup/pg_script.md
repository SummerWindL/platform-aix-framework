--  postgreSQL 数据库备份指令
>   pg_dump -h localhost -U postgres aix_db > /home/aix_db.bak

--  postgreSQL 数据库恢复指令
>   psql -h localhost -U postgres -d aix_db <  /home/aix_db.bak