CREATE TABLE data_types (
  id CHAR(36) PRIMARY KEY,
  col_boolean BOOLEAN,
  col_bit BIT,
  col_integer INTEGER,
  col_tinyint SMALLINT,
  col_bigint BIGINT,
  col_real REAL,
  col_float FLOAT,
  col_double DOUBLE PRECISION,
  col_decimal DECIMAL,
  col_numeric NUMERIC,
  col_date DATE,
  col_time TIME,
  col_timestamp TIMESTAMP,
  col_blob BYTEA
);


CREATE TABLE data_types_nonull (
  id CHAR(36) PRIMARY KEY,
  col_boolean BOOLEAN NOT NULL,
  col_bit BIT NOT NULL,
  col_integer INTEGER NOT NULL,
  col_tinyint SMALLINT NOT NULL,
  col_bigint BIGINT NOT NULL,
  col_real REAL NOT NULL,
  col_float FLOAT NOT NULL,
  col_double DOUBLE PRECISION NOT NULL,
  col_decimal DECIMAL NOT NULL,
  col_numeric NUMERIC NOT NULL,
  col_date DATE NOT NULL,
  col_time TIME NOT NULL,
  col_timestamp TIMESTAMP NOT NULL,
  col_blob BYTEA NOT NULL
);