 ALTER TABLE aix.t_pikai_third_party_auth ALTER COLUMN provider_user_id TYPE varchar(1000) USING provider_user_id::varchar(1000);
 ALTER TABLE aix.t_pikai_third_party_auth ALTER COLUMN refresh_token TYPE varchar(1000) USING refresh_token::varchar(1000);
 ALTER TABLE aix.t_pikai_third_party_auth ALTER COLUMN access_token TYPE varchar(1000) USING access_token::varchar(1000);