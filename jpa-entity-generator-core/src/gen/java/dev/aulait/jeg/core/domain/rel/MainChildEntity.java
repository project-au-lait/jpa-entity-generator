package dev.aulait.jeg.core.domain.rel;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "main_child")
public class MainChildEntity extends dev.aulait.jeg.core.domain.CompositeBaseEntity
    implements java.io.Serializable {

  @EqualsAndHashCode.Include @EmbeddedId private MainChildEntityId id;
}
