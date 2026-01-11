package dev.aulait.jeg.core.domain.jdbc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
  private Integer KEY_SEQ;
  private String IS_AUTOINCREMENT;

  @Getter(lazy = true)
  private final boolean pk = KEY_SEQ != null;

  private TableModel table;
}
