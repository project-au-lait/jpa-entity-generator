package ${root.pkg};

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import javax.annotation.processing.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Generated("dev.aulait.jeg:jpa-entity-generator")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ${root.name} implements java.io.Serializable {
<#list root.fields as field>

  @Column(name = "${field.columnName}")
  private ${field.type} ${field.name};
</#list>
}
