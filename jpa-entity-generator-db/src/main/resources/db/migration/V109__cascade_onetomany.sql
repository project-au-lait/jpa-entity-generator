CREATE TABLE cascade_onetomany_parent (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  --${commonColumns}
);


CREATE TABLE cascade_onetomany_child (
  parent_id CHAR(36) REFERENCES cascade_onetomany_parent,
  seq_no int,
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT cascade_onetomany_child_pk PRIMARY KEY (parent_id, seq_no)
);