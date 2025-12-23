package dev.aulait.jeg.core.domain.selfref;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
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
@Table(name = "composite_self_reference")
public class CompositeSelfReferenceEntity extends dev.aulait.jeg.core.domain.CompositeBaseEntity
    implements java.io.Serializable {

  @EqualsAndHashCode.Include @EmbeddedId private CompositeSelfReferenceEntityId id;

  @Column(name = "self_ref_seq")
  private Integer selfRefSeq;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false),
    @JoinColumn(name = "self_ref_seq", referencedColumnName = "seq", insertable = false, updatable = false)
  })
  private CompositeSelfReferenceEntity compositeSelfReference;
}
