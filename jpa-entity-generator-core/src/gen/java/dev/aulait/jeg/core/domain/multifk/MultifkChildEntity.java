package dev.aulait.jeg.core.domain.multifk;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "multifk_child")
public class MultifkChildEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @EmbeddedId private MultifkChildEntityId id;

  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "multifk_parent_id")
  private MultifkParentEntity multifkParent;
}
