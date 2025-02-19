package dev.aulait.jeg.core.domain.multifk;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "multifk_child")
public class MultifkChildEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @EqualsAndHashCode.Include @EmbeddedId private MultifkChildEntityId id;

  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "multifk_parent_id", referencedColumnName = "id")
  private MultifkParentEntity multifkParent;
}
