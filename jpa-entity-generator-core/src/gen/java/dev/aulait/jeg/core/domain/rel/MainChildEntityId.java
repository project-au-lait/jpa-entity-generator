package dev.aulait.jeg.core.domain.rel;

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
public class MainChildEntityId implements java.io.Serializable {

  @Column(name = "id")
  private String id;

  @Column(name = "seq_no")
  private Integer seqNo;
}
