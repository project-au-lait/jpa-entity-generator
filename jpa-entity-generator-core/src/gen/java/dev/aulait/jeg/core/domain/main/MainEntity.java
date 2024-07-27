package dev.aulait.jeg.core.domain.main;

import dev.aulait.jeg.core.domain.rel.MainChildEntity;
import dev.aulait.jeg.core.domain.rel.ManyToManyEntity;
import dev.aulait.jeg.core.domain.rel.OneToOneEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "main")
public class MainEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @Id
  @Column(name = "id")
  @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "col_char")
  private String colChar;

  @Column(name = "col_date")
  private java.time.LocalDate colDate;

  @Column(name = "col_time")
  private java.time.LocalTime colTime;

  @Column(name = "col_timestamp")
  private java.time.LocalDateTime colTimestamp;

  @Column(name = "col_jsonb")
  private String colJsonb;

  @Column(name = "sort_key")
  private Integer sortKey;

  @OneToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.ALL})
  @PrimaryKeyJoinColumn
  @lombok.Setter()
  private OneToOneEntity oneToOne;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "id", insertable = false, updatable = false)
  private Set<MainChildEntity> mainChilds = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "main_many_to_many_rel",
      joinColumns = {@JoinColumn(name = "main_id", nullable = false, updatable = false)},
      inverseJoinColumns = {
        @JoinColumn(name = "many_to_many_id", nullable = false, updatable = false)
      })
  private Set<ManyToManyEntity> manyToManies = new HashSet<>();

  ;
}
