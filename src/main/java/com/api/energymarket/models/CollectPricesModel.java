package com.api.energymarket.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ENERGY_PRICES")
public class CollectPricesModel {

  @Id
  private int id;
  @Column(nullable = false)
  private String mes;
  @Column(nullable = false)
  private double sudeste;
  @Column(nullable = false)
  private double sul;
  @Column(nullable = false)
  private double nordeste;
  @Column(nullable = false)
  private double norte;

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getMes() {
    return mes;
  }
  public void setMes(String mes) {
    this.mes = mes;
  }
  public double getSudeste() {
    return sudeste;
  }
  public void setSudeste(double sudeste) {
    this.sudeste = sudeste;
  }
  public double getSul() {
    return sul;
  }
  public void setSul(double sul) {
    this.sul = sul;
  }
  public double getNordeste() {
    return nordeste;
  }
  public void setNordeste(double nordeste) {
    this.nordeste = nordeste;
  }
  public double getNorte() {
    return norte;
  }
  public void setNorte(double norte) {
    this.norte = norte;
  }
}
