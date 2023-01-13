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
public class Filling implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filling_id_seq")
    private int id;

    private int volumeMilliLitres;
    private int totalPricePence;
    private FillingState fillingStatus;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date started;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date completed;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date paid;

    private int posId;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pump pump;

    @ManyToOne(fetch = FetchType.EAGER)
    private Tank tank;
}
