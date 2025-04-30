ALTER TABLE aix.t_pikai_timeline_content ADD content_uid varchar(32) DEFAULT '' NOT NULL;
ALTER TABLE aix.t_pikai_timeline_content ADD content_date date DEFAULT current_date NOT NULL;
COMMENT ON COLUMN aix.t_pikai_timeline_content.content_date IS '文章日期';