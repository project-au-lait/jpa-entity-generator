package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.domain.jdbc.TableModel;
import java.util.List;

public interface RelationProcessor {
  void process(List<TableModel> tables, List<EntityModel> entities);
}
