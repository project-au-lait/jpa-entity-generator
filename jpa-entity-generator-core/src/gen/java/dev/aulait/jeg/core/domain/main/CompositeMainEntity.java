package dev.aulait.jeg.core.domain.main;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "composite_main")
public class CompositeMainEntity extends dev.aulait.jeg.core.domain.CompositeBaseEntity
    implements java.io.Serializable {

  @EmbeddedId private CompositeMainEntityId id;

  @Column(name = "name")
  private String name;
}
