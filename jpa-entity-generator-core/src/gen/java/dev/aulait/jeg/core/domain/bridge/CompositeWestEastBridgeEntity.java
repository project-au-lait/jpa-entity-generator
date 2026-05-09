package dev.aulait.jeg.core.domain.bridge;

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
@Table(name = "composite_west_east_bridge")
public class CompositeWestEastBridgeEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @EqualsAndHashCode.Include @EmbeddedId private CompositeWestEastBridgeEntityId id;

  @Column(name = "extra")
  private String extra;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
    @JoinColumn(
        name = "east_id_1",
        referencedColumnName = "id_1",
        insertable = false,
        updatable = false),
    @JoinColumn(
        name = "east_id_2",
        referencedColumnName = "id_2",
        insertable = false,
        updatable = false)
  })
  private CompositeEastEntity east;
}
