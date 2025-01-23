CREATE TABLE "left" (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  --${commonColumns}
);


CREATE TABLE "right" (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  --${commonColumns}
);


CREATE TABLE left_right_rel (
  left_id CHAR(36),
  right_id CHAR(36),
  CONSTRAINT left_right__rel_pk PRIMARY KEY (left_id, right_id),
  CONSTRAINT left_right_rel_left_id_fkey FOREIGN KEY (left_id) REFERENCES "left" (id),
  CONSTRAINT left_right_rel_right_id_fkey FOREIGN KEY (right_id) REFERENCES "right" (id)
);