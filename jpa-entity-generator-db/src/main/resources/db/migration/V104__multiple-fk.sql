CREATE TABLE multifk_parent (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  --${commonColumns}
);


CREATE TABLE multifk_child (
  id CHAR(36),
  seq_no int,
  name VARCHAR(100),
  multifk_parent_id CHAR(36),
  --${commonColumns},
  CONSTRAINT multifk_child_pk PRIMARY KEY (id, seq_no),
  CONSTRAINT multifk_child_id_fkey FOREIGN KEY (id) REFERENCES multifk_parent (id),
  CONSTRAINT multifk_child_multifk_parent_id_fkey FOREIGN KEY (multifk_parent_id) REFERENCES multifk_parent (id)
);