CREATE TABLE master (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  --${commonColumns}
);


CREATE TABLE transaction (
  id CHAR(36) PRIMARY KEY,
  master_id CHAR(36),
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT transaction_master_id_fkey FOREIGN KEY (master_id) REFERENCES master (id)
);


CREATE TABLE composite_master (
  id_1 CHAR(36) NOT NULL,
  id_2 CHAR(36) NOT NULL,
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT composite_master_pk PRIMARY KEY (id_1, id_2)
);


CREATE TABLE composite_transaction (
  id CHAR(36) PRIMARY KEY,
  master_id_1 CHAR(36),
  master_id_2 CHAR(36),
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT composite_transaction_master_id_fkey FOREIGN KEY (master_id_1, master_id_2) REFERENCES composite_master (id_1, id_2)
);


CREATE TABLE field_name_transaction (
  id CHAR(36) PRIMARY KEY,
  related_master_id CHAR(36),
  comp_master_id_1 CHAR(36),
  co_master_id_2 CHAR(36),
  --${commonColumns},
  CONSTRAINT related_master_id_fkey FOREIGN KEY (related_master_id) REFERENCES master (id),
  CONSTRAINT related_composite_master_id_fkey FOREIGN KEY (comp_master_id_1, co_master_id_2) REFERENCES composite_master (id_1, id_2)
);