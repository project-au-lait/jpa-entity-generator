package dev.aulait.jeg.core.domain.readonlymanytoone;

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
@Table(name = "composite_readonly_manytoone_transaction")
public class CompositeReadonlyManytooneTransactionEntity
    extends dev.aulait.jeg.core.domain.BaseEntity implements java.io.Serializable {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "master_id_1")
  private String masterId1;

  @Column(name = "master_id_2")
  private String masterId2;

  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
    @JoinColumn(
        name = "master_id_1",
        referencedColumnName = "id_1",
        insertable = false,
        updatable = false),
    @JoinColumn(
        name = "master_id_2",
        referencedColumnName = "id_2",
        insertable = false,
        updatable = false)
  })
  private CompositeReadonlyManytooneMasterEntity master;
}
