CREATE TABLE master (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  --${commonColumns}
);


CREATE TABLE transaction (
  id CHAR(36) PRIMARY KEY,
  master_id CHAR(36) REFERENCES master,
  name VARCHAR(100),
  --${commonColumns}
);