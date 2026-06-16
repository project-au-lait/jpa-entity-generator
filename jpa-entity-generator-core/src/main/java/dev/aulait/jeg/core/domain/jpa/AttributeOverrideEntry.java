package dev.aulait.jeg.core.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttributeOverrideEntry {
  private String name;
  private String column;
}
