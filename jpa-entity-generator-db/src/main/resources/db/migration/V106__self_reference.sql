CREATE TABLE self_reference (
  id CHAR(36) PRIMARY KEY,
  self_ref_id CHAR(36),
  CONSTRAINT self_reference_id_fkey FOREIGN KEY (self_ref_id) REFERENCES self_reference (id)
);


CREATE TABLE composite_self_reference (
  id CHAR(36),
  seq int,
  self_ref_seq int,
  CONSTRAINT composite_self_reference_pk PRIMARY KEY (id, seq),
  CONSTRAINT composite_self_reference_fk FOREIGN KEY (id, self_ref_seq) REFERENCES composite_self_reference (id, seq)
);
