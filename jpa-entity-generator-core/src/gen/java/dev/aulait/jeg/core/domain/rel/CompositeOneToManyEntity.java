package dev.aulait.jeg.core.domain.rel;

import dev.aulait.jeg.core.domain.main.CompositeMainEntity;
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
@Table(name = "composite_one_to_many")
public class CompositeOneToManyEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
    @JoinColumn(name = "composite_main_id_1", referencedColumnName = "id_1"),
    @JoinColumn(name = "composite_main_id_2", referencedColumnName = "id_2")
  })
  private CompositeMainEntity compositeMain;
}
