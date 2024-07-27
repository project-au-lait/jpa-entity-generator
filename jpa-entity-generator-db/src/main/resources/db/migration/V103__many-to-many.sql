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
  left_id CHAR(36) REFERENCES "left",
  right_id CHAR(36) REFERENCES "right",
  CONSTRAINT left_right__rel_pk PRIMARY KEY (left_id, right_id)
);