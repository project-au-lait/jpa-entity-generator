package dev.aulait.jeg.core.domain.main;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import javax.annotation.processing.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Generated("dev.aulait.jeg:jpa-entity-generator")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Embeddable
public class CompositeMainEntityId implements java.io.Serializable {

  @Column(name = "id_1")
  private String id1;

  @Column(name = "id_2")
  private String id2;
}
