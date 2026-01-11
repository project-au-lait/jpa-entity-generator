package dev.aulait.jeg.core.domain.jpa;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldMetadataModel {
  private String fieldName;
  private String columnName;
  private String javaType;
  private String javaWrapperType;
  private boolean multiple;
  private Integer stringLength;
  private boolean id;
  private boolean required;
  private boolean autoIncrement;
}
