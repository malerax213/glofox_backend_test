package com.tui.proof.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "addressId", unique = true)
  //@GenericGenerator(name="system-uuid", strategy = "uuid")
  public Integer id;
  public String street;
  public String postcode;
  public String city;
  public String country;
}
