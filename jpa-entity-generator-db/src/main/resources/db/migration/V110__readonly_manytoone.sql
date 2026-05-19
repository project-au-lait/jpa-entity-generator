CREATE TABLE readonly_manytoone_master (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  --${commonColumns}
);


CREATE TABLE readonly_manytoone_transaction (
  id CHAR(36) PRIMARY KEY,
  master_id CHAR(36),
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT readonly_manytoone_transaction_master_id_fkey FOREIGN KEY (master_id) REFERENCES readonly_manytoone_master (id)
);


CREATE TABLE composite_readonly_manytoone_master (
  id_1 CHAR(36),
  id_2 CHAR(36),
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT composite_readonly_manytoone_master_pk PRIMARY KEY (id_1, id_2)
);


CREATE TABLE composite_readonly_manytoone_transaction (
  id CHAR(36) PRIMARY KEY,
  master_id_1 CHAR(36),
  master_id_2 CHAR(36),
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT composite_readonly_manytoone_transaction_master_id_fkey FOREIGN KEY (master_id_1, master_id_2) REFERENCES composite_readonly_manytoone_master (id_1, id_2)
);