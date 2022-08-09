package com.glofox.dev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "book_id", unique = true)
  public Integer id;
  public String name;
  public Date date;
  @ManyToOne(cascade=CascadeType.PERSIST)
  @JoinColumn(name = "class_id")
  public Class aClass;
}
