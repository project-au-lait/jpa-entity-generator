package dev.aulait.jeg.core.domain.manytomany;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "left")
public class LeftEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "name")
  private String name;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "left_right_rel",
      joinColumns = {@JoinColumn(name = "left_id", nullable = false, updatable = false)},
      inverseJoinColumns = {@JoinColumn(name = "right_id", nullable = false, updatable = false)})
  private Set<RightEntity> rights = new HashSet<>();

  ;
}
