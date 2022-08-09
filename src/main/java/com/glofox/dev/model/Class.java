package com.glofox.dev.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer class_id;
    public String name;
    public Date start_date;
    public Date end_date;
    public Integer capacity;
}
