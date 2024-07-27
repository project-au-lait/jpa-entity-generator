package dev.aulait.jeg.core.domain.jdbc;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ColumnModel {
  @EqualsAndHashCode.Include private String TABLE_CAT;
  @EqualsAndHashCode.Include private String TABLE_SCHEM;
  @EqualsAndHashCode.Include private String TABLE_NAME;
  @EqualsAndHashCode.Include private String COLUMN_NAME;
  private int DATA_TYPE;
  private String TYPE_NAME;
  private int COLUMN_SIZE;
  private int DECIMAL_DIGITS;
  private String COLUMN_DEF;
  private int NULLABLE;
  private boolean pk;
  private TableModel table;
}
