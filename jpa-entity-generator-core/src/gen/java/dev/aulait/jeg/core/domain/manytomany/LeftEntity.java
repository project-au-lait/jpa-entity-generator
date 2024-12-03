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
      joinColumns = {@JoinColumn(name = "left_id", nullable = false)},
      inverseJoinColumns = {@JoinColumn(name = "right_id", nullable = false)})
  private Set<RightEntity> rights = new HashSet<>();

  ;
}
