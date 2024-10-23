CREATE TABLE multifk_parent (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  --${commonColumns}
);


CREATE TABLE multifk_child (
  id CHAR(36) REFERENCES multifk_parent,
  seq_no int,
  name VARCHAR(100),
  multifk_parent_id CHAR(36) REFERENCES multifk_parent,
  --${commonColumns},
  CONSTRAINT multifk_child_pk PRIMARY KEY (id, seq_no)
);