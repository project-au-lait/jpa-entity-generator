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
  @Deprecated private Integer stringLength;
  private Integer columnSize;
  private Integer decimalDigits;
  private String columnDef;
  private boolean id;
  private boolean required;
  private boolean autoIncrement;
  private boolean generated;
}
