package dev.aulait.jeg.core.domain.bridge;

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
@Table(name = "west_east_bridge")
public class WestEastBridgeEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @EqualsAndHashCode.Include @EmbeddedId private WestEastBridgeEntityId id;

  @Column(name = "extra")
  private String extra;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "east_id", referencedColumnName = "id", insertable = false, updatable = false)
  private EastEntity east;
}
