package dev.aulait.jeg.core.domain.parentchild;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "child")
public class ChildEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @EmbeddedId private ChildEntityId id;

  @Column(name = "name")
  private String name;
}
