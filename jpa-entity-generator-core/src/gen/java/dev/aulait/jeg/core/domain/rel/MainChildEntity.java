package dev.aulait.jeg.core.domain.rel;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "main_child")
public class MainChildEntity extends dev.aulait.jeg.core.domain.CompositeBaseEntity
    implements java.io.Serializable {

  @EmbeddedId private MainChildEntityId id;
}
