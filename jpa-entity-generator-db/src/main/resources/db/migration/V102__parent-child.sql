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


CREATE TABLE composite_parent (
  id_1 CHAR(36),
  id_2 CHAR(36),
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT composite_parent_pk PRIMARY KEY (id_1, id_2)
);


CREATE TABLE composite_child (
  parent_id_1 CHAR(36),
  parent_id_2 CHAR(36),
  seq_no int,
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT composite_child_pk PRIMARY KEY (parent_id_1, parent_id_2, seq_no),
  CONSTRAINT composite_child_parent_id_fkey FOREIGN KEY (parent_id_1, parent_id_2) REFERENCES composite_parent (id_1, id_2)
);