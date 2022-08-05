package com.tui.proof.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class PilotesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GenericGenerator(name="system-uuid", strategy = "uuid")
    public Integer number;
    public int pilotes;
    public double orderTotal;
    public LocalDateTime creationTime;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "clientId")
    public Client client;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "addressId")
    public Address address;

}
