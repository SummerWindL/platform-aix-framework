-- 1. 创建多列GIN索引
CREATE EXTENSION pg_trgm;
CREATE INDEX idx_trgm_multi_search ON t_pikai_timeline_content USING GIN (
    title gin_trgm_ops,
    content gin_trgm_ops,
    tag gin_trgm_ops
);

-- 2. 保持原查询不变
explain SELECT * FROM t_pikai_timeline_content
WHERE title LIKE '%111%' OR
      content LIKE '%111%' OR
      tag LIKE '%111%';