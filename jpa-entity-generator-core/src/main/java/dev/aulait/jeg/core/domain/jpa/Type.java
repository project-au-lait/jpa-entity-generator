package dev.aulait.jeg.core.domain.jpa;

public interface Type {

  String getPkg();

  String getName();

  default String getFqdn() {
    return getPkg() + "." + getName();
  }
}
