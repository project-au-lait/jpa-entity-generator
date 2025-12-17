package dev.aulait.jeg.core.domain.mastertran;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
@Table(name = "field_name_transaction")
public class FieldNameTransactionEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "related_master_id", referencedColumnName = "id")
  private FieldNameMasterEntity relatedMaster;
}
