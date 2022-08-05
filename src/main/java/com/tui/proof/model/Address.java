package com.tui.proof.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "addressId", unique = true)
  public Integer id;
  public String street;
  public String postcode;
  public String city;
  public String country;
}
