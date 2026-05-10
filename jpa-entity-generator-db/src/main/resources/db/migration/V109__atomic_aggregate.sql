CREATE TABLE atomic_parent (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  --${commonColumns}
);


CREATE TABLE atomic_child (
  parent_id CHAR(36) REFERENCES atomic_parent,
  seq_no int,
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT atomic_child_pk PRIMARY KEY (parent_id, seq_no)
);