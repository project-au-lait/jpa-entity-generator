CREATE TABLE main (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  col_char CHAR(1),
  col_date DATE,
  col_time TIME,
  col_timestamp TIMESTAMP,
  col_boolean BOOLEAN,
  col_json JSON,
  sort_key INT,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(36) NOT NULL DEFAULT 'system',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by CHAR(36) NOT NULL DEFAULT 'system',
  version bigint NOT NULL DEFAULT 0
);


CREATE TABLE one_to_one (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(36) NOT NULL DEFAULT 'system',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by CHAR(36) NOT NULL DEFAULT 'system',
  version bigint NOT NULL DEFAULT 0,
  CONSTRAINT one_to_one_id_fkey FOREIGN KEY (id) REFERENCES main (id)
);


CREATE TABLE one_to_many (
  id CHAR(36) PRIMARY KEY,
  main_id CHAR(36),
  name VARCHAR(100),
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(36) NOT NULL DEFAULT 'system',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by CHAR(36) NOT NULL DEFAULT 'system',
  version bigint NOT NULL DEFAULT 0,
  CONSTRAINT one_to_many_main_fkey FOREIGN KEY (main_id) REFERENCES main (id)
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
  main_id CHAR(36),
  many_to_many_id CHAR(36),
  CONSTRAINT main_many_to_many_rel_pk PRIMARY KEY (main_id, many_to_many_id),
  CONSTRAINT main_many_to_many_rel_main_fkey FOREIGN KEY (main_id) REFERENCES main (id),
  CONSTRAINT main_many_to_many_rel_many_to_many_fkey FOREIGN KEY (many_to_many_id) REFERENCES many_to_many (id)
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
  CONSTRAINT composite_one_to_many_composite_main_fkey FOREIGN KEY (composite_main_id_1, composite_main_id_2) REFERENCES composite_main (id_1, id_2)
);


CREATE TABLE self_ref (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  self_id CHAR(36),
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(36) NOT NULL DEFAULT 'system',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by CHAR(36) NOT NULL DEFAULT 'system',
  version bigint NOT NULL DEFAULT 0,
  CONSTRAINT self_ref_self_fkey FOREIGN KEY (self_id) REFERENCES self_ref (id)
);


CREATE TABLE main_child (
  id CHAR(36),
  seq_no INT,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by CHAR(36) NOT NULL DEFAULT 'system',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by CHAR(36) NOT NULL DEFAULT 'system',
  version bigint NOT NULL DEFAULT 0,
  CONSTRAINT main_child_pk PRIMARY KEY (id, seq_no),
  CONSTRAINT main_child_id_fkey FOREIGN KEY (id) REFERENCES main (id)
);