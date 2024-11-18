package dev.aulait.jeg.core.domain.jpa;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldMetadataModel {
  private String fieldName;
  private String javaType;
  private boolean multiple;
  private String dbType;
  private Integer dbTypeSize;
  private boolean id;
  private boolean required;
}
