package dev.aulait.jeg.core.domain.jpa;

import java.util.List;
import lombok.Data;

@Data
public class EmbeddedFieldModel {
  private String fieldName;
  private String type;
  private List<AttributeOverrideEntry> attributeOverrides;
  private int keySeq;
}
