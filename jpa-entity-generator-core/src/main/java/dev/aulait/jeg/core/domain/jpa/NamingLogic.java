package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.domain.jdbc.ForeignKeyModel;
import dev.aulait.jeg.core.infra.config.Config;
import dev.aulait.jeg.core.infra.util.WordUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class NamingLogic {

  private final Config config;

  public String toManyToOneFieldName(ForeignKeyModel fk, EntityModel oneEntity) {
    List<String> fkFieldNames =
        fk.getKeys().stream()
            .map(k -> WordUtils.fkColNameToFieldName(k.getFKCOLUMN_NAME(), k.getPKCOLUMN_NAME()))
            .filter(StringUtils::isNotEmpty)
            .distinct()
            .toList();
    return fkFieldNames.size() == 1
        ? fkFieldNames.get(0)
        : WordUtils.entityNameToFieldName(oneEntity.getName(), config.getEntitySuffix());
  }
}
