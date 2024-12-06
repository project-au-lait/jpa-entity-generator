package dev.aulait.jeg.core.domain.rel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "one_to_one")
public class OneToOneEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "name")
  private String name;
}
