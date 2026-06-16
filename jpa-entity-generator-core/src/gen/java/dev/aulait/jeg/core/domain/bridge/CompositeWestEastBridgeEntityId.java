package dev.aulait.jeg.core.domain.bridge;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import javax.annotation.processing.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Generated("dev.aulait.jeg:jpa-entity-generator")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CompositeWestEastBridgeEntityId implements java.io.Serializable {

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id1", column = @Column(name = "west_id_1")),
    @AttributeOverride(name = "id2", column = @Column(name = "west_id_2"))
  })
  private CompositeWestEntityId westId;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id1", column = @Column(name = "east_id_1")),
    @AttributeOverride(name = "id2", column = @Column(name = "east_id_2"))
  })
  private CompositeEastEntityId eastId;
}
