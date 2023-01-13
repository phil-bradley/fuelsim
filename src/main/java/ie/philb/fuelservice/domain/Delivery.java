/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import lombok.Data;

/**
 *
 * @author PBradley
 */
@Data
@Entity
public class Delivery implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_id_seq")
    private int id;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @ManyToOne(fetch = FetchType.EAGER)
    private Tank tank;

    private int amountLitres;
    private int volumePreDelivery;
    private int volumePostDelivery;

}
