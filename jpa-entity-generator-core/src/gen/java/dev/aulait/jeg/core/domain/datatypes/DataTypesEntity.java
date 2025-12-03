package dev.aulait.jeg.core.domain.datatypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "data_types")
public class DataTypesEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "col_boolean")
  private Boolean colBoolean;

  @Column(name = "col_bit")
  private Boolean colBit;

  @Column(name = "col_integer")
  private Integer colInteger;

  @Column(name = "col_bigint")
  private Long colBigint;

  @Column(name = "col_numeric")
  private java.math.BigDecimal colNumeric;

  @Column(name = "col_date")
  private java.time.LocalDate colDate;

  @Column(name = "col_time")
  private java.time.LocalTime colTime;

  @Column(name = "col_timestamp")
  private java.time.LocalDateTime colTimestamp;

  @Column(name = "col_blob")
  private byte[] colBlob;
}
