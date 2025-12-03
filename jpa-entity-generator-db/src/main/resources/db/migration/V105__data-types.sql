CREATE TABLE data_types (
  id CHAR(36) PRIMARY KEY,
  col_boolean BOOLEAN,
  col_bit BIT,
  col_integer INTEGER,
  col_bigint BIGINT,
  col_numeric NUMERIC,
  col_date DATE,
  col_time TIME,
  col_timestamp TIMESTAMP,
  col_blob BYTEA
);
