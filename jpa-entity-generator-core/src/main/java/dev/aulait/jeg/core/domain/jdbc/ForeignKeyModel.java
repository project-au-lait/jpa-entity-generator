package dev.aulait.jeg.core.domain.jdbc;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ForeignKeyModel {
  @EqualsAndHashCode.Include private final String name;
  private List<KeyModel> keys = new ArrayList<>();

  public TableModel getPkTable() {
    return keys.get(0).getPkTable();
  }

  public TableModel getFkTable() {
    return keys.get(0).getFkTable();
  }
}
