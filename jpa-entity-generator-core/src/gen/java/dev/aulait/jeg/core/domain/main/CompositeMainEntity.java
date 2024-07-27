package dev.aulait.jeg.core.domain.main;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "composite_main")
public class CompositeMainEntity extends dev.aulait.jeg.core.domain.CompositeBaseEntity
    implements java.io.Serializable {

  @EmbeddedId private CompositeMainEntityId id;

  @Column(name = "name")
  private String name;
}
