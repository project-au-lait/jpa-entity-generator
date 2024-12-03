package dev.aulait.jeg.core.domain.parentchild;

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
@Table(name = "child")
public class ChildEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @EmbeddedId private ChildEntityId id;

  @Column(name = "name")
  private String name;
}
