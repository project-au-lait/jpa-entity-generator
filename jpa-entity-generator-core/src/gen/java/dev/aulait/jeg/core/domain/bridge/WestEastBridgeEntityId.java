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
public class WestEastBridgeEntityId implements java.io.Serializable {

  @Column(name = "west_id")
  private String westId;

  @Column(name = "east_id")
  private String eastId;
}
