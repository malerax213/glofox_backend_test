package com.tui.proof.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "clientId", unique = true)
  public Integer id;
  public String firstName;
  public String lastName;
  public String telephone;
}
