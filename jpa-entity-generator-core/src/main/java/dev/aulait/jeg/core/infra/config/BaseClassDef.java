package dev.aulait.jeg.core.infra.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class BaseClassDef {
  private List<String> tables = new ArrayList<>();

  private String baseClass;
}
