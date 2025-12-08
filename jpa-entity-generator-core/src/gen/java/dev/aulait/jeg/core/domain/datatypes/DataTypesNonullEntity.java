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
@Table(name = "data_types_nonull")
public class DataTypesNonullEntity extends dev.aulait.jeg.core.domain.BaseEntity
    implements java.io.Serializable {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "col_boolean")
  private boolean colBoolean;

  @Column(name = "col_bit")
  private boolean colBit;

  @Column(name = "col_integer")
  private int colInteger;

  @Column(name = "col_tinyint")
  private int colTinyint;

  @Column(name = "col_bigint")
  private long colBigint;

  @Column(name = "col_real")
  private float colReal;

  @Column(name = "col_float")
  private double colFloat;

  @Column(name = "col_double")
  private double colDouble;

  @Column(name = "col_decimal")
  private java.math.BigDecimal colDecimal;

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
