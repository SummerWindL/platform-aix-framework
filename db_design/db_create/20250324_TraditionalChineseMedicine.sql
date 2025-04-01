/**中医药方管理Traditional Chinese medicine prescription**/
CREATE TABLE t_tcm_prescriptions (
                                   id SERIAL PRIMARY KEY,
                                   name VARCHAR(255) NOT NULL,
                                   ingredients TEXT NOT NULL,
                                   usage VARCHAR(100),
                                   indications TEXT,
                                   file_path VARCHAR(500),
                                   created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_tcm_name ON t_tcm_prescriptions(name);


CREATE SEQUENCE
sqe_tcm_prescriptions
INCREMENT 1        -- 步长
MINVALUE 1        -- 最小值
MAXVALUE 999999999    --最大值
START WITH 1    --起始值
CACHE 1;
