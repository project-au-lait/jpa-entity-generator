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