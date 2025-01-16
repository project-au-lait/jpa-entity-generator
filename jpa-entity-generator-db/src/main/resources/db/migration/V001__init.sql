CREATE TABLE main (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  col_char CHAR(1),
  col_date DATE,
  col_time TIME,
  col_timestamp TIMESTAMP,
  col_boolean BOOLEAN,
  col_jsonb JSONB,
  sort_key INT,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(36) NOT NULL DEFAULT 'system',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by CHAR(36) NOT NULL DEFAULT 'system',
  version bigint NOT NULL DEFAULT 0
);


CREATE TABLE one_to_one (
  id CHAR(36) PRIMARY KEY REFERENCES main,
  name VARCHAR(100),
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(36) NOT NULL DEFAULT 'system',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by CHAR(36) NOT NULL DEFAULT 'system',
  version bigint NOT NULL DEFAULT 0
);


CREATE TABLE one_to_many (
  id CHAR(36) PRIMARY KEY,
  main_id CHAR(36) REFERENCES main,
  name VARCHAR(100),
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(36) NOT NULL DEFAULT 'system',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by CHAR(36) NOT NULL DEFAULT 'system',
  version bigint NOT NULL DEFAULT 0
);


CREATE TABLE many_to_many (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(36) NOT NULL DEFAULT 'system',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by CHAR(36) NOT NULL DEFAULT 'system',
  version bigint NOT NULL DEFAULT 0
);


CREATE TABLE main_many_to_many_rel (
  main_id CHAR(36) REFERENCES main,
  many_to_many_id CHAR(36) REFERENCES many_to_many,
  CONSTRAINT main_many_to_many_rel_pk PRIMARY KEY (main_id, many_to_many_id)
);


CREATE TABLE composite_main (
  id_1 CHAR(3),
  id_2 CHAR(3),
  name VARCHAR(100),
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(36) NOT NULL DEFAULT 'system',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by CHAR(36) NOT NULL DEFAULT 'system',
  version bigint NOT NULL DEFAULT 0,
  CONSTRAINT composite_id_pk PRIMARY KEY (id_1, id_2)
);


CREATE TABLE composite_one_to_many (
  id CHAR(3) PRIMARY KEY,
  composite_main_id_1 CHAR(3),
  composite_main_id_2 CHAR(3),
  name VARCHAR(100),
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(36) NOT NULL DEFAULT 'system',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by CHAR(36) NOT NULL DEFAULT 'system',
  version bigint NOT NULL DEFAULT 0,
  FOREIGN KEY (composite_main_id_1, composite_main_id_2) REFERENCES composite_main (id_1, id_2)
);


CREATE TABLE self_ref (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  self_id CHAR(36) REFERENCES self_ref,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(36) NOT NULL DEFAULT 'system',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by CHAR(36) NOT NULL DEFAULT 'system',
  version bigint NOT NULL DEFAULT 0
);


CREATE TABLE main_child (
  id CHAR(36) REFERENCES main,
  seq_no INT,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(36) NOT NULL DEFAULT 'system',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by CHAR(36) NOT NULL DEFAULT 'system',
  version bigint NOT NULL DEFAULT 0,
  CONSTRAINT main_child_pk PRIMARY KEY (id, seq_no)
);


-- CREATE TABLE master (
--   id CHAR(36) PRIMARY KEY,
--   name VARCHAR(100),
--   created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--   created_by CHAR(36) NOT NULL DEFAULT 'system',
--   updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--   updated_by CHAR(36) NOT NULL DEFAULT 'system',
--   version bigint NOT NULL DEFAULT 0
-- );
-- CREATE TABLE parent_tran (
--   id CHAR(36) PRIMARY KEY,
--   master_id CHAR(36) REFERENCES master,
--   name VARCHAR(100),
--   created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--   created_by CHAR(36) NOT NULL DEFAULT 'system',
--   updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--   updated_by CHAR(36) NOT NULL DEFAULT 'system',
--   version bigint NOT NULL DEFAULT 0
-- );
-- CREATE TABLE child_tran (
--   id CHAR(36) REFERENCES parent_tran,
--   seq_no INT,
--   name VARCHAR(100),
--   created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--   created_by CHAR(36) NOT NULL DEFAULT 'system',
--   updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--   updated_by CHAR(36) NOT NULL DEFAULT 'system',
--   version bigint NOT NULL DEFAULT 0,
--   CONSTRAINT child_tran_id_pk PRIMARY KEY (id, seq_no)
-- );