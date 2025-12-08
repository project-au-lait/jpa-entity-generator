package dev.aulait.jeg.core.domain.parentchild;

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
public class CompositeChildEntityId implements java.io.Serializable {

  @Column(name = "parent_id_1")
  private String parentId1;

  @Column(name = "parent_id_2")
  private String parentId2;

  @Column(name = "seq_no")
  private int seqNo;
}
