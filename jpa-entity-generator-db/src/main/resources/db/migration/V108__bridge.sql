CREATE TABLE west (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  --${commonColumns}
);


CREATE TABLE east (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100),
  --${commonColumns}
);


CREATE TABLE west_east_bridge (
  west_id CHAR(36),
  east_id CHAR(36),
  extra VARCHAR(100),
  CONSTRAINT west_east_bridge_pk PRIMARY KEY (west_id, east_id),
  CONSTRAINT west_east_bridge_west_id_fkey FOREIGN KEY (west_id) REFERENCES west (id),
  CONSTRAINT west_east_bridge_east_id_fkey FOREIGN KEY (east_id) REFERENCES east (id)
);


CREATE TABLE composite_west (
  id_1 CHAR(36) NOT NULL,
  id_2 CHAR(36) NOT NULL,
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT composite_west_pk PRIMARY KEY (id_1, id_2)
);


CREATE TABLE composite_east (
  id_1 CHAR(36) NOT NULL,
  id_2 CHAR(36) NOT NULL,
  name VARCHAR(100),
  --${commonColumns},
  CONSTRAINT composite_east_pk PRIMARY KEY (id_1, id_2)
);


CREATE TABLE composite_west_east_bridge (
  west_id_1 CHAR(36),
  west_id_2 CHAR(36),
  east_id_1 CHAR(36),
  east_id_2 CHAR(36),
  extra VARCHAR(100),
  CONSTRAINT composite_west_east_bridge_pk PRIMARY KEY (west_id_1, west_id_2, east_id_1, east_id_2),
  CONSTRAINT composite_west_east_bridge_west_id_fkey FOREIGN KEY (west_id_1, west_id_2) REFERENCES composite_west (id_1, id_2),
  CONSTRAINT composite_west_east_bridge_east_id_fkey FOREIGN KEY (east_id_1, east_id_2) REFERENCES composite_east (id_1, id_2)
);