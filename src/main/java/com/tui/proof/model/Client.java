package com.tui.proof.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "clientId", unique = true)
  //@GenericGenerator(name="system-uuid", strategy = "uuid")
  public Integer id;
  public String firstName;
  public String lastName;
  public String telephone;
}
