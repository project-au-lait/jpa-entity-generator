package dev.aulait.jeg.core.domain.parentchild;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ChildEntityId implements java.io.Serializable {

  @Column(name = "parent_id")
  private String parentId;

  @Column(name = "seq_no")
  private Integer seqNo;
}
