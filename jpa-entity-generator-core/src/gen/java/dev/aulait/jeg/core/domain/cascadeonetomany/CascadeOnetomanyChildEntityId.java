package dev.aulait.jeg.core.domain.cascadeonetomany;

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
public class CascadeOnetomanyChildEntityId implements java.io.Serializable {

  @Column(name = "parent_id")
  private String parentId;

  @Column(name = "seq_no")
  private int seqNo;
}
