package dev.aulait.jeg.core.domain.bridge;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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

  @Column(name = "west_id_1")
  private String westId1;

  @Column(name = "west_id_2")
  private String westId2;

  @Column(name = "east_id_1")
  private String eastId1;

  @Column(name = "east_id_2")
  private String eastId2;
}
