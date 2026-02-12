package dev.aulait.jeg.core.domain.jpa;

import java.util.List;
import lombok.Data;

@Data
public class EntityMetadataModel {
  private String packageName;
  private String className;
  private String tableName;
  private String parentType;
  private List<FieldMetadataModel> fields;
}
