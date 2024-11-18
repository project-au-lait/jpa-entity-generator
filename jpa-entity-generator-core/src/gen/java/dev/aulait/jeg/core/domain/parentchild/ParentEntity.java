package dev.aulait.jeg.core.domain.parentchild;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parent")
public class ParentEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "name")
  private String name;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "id", insertable = false, updatable = false)
  private Set<ChildEntity> children = new HashSet<>();
}
