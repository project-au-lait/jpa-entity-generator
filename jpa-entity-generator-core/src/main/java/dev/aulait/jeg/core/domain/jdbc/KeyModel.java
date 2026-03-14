package dev.aulait.jeg.core.domain.jdbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class KeyModel {
  private String PKTABLE_NAME;
  private String PKCOLUMN_NAME;
  private String FKTABLE_NAME;
  private String FKCOLUMN_NAME;
  private String FK_NAME;
  private String PK_NAME;
  private TableModel pkTable;
  private ColumnModel pkColumn;
  private TableModel fkTable;
  private ColumnModel fkColumn;
}
