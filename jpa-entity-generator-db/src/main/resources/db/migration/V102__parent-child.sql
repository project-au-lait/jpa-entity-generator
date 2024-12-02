CREATE TABLE parent (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  --${commonColumns}
);


CREATE TABLE child (
  parent_id CHAR(36) REFERENCES parent,
  seq_no int,
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT child_pk PRIMARY KEY (parent_id, seq_no)
);