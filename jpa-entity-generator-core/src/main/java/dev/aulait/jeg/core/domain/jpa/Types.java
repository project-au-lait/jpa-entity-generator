package dev.aulait.jeg.core.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Types implements Type {
  Set("java.util"),
  HashSet("java.util"),
  Getter("lombok"),
  Setter("lombok"),
  CascadeType("jakarta.persistence"),
  Column("jakarta.persistence"),
  Entity("jakarta.persistence"),
  FetchType("jakarta.persistence"),
  Id("jakarta.persistence"),
  EmbeddedId("jakarta.persistence"),
  JoinColumn("jakarta.persistence"),
  JoinColumns("jakarta.persistence"),
  JoinTable("jakarta.persistence"),
  ManyToMany("jakarta.persistence"),
  ManyToOne("jakarta.persistence"),
  OneToMany("jakarta.persistence"),
  OneToOne("jakarta.persistence"),
  PrimaryKeyJoinColumn("jakarta.persistence"),
  Table("jakarta.persistence");

  @Getter private String pkg;

  public String getName() {
    return name();
  }
}
