package dev.aulait.jeg.core.domain.mastertran;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
@Table(name = "field_name_transaction")
public class FieldNameTransactionEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id")
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
    @JoinColumn(name = "comp_master_id_1", referencedColumnName = "id_1"),
    @JoinColumn(name = "co_master_id_2", referencedColumnName = "id_2")
  })
  private CompositeMasterEntity compositeMaster;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "related_master_id", referencedColumnName = "id")
  private MasterEntity relatedMaster;
}
