package dev.aulait.jeg.core.domain.main;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CompositeMainEntityId implements java.io.Serializable {

  @Column(name = "id_1")
  private String id1;

  @Column(name = "id_2")
  private String id2;
}
