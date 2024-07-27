CREATE TABLE parent (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  --${commonColumns}
);


CREATE TABLE child (
  id CHAR(36) REFERENCES parent,
  seq_no int,
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT child_pk PRIMARY KEY (id, seq_no)
);